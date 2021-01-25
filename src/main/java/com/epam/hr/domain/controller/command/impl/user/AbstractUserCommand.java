package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.impl.AbstractCommand;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.SessionManager;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractUserCommand extends AbstractCommand {

    protected void renewUserInHisSession(User user, HttpServletRequest request) {
        SessionManager sessionManager = (SessionManager) request.getSession()
                .getServletContext().getAttribute(Attributes.SESSION_MANAGER);
        sessionManager.renewUserIfSessionExists(user);
    }

    protected User buildUserFromRequest(HttpServletRequest request) {
        return buildUserFromRequest(request, User.DEFAULT);
    }

    protected User buildUserFromRequest(HttpServletRequest request, User user) {
        String password = (String) request.getAttribute(Attributes.PASSWORD);
        password = password == null ? "" : password;
        String login = (String) request.getAttribute(Attributes.LOGIN);
        String name = (String) request.getAttribute(Attributes.NAME);
        String lastName = (String) request.getAttribute(Attributes.LAST_NAME);
        String patronymic = (String) request.getAttribute(Attributes.PATRONYMIC);
        String birthDateString = (String) request.getAttribute(Attributes.BIRTH_DATE);
        String phone = (String) request.getAttribute(Attributes.PHONE);
        String email = (String) request.getAttribute(Attributes.EMAIL);

        return new User.Builder(user)
                .setLogin(login)
                .setPassword(password)
                .setName(name)
                .setLastName(lastName)
                .setPatronymic(patronymic)
                .setEmail(email)
                .setPhone(phone)
                .setBirthDate(birthDateString)
                .build();
    }

}
