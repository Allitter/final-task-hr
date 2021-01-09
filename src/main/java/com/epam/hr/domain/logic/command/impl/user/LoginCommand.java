package com.epam.hr.domain.logic.command.impl.user;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.CommandType;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginCommand implements Command {
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String login = (String) request.getAttribute(Attributes.LOGIN);
        String password = (String) request.getAttribute(Attributes.PASSWORD);
        try {
            User user = userService.authenticateUser(login, password);
            request.setAttribute(Attributes.COMMAND, CommandType.DEFAULT_COMMAND);
            HttpSession session = request.getSession();
            session.setAttribute(Attributes.USER, user);

            String path = request.getContextPath() + request.getServletPath();
            return Router.redirect(path);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            return Router.forward(Pages.LOGIN);
        } catch (ServiceException e) {
            return Router.forward(Pages.SERVER_ERROR);
        }
    }
}
