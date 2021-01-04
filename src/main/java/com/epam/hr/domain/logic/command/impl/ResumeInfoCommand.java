package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.ResumeService;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ResumeInfoCommand implements Command {
    private final ResumeService resumeService;
    private final UserService userService;

    public ResumeInfoCommand(ResumeService resumeService, UserService userService) {
        this.resumeService = resumeService;
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long resumeId = Long.parseLong((String) request.getAttribute(Attributes.RESUME_ID));

        Optional<Resume> optional = resumeService.findById(resumeId);

        if (!optional.isPresent()) {
            return Router.forward(Pages.PAGE_NOT_FOUND);
        }

        Resume resume = optional.get();
        long userId = resume.getIdUser();

        Optional<User> optionalUser = userService.findById(userId);

        if (!optionalUser.isPresent()) {
            return Router.forward(Pages.SERVER_ERROR);
        }

        User user = optionalUser.get();

        request.setAttribute(Attributes.RESUME, resume);
        request.setAttribute(Attributes.JOB_SEEKER, user);

        return Router.forward(Pages.RESUME_INFO);
    }


}
