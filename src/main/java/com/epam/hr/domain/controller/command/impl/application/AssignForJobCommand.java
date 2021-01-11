package com.epam.hr.domain.controller.command.impl.application;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.JobApplicationState;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AssignForJobCommand implements Command {
    private final JobApplicationService jobApplicationService;

    public AssignForJobCommand(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idJobApplication = Long.parseLong((String)request.getAttribute(Attributes.JOB_APPLICATION_ID));

        Optional<JobApplication> optionalApplication = jobApplicationService.findById(idJobApplication);
        if (!optionalApplication.isPresent()) {
            return Router.forward(Pages.PAGE_NOT_FOUND);
        }

        JobApplication jobApplication = optionalApplication.get();
        jobApplicationService.updateState(jobApplication, JobApplicationState.APPLIED);

        String path = request.getHeader(Attributes.REFERER);
        return Router.redirect(path);
    }
}
