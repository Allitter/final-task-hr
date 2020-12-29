package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class VacancyApplyCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        return null;
    }
}
