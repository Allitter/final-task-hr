package com.epam.hr.domain.controller.command.impl.resume;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ResumeAddAcceptCommand implements Command {
    private static final String COMMAND_ACCOUNT = "?command=account";
    private final ResumeService service;
    private static final int DEFAULT_ID = -1;

    public ResumeAddAcceptCommand(ResumeService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String name = (String) request.getAttribute(Attributes.RESUME_NAME);
        String resumeText = (String) request.getAttribute(Attributes.LONG_DESCRIPTION);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long idUser = user.getId();
        Resume resume = new Resume(DEFAULT_ID, idUser, name, resumeText);

        try {
            service.saveResume(resume);

            String path = request.getContextPath() + request.getServletPath() + COMMAND_ACCOUNT;
            return Router.redirect(path);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            request.setAttribute(Attributes.RESUME, resume);
            return Router.forward(Pages.RESUME_ADD);
        }
    }

}
