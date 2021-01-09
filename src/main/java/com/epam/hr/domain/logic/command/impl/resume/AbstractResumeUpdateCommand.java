package com.epam.hr.domain.logic.command.impl.resume;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.ResumeService;
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

    public AbstractResumeUpdateCommand(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        long resumeId = Long.parseLong((String) request.getAttribute(Attributes.RESUME_ID));

        Resume resume = null;
        try {
            Optional<Resume> optional = resumeService.findById(resumeId);

            if (!optional.isPresent()) {
                return Router.forward(Pages.PAGE_NOT_FOUND);
            }
            resume = optional.get();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Attributes.USER);
            if (!hasAccess(resume, user)) {
                return Router.forward(Pages.ACCESS_DENIED);
            }

            afterCheckActions(request, resume);

            return destination(request);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            request.setAttribute(Attributes.RESUME, resume);
            return validationFailDestination();
        } catch (ServiceException e) {
            return Router.forward(Pages.SERVER_ERROR);
        }
    }

    protected abstract boolean hasAccess(Resume resume, User user);
    protected abstract void afterCheckActions(HttpServletRequest request, Resume resume) throws ServiceException;
    protected abstract Router destination(HttpServletRequest request);

    protected Router validationFailDestination() {
        return Router.forward(Pages.SERVER_ERROR);
    }
}
