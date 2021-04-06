package com.epam.hr.domain.controller.command.impl;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Pages;

import javax.servlet.http.HttpServletRequest;

public class LoginPageCommand extends AbstractCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        return Router.forward(Pages.LOGIN);
    }

}
