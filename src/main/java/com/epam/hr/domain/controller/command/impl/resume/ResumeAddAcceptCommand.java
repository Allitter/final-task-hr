package com.epam.hr.domain.controller.command.impl.resume;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ResumeAddAcceptCommand extends AbstractResumeCommand {
    private static final String CONTROLLER_COMMAND_ACCOUNT = "/controller?command=account";
    private final ResumeService service;

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

        try {
            service.addResume(idUser, name, resumeText);

            String path = request.getContextPath() + CONTROLLER_COMMAND_ACCOUNT;
            return Router.redirect(path);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            Resume resume = new Resume.Builder(idUser)
                    .setName(name)
                    .setText(resumeText)
                    .build();
            request.setAttribute(Attributes.RESUME, resume);
            return Router.forward(Pages.RESUME_ADD);
        }
    }
}
