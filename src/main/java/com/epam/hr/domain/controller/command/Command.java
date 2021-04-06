package com.epam.hr.domain.controller.command;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.exception.ControllerException;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Classes that implement this interface are used to handle requests
 *
 * @see CommandType
 * @see CommandFactory
 * @see com.epam.hr.domain.controller.command.impl.AbstractCommand
 */
public interface Command {

    /**
     * This method is used to handle requests
     *
     * @param request see {@link HttpServletRequest HttpServletRequest}
     * @return {@link Router router}
     * @throws ServiceException    when error occurs while accessing service
     * @throws ControllerException when error occurs inside th command
     */
    Router execute(HttpServletRequest request) throws ServiceException, ControllerException;

}
