package com.epam.hr.domain.controller;

import com.epam.hr.data.pool.ConnectionPool;
import com.epam.hr.domain.controller.command.*;
import com.epam.hr.domain.service.SessionManager;
import com.epam.hr.domain.service.impl.SessionManagerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Servlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init() throws ServletException {
        super.init();
        SessionManager sessionManager = new SessionManagerImpl();
        getServletContext().setAttribute(Attributes.SESSION_MANAGER, sessionManager);
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroy();
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

            if (router.isForward()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                dispatcher.forward(request, response);
            } else if (router.isRedirect()) {
                response.sendRedirect(page);
            } else {
                LOGGER.warn("Unknown route type {}", router.getType());
                RequestDispatcher dispatcher = request.getRequestDispatcher(Pages.SERVER_ERROR);
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            LOGGER.error(e);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Pages.SERVER_ERROR);
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                LOGGER.error(ex);
            }
        }
    }
}
