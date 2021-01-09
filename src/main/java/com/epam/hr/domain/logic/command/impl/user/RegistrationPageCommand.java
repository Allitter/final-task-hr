package com.epam.hr.domain.logic.command.impl.user;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;

import javax.servlet.http.HttpServletRequest;

public class RegistrationPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        return Router.forward(Pages.REGISTRATION);
    }

}
