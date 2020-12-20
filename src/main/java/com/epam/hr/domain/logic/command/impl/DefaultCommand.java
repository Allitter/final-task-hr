package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.UserRole;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DefaultCommand implements Command {
    private static final String CONTROLLER_COMMAND_VACANCIES = "/controller?command=vacancies";
    private static final String CONTROLLER_COMMAND_EMPLOYEES = "/controller?command=employees";

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        UserRole role = user.getRole();

        String path;
        switch (role) {
            case JOB_SEEKER :
            case EMPLOYEE :
                path = request.getContextPath() + CONTROLLER_COMMAND_VACANCIES;
                return Router.redirect(path);
            case ADMINISTRATOR :
                path = request.getContextPath() + CONTROLLER_COMMAND_EMPLOYEES;
                return Router.redirect(path);
            default:
                return Router.forward(Pages.SIGN_IN);
        }
    }

}
