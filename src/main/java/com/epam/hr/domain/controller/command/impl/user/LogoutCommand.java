package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.controller.command.Pages;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return Router.forward(Pages.LOGIN);
    }
}
