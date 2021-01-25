package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.EntityNotFoundException;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class UserBanCommand extends AbstractUserCommand {
    private final UserService service;

    public UserBanCommand(UserService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        if (user.getRole() != User.Role.ADMINISTRATOR) {
            return Router.forward(Pages.ACCESS_DENIED);
        }

        long idAdministrant = user.getId();
        long idTarget = Long.parseLong((String) request.getAttribute(Attributes.USER_ID));
        String message = (String) request.getAttribute(Attributes.MESSAGE);

        Optional<User> optionalUser = service.banUser(idTarget, idAdministrant, message);
        user = optionalUser.orElseThrow(EntityNotFoundException::new);
        renewUserInHisSession(user, request);

        String previousPage = (String) request.getAttribute(Attributes.PREVIOUS_PAGE);
        String path = previousPage != null ? previousPage : request.getHeader(Attributes.REFERER);
        return Router.redirect(path);
    }
}
