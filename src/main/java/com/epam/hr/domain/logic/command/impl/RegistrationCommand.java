package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.UserRole;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class RegistrationCommand implements Command {
    private static final String DATE_FORMAT = "yyyy-mm-dd";
    private final UserService service;

    public RegistrationCommand(UserService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        // extract user fields
        UserRole role = UserRole.JOB_SEEKER;
        String login = (String) request.getAttribute(Attributes.LOGIN);
        String password = (String) request.getAttribute(Attributes.PASSWORD);
        String name = (String) request.getAttribute(Attributes.NAME);
        String lastName = (String) request.getAttribute(Attributes.LAST_NAME);
        String patronymic = (String) request.getAttribute(Attributes.PATRONYMIC);
        String birthDateString = (String) request.getAttribute(Attributes.BIRTH_DATE);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        Date birthDate;
        try {
            birthDate = dateFormat.parse(birthDateString);
        } catch (ParseException e) {
            throw new ServiceException("Invalid date format");
        }

        // TODO validate

        // check login for uniqueness
        if (!service.isLoginUnique(login)) {
            request.setAttribute(Attributes.SHOW_NONE_UNIQUE_LOGIN_MESSAGE, true);
            return Router.forward(Pages.LOGIN);
        }

        // TODO send email code
        User user = new User(-1, role, login, password, name, lastName, patronymic, birthDate);
        service.saveUser(user);

        // send to login page

        return Router.forward(Pages.LOGIN);
    }
}
