package com.epam.hr.domain.controller.command.impl;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class PageNotFoundCommand extends AbstractCommand {

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        return Router.forward(Pages.PAGE_NOT_FOUND);
    }
}
