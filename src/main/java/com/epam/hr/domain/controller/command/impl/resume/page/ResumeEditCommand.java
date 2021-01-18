package com.epam.hr.domain.controller.command.impl.resume.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.resume.AbstractResumeEditCommand;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.service.ResumeService;

import javax.servlet.http.HttpServletRequest;

public class ResumeEditCommand extends AbstractResumeEditCommand {

    public ResumeEditCommand(ResumeService resumeService) {
        super(resumeService);
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
