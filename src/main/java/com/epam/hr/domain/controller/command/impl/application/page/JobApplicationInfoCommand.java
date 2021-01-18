package com.epam.hr.domain.controller.command.impl.application.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.application.AbstractJobApplicationCommand;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class JobApplicationInfoCommand extends AbstractJobApplicationCommand {
    private final UserService userService;
    private final JobApplicationService jobApplicationService;

    public JobApplicationInfoCommand(UserService userService,
                                     JobApplicationService jobApplicationService) {
        this.userService = userService;
        this.jobApplicationService = jobApplicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idJobApplication =  Long.parseLong((String)request.getAttribute(Attributes.JOB_APPLICATION_ID));

        JobApplication jobApplication = jobApplicationService.tryFindById(idJobApplication);
        request.setAttribute(Attributes.JOB_APPLICATION, jobApplication);

        long idUser = jobApplication.getIdUser();
        User user = userService.tryFindById(idUser);
        request.setAttribute(Attributes.JOB_SEEKER, user);

        return Router.forward(Pages.JOB_APPLICATION_INFO);
    }
}
