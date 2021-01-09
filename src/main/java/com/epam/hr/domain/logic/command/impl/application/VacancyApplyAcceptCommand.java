package com.epam.hr.domain.logic.command.impl.application;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.JobApplicationService;
import com.epam.hr.domain.logic.service.ResumeService;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.JobApplicationState;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class VacancyApplyAcceptCommand implements Command {
    private static final String CONTROLLER_COMMAND_JOB_APPLICATIONS_FOR_SEEKER = "/controller?command=job_applications_for_seeker";
    private final JobApplicationService jobApplicationService;
    private final ResumeService resumeService;

    public VacancyApplyAcceptCommand(JobApplicationService jobApplicationService,
                                     ResumeService resumeService) {

        this.jobApplicationService = jobApplicationService;
        this.resumeService = resumeService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            long idVacancy = Long.parseLong((String)request.getAttribute(Attributes.VACANCY_ID));
            long idResume = Long.parseLong((String)request.getAttribute(Attributes.RESUME_ID));
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Attributes.USER);
            long idUser = user.getId();

            Optional<Resume> optionalResume = resumeService.findById(idResume);
            if (!optionalResume.isPresent()) {
                return Router.forward(Pages.PAGE_NOT_FOUND);
            }
            Resume resume = optionalResume.get();
            String resumeText = resume.getText();

            JobApplication jobApplication = new JobApplication.Builder()
                    .setIdUser(idUser)
                    .setIdVacancy(idVacancy)
                    .setState(JobApplicationState.RECENTLY_CREATED)
                    .setResumeText(resumeText)
                    .build();

            jobApplicationService.add(jobApplication);

            String path = request.getContextPath() + CONTROLLER_COMMAND_JOB_APPLICATIONS_FOR_SEEKER;
            return Router.redirect(path);
        } catch (ServiceException e) {
            return Router.forward(Pages.SERVER_ERROR);
        }
    }
}
