package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class UserUnbanCommand implements Command {
    private final UserService service;

    public UserUnbanCommand(UserService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long id = Long.parseLong((String)request.getAttribute(Attributes.USER_ID));

        service.unbanUser(id);

        String path = request.getContextPath() + request.getServletPath();
        return Router.redirect(path);
    }
}
