package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.VerificationToken;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.domain.service.VerificationTokenService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

public class VerificationCommand extends AbstractUserCommand {
    private static final String INCORRECT_CODE = "incorrectCode";
    private final VerificationTokenService verificationTokenService;
    private final UserService userService;

    public VerificationCommand(UserService userService,
                               VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String code = (String) request.getAttribute(Attributes.VERIFICATION_CODE);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long idUser = user.getId();

        List<VerificationToken> tokens = verificationTokenService.findUserTokens(idUser);
        for (VerificationToken token : tokens) {
            if (token.getCode().equals(code)) {
                user = userService.enable(idUser);
                session.setAttribute(Attributes.USER, user);

                String path = request.getContextPath() + request.getServletPath();
                return Router.redirect(path);
            }
        }

        request.setAttribute(Attributes.FAILS, Collections.singletonList(INCORRECT_CODE));
        String path = request.getHeader(Attributes.REFERER);
        return Router.redirect(path);
    }
}
