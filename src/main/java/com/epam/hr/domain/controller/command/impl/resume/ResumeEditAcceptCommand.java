package com.epam.hr.domain.controller.command.impl.resume;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class ResumeEditAcceptCommand extends AbstractResumeUpdateCommand {
    private static final String CONTROLLER_COMMAND_ACCOUNT = "/controller?command=account";

    public ResumeEditAcceptCommand(ResumeService resumeService) {
        super(resumeService);
    }

    @Override
    protected boolean hasAccess(Resume resume, User user) {
        return resume.getIdUser() == user.getId();
    }

    @Override
    protected void afterCheckActions(HttpServletRequest request, Resume resume) throws ServiceException {
        String name = (String) request.getAttribute(Attributes.RESUME_NAME);
        String resumeText = (String) request.getAttribute(Attributes.LONG_DESCRIPTION);
        resume = resume.changeName(name);
        resume = resume.changeText(resumeText);
        resumeService.saveResume(resume);
    }

    @Override
    protected Router destination(HttpServletRequest request) {
        String path = request.getContextPath() + CONTROLLER_COMMAND_ACCOUNT;
        return Router.redirect(path);
    }

    @Override
    protected Router validationFailDestination() {
        return Router.forward(Pages.RESUME_EDIT);
    }
}
