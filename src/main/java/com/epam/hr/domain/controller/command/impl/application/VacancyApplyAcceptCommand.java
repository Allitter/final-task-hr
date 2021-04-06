package com.epam.hr.domain.controller.command.impl.application;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.impl.AbstractCommand;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class VacancyApplyAcceptCommand extends AbstractCommand {
    private static final String CONTROLLER_COMMAND_JOB_APPLICATIONS_FOR_SEEKER = "/controller?command=job_applications_for_seeker";
    private final JobApplicationService jobApplicationService;


    public VacancyApplyAcceptCommand(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idVacancy = Long.parseLong((String) request.getAttribute(Attributes.VACANCY_ID));
        long idResume = Long.parseLong((String) request.getAttribute(Attributes.RESUME_ID));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long idUser = user.getId();

        jobApplicationService.addJobApplication(idUser, idVacancy, idResume);

        String path = request.getContextPath() + CONTROLLER_COMMAND_JOB_APPLICATIONS_FOR_SEEKER;
        return Router.redirect(path);
    }
}
