package com.epam.hr.domain.controller.command.impl.resume;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class ResumeRemoveCommand extends AbstractResumeEditCommand {

    public ResumeRemoveCommand(ResumeService resumeService) {
        super(resumeService);
    }

    @Override
    protected void afterCheckActions(HttpServletRequest request, Resume resume) throws ServiceException {
        long id = Long.parseLong((String) request.getAttribute(Attributes.RESUME_ID));
        resumeService.remove(id);
    }

    @Override
    protected Router validationFailDestination() {
        return Router.forward(Pages.RESUME_EDIT);
    }
}
