package com.epam.hr.domain.controller.command.impl;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.CommandType;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ConfirmationCommand extends AbstractCommand {
    private final UserService userService;

    public ConfirmationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String password = (String) request.getAttribute(Attributes.PASSWORD);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        String login = user.getLogin();

        try {
            userService.authenticateUser(login, password);

            setTargetCommandToCommandAttributeOrErrorPageCommand(request);

            String path = request.getServletPath();
            return Router.forward(path);
        } catch (ValidationException e) {
            String path = (String) request.getAttribute(Attributes.PREVIOUS_PAGE);
            return Router.redirect(path);
        }
    }

    private void setTargetCommandToCommandAttributeOrErrorPageCommand(HttpServletRequest request) {
        String commandName = (String) request.getAttribute(Attributes.TARGET_COMMAND);
        Optional<CommandType> optionalTargetCommand = CommandType.getCommand(commandName);

        if (optionalTargetCommand.isPresent()) {
            CommandType targetCommand = optionalTargetCommand.get();
            request.setAttribute(Attributes.COMMAND, targetCommand);
        } else {
            request.setAttribute(Attributes.COMMAND, CommandType.SERVER_ERROR);
        }
    }
}
