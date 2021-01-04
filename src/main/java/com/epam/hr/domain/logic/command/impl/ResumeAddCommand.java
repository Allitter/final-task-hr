package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class ResumeAddCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        return Router.forward(Pages.RESUME_ADD);
    }
}
