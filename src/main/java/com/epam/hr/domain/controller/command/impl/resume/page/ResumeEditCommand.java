package com.epam.hr.domain.controller.command.impl.resume.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.resume.AbstractResumeUpdateCommand;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;

import javax.servlet.http.HttpServletRequest;

public class ResumeEditCommand extends AbstractResumeUpdateCommand {

    public ResumeEditCommand(ResumeService resumeService) {
        super(resumeService);
    }

    @Override
    protected boolean hasAccess(Resume resume, User user) {
        return resume.getIdUser() == user.getId();
    }

    @Override
    protected void afterCheckActions(HttpServletRequest request, Resume resume) {
        request.setAttribute(Attributes.RESUME, resume);
    }

    @Override
    protected Router destination(HttpServletRequest request) {
        return Router.forward(Pages.RESUME_EDIT);
    }
}
