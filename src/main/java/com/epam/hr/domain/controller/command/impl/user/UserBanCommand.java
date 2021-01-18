package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UserBanCommand extends AbstractUserCommand {
    private final UserService service;

    public UserBanCommand(UserService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long id = Long.parseLong((String)request.getAttribute(Attributes.USER_ID));

        Optional<User> optionalUser = service.banUser(id);
        renewUserInHisSession(optionalUser, request);

        String previousPage = (String) request.getAttribute(Attributes.PREVIOUS_PAGE);
        String path = previousPage != null ? previousPage : request.getHeader(Attributes.REFERER);
        return Router.redirect(path);
    }
}
