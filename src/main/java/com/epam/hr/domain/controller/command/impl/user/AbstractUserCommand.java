package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.impl.AbstractCommand;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.SessionManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public abstract class AbstractUserCommand extends AbstractCommand {

    protected void renewUserInHisSession(Optional<User> optionalUser, HttpServletRequest request) {
        if (optionalUser.isPresent()) {
           User user = optionalUser.get();
           renewUserInHisSession(user, request);
        }
    }

    protected void renewUserInHisSession(User user, HttpServletRequest request) {
        SessionManager sessionManager = (SessionManager) request.getSession()
                .getServletContext().getAttribute(Attributes.SESSION_MANAGER);
        sessionManager.renewUserIfSessionExists(user);
    }

    protected User buildUserFromRequest(HttpServletRequest request) {
        return buildUserFromRequest(request, Optional.empty());
    }

    protected User buildUserFromRequest(HttpServletRequest request, User originUser) {
        return buildUserFromRequest(request, Optional.of(originUser));
    }

    private User buildUserFromRequest(HttpServletRequest request, Optional<User> origin) {
        String password = (String) request.getAttribute(Attributes.PASSWORD);
        password = password == null ? "" : password;
        String login = (String) request.getAttribute(Attributes.LOGIN);
        String name = (String) request.getAttribute(Attributes.NAME);
        String lastName = (String) request.getAttribute(Attributes.LAST_NAME);
        String patronymic = (String) request.getAttribute(Attributes.PATRONYMIC);
        String birthDateString = (String) request.getAttribute(Attributes.BIRTH_DATE);
        String phone = (String) request.getAttribute(Attributes.PHONE);
        String email = (String) request.getAttribute(Attributes.EMAIL);
        User.Builder builder;
        if (origin.isPresent()) {
            User user = origin.get();
            builder = new User.Builder(user);
        } else {
            builder = new User.Builder();
        }

        return builder.setLogin(login)
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
