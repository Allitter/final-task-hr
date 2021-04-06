package com.epam.hr.domain.controller.command.impl.resume.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.resume.AbstractResumeCommand;

import javax.servlet.http.HttpServletRequest;

public class ResumeAddCommand extends AbstractResumeCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        return Router.forward(Pages.RESUME_ADD);
    }

}
