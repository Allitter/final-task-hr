package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class EmployeeInfoCommand implements Command {
    private final UserService service;

    public EmployeeInfoCommand(UserService service) {
        this.service = service;
    }

    // TODO maybe merge commands
    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long id = Long.parseLong((String)request.getAttribute(Attributes.USER_ID));

        Optional<User> optional = service.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            request.setAttribute(Attributes.EMPLOYEE, user);
        } else {
            return Router.forward(Pages.PAGE_NOT_FOUND);
        }

        return Router.forward(Pages.EMPLOYEE_INFO);
    }
}
