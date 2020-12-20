package com.epam.hr.domain.logic;

import com.epam.hr.data.ConnectionPool;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.CommandType;
import com.epam.hr.exception.LogicRuntimeException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.CommandFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class Servlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init() {
        try {
            super.init();
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException | ServletException e) {
            throw new LogicRuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool.getInstance().destroy();

            Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
            while (driverEnumeration.hasMoreElements()) {
                Driver driver = driverEnumeration.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }

        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) {
        try {
            CommandType commandName = (CommandType) request.getAttribute(Attributes.COMMAND);
            Command command = CommandFactory.create(commandName);
            Router router = command.execute(request);
            String page = router.getPath();

            if (router.getType().equals(RouteType.FORWARD)) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(page);
            }

        } catch (ServletException | IOException | ServiceException e) {
            LOGGER.error(e);
        }
    }
}
