package com.epam.hr.domain.logic.command.impl.user;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.UserRole;
import com.epam.hr.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountUpdateCommand implements Command {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final boolean DEFAULT_IS_BANNED = false;
    private final UserService service;

    public AccountUpdateCommand(UserService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        // extract user fields
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        UserRole role = user.getRole();
        String login = (String) request.getAttribute(Attributes.LOGIN);
        String name = (String) request.getAttribute(Attributes.RESUME_NAME);
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
            return Router.forward(Pages.ACCOUNT);
        }

        // TODO send email code
        // todo use builder
        User updatedUser = new User(user.getId(), role, login, "", name, lastName, patronymic, birthDate, DEFAULT_IS_BANNED);
        service.saveUser(updatedUser);

        long id = user.getId();
        user = service.findById(id).get();
        session.setAttribute(Attributes.USER, user);
        // send to login page
        String path = request.getHeader(Attributes.REFERER);
        return Router.redirect(path);
    }
}
