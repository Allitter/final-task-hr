package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AccountUpdateCommand extends AbstractUserCommand {
    private final UserService service;
    private final ResumeService resumeService;

    public AccountUpdateCommand(UserService service, ResumeService resumeService) {
        this.service = service;
        this.resumeService = resumeService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        User updatedUser = buildUserFromRequest(request, user);

        try {
            service.updateUser(updatedUser);
            session.setAttribute(Attributes.USER, updatedUser);

            String path = request.getHeader(Attributes.REFERER);
            return Router.redirect(path);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            List<Resume> resumes = resumeService.findUserResumes(updatedUser.getId());
            request.setAttribute(Attributes.RESUMES, resumes);
            request.setAttribute(Attributes.FAILS, fails);
            return Router.forward(Pages.ACCOUNT);
        }
    }
}
