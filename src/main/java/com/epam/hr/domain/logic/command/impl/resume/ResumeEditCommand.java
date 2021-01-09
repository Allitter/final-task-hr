package com.epam.hr.domain.logic.command.impl.resume;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.ResumeService;
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
