package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.ApplicationService;
import com.epam.hr.domain.logic.service.VacancyService;
import com.epam.hr.domain.model.*;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobApplicationsCommand implements Command {
    private final ApplicationService applicationService;
    private final VacancyService vacancyService;

    public JobApplicationsCommand(ApplicationService applicationService, VacancyService vacancyService) {
        this.applicationService = applicationService;
        this.vacancyService = vacancyService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long userId = user.getId();

        // todo add pagination

        if (!UserRole.JOB_SEEKER.equals(user.getRole())) {
            return Router.forward(Pages.ACCESS_DENIED);
        }

        List<JobApplication> jobApplications = applicationService.findUserApplications(userId);
        List<JobApplicationDto> dtos = new ArrayList<>();
        for (JobApplication jobApplication : jobApplications) {
            long idVacancy = jobApplication.getIdVacancy();
            Optional<Vacancy> vacancyOptional = vacancyService.findById(idVacancy);

            if (!vacancyOptional.isPresent()) {
                continue;
            }

            Vacancy vacancy = vacancyOptional.get();
            String vacancyName = vacancy.getName();
            String vacancyShortDescription = vacancy.getShortDescription();
            JobApplicationDto dto = new JobApplicationDto(jobApplication, vacancyName, vacancyShortDescription);

            dtos.add(dto);
        }

        request.setAttribute(Attributes.JOB_APPLICATION_DTOS, dtos);

        return Router.forward(Pages.JOB_APPLICATIONS);
    }
}
