package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RegistrationCommand extends AbstractUserCommand {
    public static final String VERIFICATION_PAGE_COMMAND = "?command=verification_page";
    private final UserService service;


    public RegistrationCommand(UserService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        User user = buildUserFromRequest(request);
        try {
            user = service.addUser(user);
            HttpSession session = request.getSession();
            session.setAttribute(Attributes.USER, user);

            String path = request.getContextPath() + request.getServletPath() + VERIFICATION_PAGE_COMMAND;
            return Router.redirect(path);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            return Router.forward(Pages.REGISTRATION);
        }
    }
}
