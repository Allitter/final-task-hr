package com.epam.hr.domain.controller.command;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    Router execute(HttpServletRequest request) throws ServiceException;

}
