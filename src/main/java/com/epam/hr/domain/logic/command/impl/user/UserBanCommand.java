package com.epam.hr.domain.logic.command.impl.user;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class UserBanCommand implements Command {
    private final UserService service;

    public UserBanCommand(UserService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        long id = Long.parseLong((String)request.getAttribute(Attributes.USER_ID));

        try {
            service.banUser(id);

            String path = request.getContextPath() + request.getServletPath();
            return Router.redirect(path);
        } catch (ServiceException e) {
            return Router.forward(Pages.SERVER_ERROR);
        }
    }
}
