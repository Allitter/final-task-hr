package com.epam.hr.domain.controller.command.impl.user.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.user.AbstractUserCommand;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class EmployeeInfoCommand extends AbstractUserCommand {
    private final UserService service;

    public EmployeeInfoCommand(UserService service) {
        this.service = service;
    }

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
