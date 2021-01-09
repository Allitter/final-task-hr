package com.epam.hr.domain.logic.command.impl.application;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.JobApplicationService;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class JobApplicationInfoCommand implements Command {
    private final UserService userService;
    private final JobApplicationService jobApplicationService;

    public JobApplicationInfoCommand(UserService userService,
                                     JobApplicationService jobApplicationService) {
        this.userService = userService;
        this.jobApplicationService = jobApplicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        long idApplication =  Long.parseLong((String)request.getAttribute(Attributes.JOB_APPLICATION_ID));

        try {
            Optional<JobApplication> optionalApplication = jobApplicationService.findById(idApplication);
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

            return Router.forward(Pages.JOB_APPLICATION_INFO);
        } catch (ServiceException e) {
            return Router.forward(Pages.SERVER_ERROR);
        }

    }

}
