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
import java.util.Optional;

public abstract class AbstractResumeUpdateCommand implements Command {
    protected final ResumeService resumeService;

    protected AbstractResumeUpdateCommand(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long resumeId = Long.parseLong((String) request.getAttribute(Attributes.RESUME_ID));

        Optional<Resume> optional = resumeService.findById(resumeId);
        if (!optional.isPresent()) {
            return Router.forward(Pages.PAGE_NOT_FOUND);
        }

        Resume resume = optional.get();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        if (!hasAccess(resume, user)) {
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

    protected abstract boolean hasAccess(Resume resume, User user);
    protected abstract void afterCheckActions(HttpServletRequest request, Resume resume) throws ServiceException;
    protected abstract Router destination(HttpServletRequest request);

    protected Router validationFailDestination() {
        return Router.forward(Pages.SERVER_ERROR);
    }
}
