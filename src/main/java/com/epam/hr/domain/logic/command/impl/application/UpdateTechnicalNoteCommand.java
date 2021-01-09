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

public class UpdateTechnicalNoteCommand implements Command {
    private final JobApplicationService jobApplicationService;

    public UpdateTechnicalNoteCommand(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        long idJobApplication = Long.parseLong((String)request.getAttribute(Attributes.JOB_APPLICATION_ID));

        try {
            Optional<JobApplication> optionalJobApplication = jobApplicationService.findById(idJobApplication);
            if (!optionalJobApplication.isPresent()) {
                return Router.forward(Pages.PAGE_NOT_FOUND);
            }

            JobApplication jobApplication = optionalJobApplication.get();
            String technicalInterviewNote = (String)request.getAttribute(Attributes.TECHNICAL_INTERVIEW_NOTE);

            jobApplication = new JobApplication.Builder(jobApplication)
                    .setTechnicalInterviewNote(technicalInterviewNote)
                    .build();

            jobApplicationService.updateTechnicalInterviewNote(jobApplication);

            String path = request.getHeader(Attributes.REFERER);
            return Router.redirect(path);
        }  catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            return Router.forward(Pages.JOB_APPLICATION_INFO);
        } catch (ServiceException e) {
            return Router.forward(Pages.SERVER_ERROR);
        }
    }
}
