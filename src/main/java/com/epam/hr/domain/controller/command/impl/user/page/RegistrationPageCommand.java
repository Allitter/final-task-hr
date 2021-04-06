package com.epam.hr.domain.controller.command.impl.user.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.user.AbstractUserCommand;

import javax.servlet.http.HttpServletRequest;

public class RegistrationPageCommand extends AbstractUserCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        return Router.forward(Pages.REGISTRATION);
    }

}
