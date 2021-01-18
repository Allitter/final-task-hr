package com.epam.hr.domain.controller.command.impl.application;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UpdatePreliminaryNoteCommand extends AbstractJobApplicationCommand {
    private final JobApplicationService jobApplicationService;

    public UpdatePreliminaryNoteCommand(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idJobApplication = Long.parseLong((String)request.getAttribute(Attributes.JOB_APPLICATION_ID));
        String preliminaryInterviewNote = (String)request.getAttribute(Attributes.PRELIMINARY_INTERVIEW_NOTE);

        try {
            jobApplicationService.updatePreliminaryInterviewNote(idJobApplication, preliminaryInterviewNote);

            String path = request.getHeader(Attributes.REFERER);
            return Router.redirect(path);
        }  catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            JobApplication jobApplication = jobApplicationService.tryFindById(idJobApplication);
            jobApplication = new JobApplication.Builder(jobApplication)
                    .setPreliminaryInterviewNote(preliminaryInterviewNote).build();
            request.setAttribute(Attributes.JOB_APPLICATION, jobApplication);
            return Router.forward(Pages.JOB_APPLICATION_INFO);
        }
    }

}
