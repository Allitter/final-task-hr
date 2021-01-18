package com.epam.hr.domain.controller.command.impl.resume.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.resume.AbstractResumeCommand;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class ResumeInfoCommand extends AbstractResumeCommand {
    private final ResumeService resumeService;
    private final UserService userService;

    public ResumeInfoCommand(ResumeService resumeService, UserService userService) {
        this.resumeService = resumeService;
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idResume = Long.parseLong((String) request.getAttribute(Attributes.RESUME_ID));
        Resume resume = resumeService.tryFindById(idResume);
        long idUser = resume.getIdUser();
        User user = userService.tryFindById(idUser);
        request.setAttribute(Attributes.RESUME, resume);
        request.setAttribute(Attributes.JOB_SEEKER, user);

        return Router.forward(Pages.RESUME_INFO);
    }
}
