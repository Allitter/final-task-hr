package com.epam.hr.domain.logic.command.impl.user;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.MailingService;
import com.epam.hr.domain.logic.service.VerificationTokenService;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.VerificationToken;
import com.epam.hr.domain.util.VerificationCodeGenerator;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class VerificationPageCommand implements Command {
    public static final String MESSAGE_SUBJECT = "Authentication";
    public static final String MESSAGE_TEXT = "Email authentication code: ";

    private final MailingService mailingService;
    private final VerificationTokenService verificationTokenService;
    private final VerificationCodeGenerator verificationCodeGenerator;

    public VerificationPageCommand(MailingService mailingService,
                                   VerificationTokenService verificationTokenService,
                                   VerificationCodeGenerator verificationCodeGenerator) {
        this.mailingService = mailingService;
        this.verificationTokenService = verificationTokenService;
        this.verificationCodeGenerator = verificationCodeGenerator;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long idUser = user.getId();
        String email = user.getEmail();

        try {
            verificationTokenService.removeExpiredTokens(idUser);
            List<VerificationToken> tokens = verificationTokenService.findUserTokens(idUser);
            if (tokens.isEmpty()) {
                String code = verificationCodeGenerator.generate();
                VerificationToken token = new VerificationToken(idUser, code);
                verificationTokenService.save(token);

                mailingService.sendMessageTo(MESSAGE_SUBJECT, MESSAGE_TEXT + code, email);
            }

            return Router.forward(Pages.VERIFICATION);
        } catch (ServiceException e) {
            return Router.forward(Pages.SERVER_ERROR);
        }
    }
}
