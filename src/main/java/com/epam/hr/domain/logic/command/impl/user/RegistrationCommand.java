package com.epam.hr.domain.logic.command.impl.user;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.UserRole;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RegistrationCommand implements Command {
    public static final String VERIFICATION_PAGE_COMMAND = "?command=verification_page";
    private static final String DATE_FORMAT = "yyyy-mm-dd";
    private final UserService service;


    public RegistrationCommand(UserService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String login = (String) request.getAttribute(Attributes.LOGIN);
        String password = (String) request.getAttribute(Attributes.PASSWORD);
        String name = (String) request.getAttribute(Attributes.NAME);
        String lastName = (String) request.getAttribute(Attributes.LAST_NAME);
        String patronymic = (String) request.getAttribute(Attributes.PATRONYMIC);
        String birthDateString = (String) request.getAttribute(Attributes.BIRTH_DATE);
        String phone = (String) request.getAttribute(Attributes.PHONE);
        String email = (String) request.getAttribute(Attributes.EMAIL);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        try {
            Date birthDate;
            try {
                birthDate = dateFormat.parse(birthDateString);
            } catch (ParseException e) {
                throw new ServiceException("Invalid date format");
            }

            User user = new User.Builder()
                    .setRole(UserRole.JOB_SEEKER)
                    .setLogin(login)
                    .setPassword(password)
                    .setName(name)
                    .setLastName(lastName)
                    .setPatronymic(patronymic)
                    .setEmail(email)
                    .setPhone(phone)
                    .setBirthDate(birthDate)
                    .setEnabled(false)
                    .build();

            user = service.saveUser(user);
            HttpSession session = request.getSession();
            session.setAttribute(Attributes.USER, user);

            String path = request.getContextPath() + request.getServletPath() + VERIFICATION_PAGE_COMMAND;
            return Router.redirect(path);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            return Router.forward(Pages.REGISTRATION);
        } catch (ServiceException e) {
                return Router.forward(Pages.SERVER_ERROR);
        }
    }
}
