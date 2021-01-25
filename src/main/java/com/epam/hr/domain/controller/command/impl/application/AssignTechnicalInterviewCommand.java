package com.epam.hr.domain.controller.command.impl.application;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class AssignTechnicalInterviewCommand extends AbstractJobApplicationCommand {
    private final JobApplicationService jobApplicationService;

    public AssignTechnicalInterviewCommand(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idJobApplication = Long.parseLong((String) request.getAttribute(Attributes.JOB_APPLICATION_ID));

        jobApplicationService.updateState(idJobApplication, JobApplication.State.TECHNICAL_INTERVIEW);

        String path = request.getHeader(Attributes.REFERER);
        return Router.redirect(path);
    }
}
