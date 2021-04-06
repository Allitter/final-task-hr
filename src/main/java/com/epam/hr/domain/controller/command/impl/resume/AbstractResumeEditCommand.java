package com.epam.hr.domain.controller.command.impl.resume;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class AbstractResumeEditCommand extends AbstractResumeCommand {
    protected final ResumeService resumeService;

    protected AbstractResumeEditCommand(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idResume = Long.parseLong((String) request.getAttribute(Attributes.RESUME_ID));

        Resume resume = resumeService.tryFindById(idResume);
        if (!isCurrentUserResumeOwner(request, resume)) {
            return Router.forward(Pages.ACCESS_DENIED);
        }

        try {
            afterCheckActions(request, resume);

            return destination(request);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            request.setAttribute(Attributes.RESUME, resume);
            return validationFailDestination();
        }
    }

    protected abstract void afterCheckActions(HttpServletRequest request, Resume resume) throws ServiceException;

    protected Router validationFailDestination() {
        return Router.forward(Pages.SERVER_ERROR);
    }

    protected Router destination(HttpServletRequest request) {
        String path = request.getContextPath() + "/controller?command=account";
        return Router.redirect(path);
    }
}
