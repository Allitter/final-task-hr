package com.epam.hr.domain.logic.command.impl.application;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.JobApplicationService;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class UpdatePreliminaryNoteCommand implements Command {
    private final JobApplicationService jobApplicationService;

    public UpdatePreliminaryNoteCommand(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        long idJobApplication = Long.parseLong((String)request.getAttribute(Attributes.JOB_APPLICATION_ID));

        JobApplication jobApplication = null;
        try {
            Optional<JobApplication> optionalJobApplication = jobApplicationService.findById(idJobApplication);
            if (!optionalJobApplication.isPresent()) {
                return Router.forward(Pages.PAGE_NOT_FOUND);
            }

            jobApplication = optionalJobApplication.get();
            String preliminaryInterviewNote = (String)request.getAttribute(Attributes.PRELIMINARY_INTERVIEW_NOTE);

            jobApplication = new JobApplication.Builder(jobApplication)
                    .setPreliminaryInterviewNote(preliminaryInterviewNote)
                    .build();

            jobApplicationService.updatePreliminaryInterviewNote(jobApplication);

            String path = request.getHeader(Attributes.REFERER);
            return Router.redirect(path);
        }  catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            request.setAttribute(Attributes.JOB_APPLICATION, jobApplication);
            return Router.forward(Pages.JOB_APPLICATION_INFO);
        } catch (ServiceException e) {
            return Router.forward(Pages.SERVER_ERROR);
        }
    }

}
