package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.CommandType;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.SessionManager;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginCommand extends AbstractUserCommand {
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String login = (String) request.getAttribute(Attributes.LOGIN);
        String password = (String) request.getAttribute(Attributes.PASSWORD);

        try {
            User user = userService.authenticateUser(login, password);
            request.setAttribute(Attributes.COMMAND, CommandType.DEFAULT_COMMAND);
            HttpSession session = request.getSession();
            session.setAttribute(Attributes.USER, user);

            // register session in SessionManager
            SessionManager sessionManager = (SessionManager) request.getSession()
                    .getServletContext().getAttribute(Attributes.SESSION_MANAGER);
            sessionManager.bindSession(user.getId(), request.getSession());
            String path = request.getContextPath() + request.getServletPath();
            return Router.redirect(path);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            return Router.forward(Pages.LOGIN);
        }
    }
}
