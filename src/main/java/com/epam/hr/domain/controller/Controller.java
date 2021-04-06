package com.epam.hr.domain.controller;

import com.epam.hr.data.pool.ConnectionPool;
import com.epam.hr.domain.controller.command.*;
import com.epam.hr.domain.service.SessionManager;
import com.epam.hr.domain.service.impl.SessionManagerImpl;
import com.epam.hr.exception.ControllerException;
import com.epam.hr.exception.EntityNotFoundException;
import com.epam.hr.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main controller implemented with front controller template
 */
public class Controller extends HttpServlet {
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
        CommandType commandName = (CommandType) request.getAttribute(Attributes.COMMAND);
        Command command = CommandFactory.create(commandName);

        try {
            Router router = command.execute(request);
            route(router, request, response);
        } catch (EntityNotFoundException e) {
            LOGGER.warn(e);
            safeRoute(Router.forward(Pages.PAGE_NOT_FOUND), request, response);
        } catch (ServletException | IOException | ServiceException | ControllerException e) {
            LOGGER.error(e);
            safeRoute(Router.forward(Pages.SERVER_ERROR), request, response);
        }
    }

    private void safeRoute(Router route, HttpServletRequest request, HttpServletResponse response) {
        try {
            route(route, request, response);
        } catch (ServletException | IOException | ControllerException e) {
            LOGGER.error(e);
        }
    }

    private void route(Router router, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ControllerException {

        String page = router.getPath();
        switch (router.getType()) {
            case FORWARD:
                request.getRequestDispatcher(page).forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(page);
                break;
            default:
                throw new ControllerException("Unknown route type");
        }
    }
}
