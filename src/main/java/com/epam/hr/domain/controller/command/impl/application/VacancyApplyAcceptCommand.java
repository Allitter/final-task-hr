package com.epam.hr.domain.controller.command.impl.application;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class VacancyApplyAcceptCommand extends AbstractJobApplicationCommand {
    private static final String CONTROLLER_COMMAND_JOB_APPLICATIONS_FOR_SEEKER = "/controller?command=job_applications_for_seeker";
    private final JobApplicationService jobApplicationService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

    public VacancyApplyAcceptCommand(JobApplicationService jobApplicationService,
                                     ResumeService resumeService, VacancyService vacancyService) {
        this.jobApplicationService = jobApplicationService;
        this.resumeService = resumeService;
        this.vacancyService = vacancyService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idVacancy = Long.parseLong((String)request.getAttribute(Attributes.VACANCY_ID));
        long idResume = Long.parseLong((String)request.getAttribute(Attributes.RESUME_ID));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        Vacancy vacancy = vacancyService.tryFindById(idVacancy);

        Resume resume = resumeService.tryFindById(idResume);
        String resumeText = resume.getText();

        jobApplicationService.addJobApplication(user, vacancy, resumeText);

        String path = request.getContextPath() + CONTROLLER_COMMAND_JOB_APPLICATIONS_FOR_SEEKER;
        return Router.redirect(path);
    }
}
