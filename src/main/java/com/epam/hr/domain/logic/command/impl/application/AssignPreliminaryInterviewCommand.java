package com.epam.hr.domain.logic.command.impl.application;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.JobApplicationService;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.JobApplicationState;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AssignPreliminaryInterviewCommand implements Command {
    private final JobApplicationService jobApplicationService;

    public AssignPreliminaryInterviewCommand(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        long idJobApplication = Long.parseLong((String)request.getAttribute(Attributes.JOB_APPLICATION_ID));

        try {
            Optional<JobApplication> optionalApplication = jobApplicationService.findById(idJobApplication);
            if (!optionalApplication.isPresent()) {
                return Router.forward(Pages.PAGE_NOT_FOUND);
            }

            JobApplication jobApplication = optionalApplication.get();
            jobApplicationService.updateState(jobApplication, JobApplicationState.PRELIMINARY_INTERVIEW);

            String path = request.getHeader(Attributes.REFERER);
            return Router.redirect(path);
        } catch (ServiceException e) {
            return Router.forward(Pages.SERVER_ERROR);
        }
    }
}
