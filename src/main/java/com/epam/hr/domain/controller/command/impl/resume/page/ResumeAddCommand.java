package com.epam.hr.domain.controller.command.impl.resume.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.controller.command.Pages;

import javax.servlet.http.HttpServletRequest;

public class ResumeAddCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return Router.forward(Pages.RESUME_ADD);
    }
}
