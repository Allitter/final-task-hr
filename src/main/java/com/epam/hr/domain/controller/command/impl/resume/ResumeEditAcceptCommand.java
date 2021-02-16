package com.epam.hr.domain.controller.command.impl.resume;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class ResumeEditAcceptCommand extends AbstractResumeEditCommand {

    public ResumeEditAcceptCommand(ResumeService resumeService) {
        super(resumeService);
    }

    @Override
    protected void afterCheckActions(HttpServletRequest request, Resume resume) throws ServiceException {
        long id = resume.getId();
        String name = (String) request.getAttribute(Attributes.RESUME_NAME);
        String resumeText = (String) request.getAttribute(Attributes.LONG_DESCRIPTION);

        resumeService.updateResume(id, name, resumeText);
    }

    @Override
    protected Router validationFailDestination() {
        return Router.forward(Pages.RESUME_EDIT);
    }
}
