package com.epam.hr.domain.logic.command.impl.application;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.JobApplicationService;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UpdatePreliminaryNoteCommand implements Command {
    private final JobApplicationService jobApplicationService;

    public UpdatePreliminaryNoteCommand(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }


    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idJobApplication = Long.parseLong((String)request.getAttribute(Attributes.JOB_APPLICATION_ID));

        Optional<JobApplication> optionalJobApplication = jobApplicationService.findById(idJobApplication);
        if (!optionalJobApplication.isPresent()) {
            return Router.forward(Pages.PAGE_NOT_FOUND);
        }

        JobApplication jobApplication = optionalJobApplication.get();
        String preliminaryNote = (String)request.getAttribute(Attributes.PRELIMINARY_INTERVIEW_NOTE);
        jobApplication = jobApplication.changePreliminaryNote(preliminaryNote);

        jobApplicationService.save(jobApplication);
        request.setAttribute(Attributes.JOB_APPLICATION, jobApplication);

        String path = request.getHeader(Attributes.REFERER);
        return Router.redirect(path);
    }

}
