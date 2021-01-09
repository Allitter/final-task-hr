package com.epam.hr.domain.logic.command.impl.resume;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.service.ResumeService;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class ResumeRemoveCommand extends AbstractResumeUpdateCommand {
    private static final String CONTROLLER_COMMAND_ACCOUNT = "/controller?command=account";

    public ResumeRemoveCommand(ResumeService resumeService) {
        super(resumeService);
    }

    @Override
    protected boolean hasAccess(Resume resume, User user) {
        return resume.getIdUser() == user.getId();
    }

    @Override
    protected void afterCheckActions(HttpServletRequest request, Resume resume) throws ServiceException {
        long idResume = resume.getId();
        resumeService.remove(idResume);
    }

    @Override
    protected Router destination(HttpServletRequest request) {
        String path = request.getContextPath() + CONTROLLER_COMMAND_ACCOUNT;
        return Router.redirect(path);
    }
}
