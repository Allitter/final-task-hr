package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.CommandType;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String login = (String) request.getAttribute(Attributes.LOGIN);
        String password = (String) request.getAttribute(Attributes.PASSWORD);

        try {
            Optional<User> optional = userService.authenticateUser(login, password);
            if (optional.isPresent()) {
                request.setAttribute(Attributes.COMMAND, CommandType.VACANCIES);

                HttpSession session = request.getSession();

                User user = optional.get();
                session.setAttribute(Attributes.USER, user);
            } else {
                request.setAttribute(Attributes.SHOW_AUTHENTICATION_ERROR_MESSAGE, true);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            return Router.forward(Pages.SERVER_ERROR);
        }

        String path = request.getContextPath() + request.getServletPath();
        return Router.redirect(path);
    }
}
