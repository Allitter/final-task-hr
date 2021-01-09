package com.epam.hr.domain.logic.command;

import com.epam.hr.domain.logic.Router;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    Router execute(HttpServletRequest request);

}
