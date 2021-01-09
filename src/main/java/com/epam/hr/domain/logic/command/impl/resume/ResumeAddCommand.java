package com.epam.hr.domain.logic.command.impl.resume;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;

import javax.servlet.http.HttpServletRequest;

public class ResumeAddCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return Router.forward(Pages.RESUME_ADD);
    }
}
