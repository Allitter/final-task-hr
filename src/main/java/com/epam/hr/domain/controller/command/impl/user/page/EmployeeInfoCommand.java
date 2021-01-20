package com.epam.hr.domain.controller.command.impl.user.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.user.AbstractUserCommand;
import com.epam.hr.domain.model.Ban;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class EmployeeInfoCommand extends AbstractUserCommand {
    private final UserService userService;

    public EmployeeInfoCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idUser = Long.parseLong((String)request.getAttribute(Attributes.USER_ID));

        User user = userService.tryFindById(idUser);
        if (user.isBanned()) {
            Ban ban = userService.tryFindLastBan(idUser);
            request.setAttribute(Attributes.BAN, ban);
        }

        request.setAttribute(Attributes.EMPLOYEE, user);
        return Router.forward(Pages.EMPLOYEE_INFO);
    }
}
