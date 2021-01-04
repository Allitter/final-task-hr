package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.ApplicationService;
import com.epam.hr.domain.logic.service.ResumeService;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class JobApplicationInfoCommand implements Command {
    private final UserService userService;
    private final ResumeService resumeService;
    private final ApplicationService applicationService;

    public JobApplicationInfoCommand(UserService userService,
                                     ResumeService resumeService,
                                     ApplicationService applicationService) {
        this.userService = userService;
        this.resumeService = resumeService;
        this.applicationService = applicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        // extract application id
        long idApplication =  Long.parseLong((String)request.getAttribute(Attributes.JOB_APPLICATION_ID));

        // get application from service
        Optional<JobApplication> optionalApplication = applicationService.findById(idApplication);
        if (!optionalApplication.isPresent()) {
            return Router.forward(Pages.PAGE_NOT_FOUND);
        }

        JobApplication jobApplication = optionalApplication.get();
        request.setAttribute(Attributes.JOB_APPLICATION, jobApplication);

        long idUser = jobApplication.getIdUser();
        Optional<User> optionalUser = userService.findById(idUser);
        if (!optionalUser.isPresent()) {
            return Router.forward(Pages.SERVER_ERROR);
        }

        User user = optionalUser.get();
        request.setAttribute(Attributes.JOB_SEEKER, user);

        long idResume = jobApplication.getIdResume();
        Optional<Resume> optionalResume = resumeService.findById(idResume);
        if (!optionalResume.isPresent()) {
            return Router.forward(Pages.SERVER_ERROR);
        }

        Resume resume = optionalResume.get();
        request.setAttribute(Attributes.RESUME, resume);

        // forward to job application info

        return Router.forward(Pages.JOB_APPLICATION_INFO);
    }

}