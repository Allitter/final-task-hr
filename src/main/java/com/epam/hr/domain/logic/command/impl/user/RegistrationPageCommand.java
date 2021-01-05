package com.epam.hr.domain.logic.command.impl.user;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class RegistrationPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String path = Pages.REGISTRATION;
        return Router.forward(path);
    }
}
