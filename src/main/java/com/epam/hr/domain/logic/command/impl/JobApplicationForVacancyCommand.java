package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.ApplicationService;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.logic.service.VacancyService;
import com.epam.hr.domain.model.*;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobApplicationForVacancyCommand implements Command {
    private final ApplicationService applicationService;
    private final VacancyService vacancyService;
    private final UserService userService;

    public JobApplicationForVacancyCommand(ApplicationService applicationService, VacancyService vacancyService, UserService userService) {
        this.applicationService = applicationService;
        this.vacancyService = vacancyService;
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        // todo add pagination
        long vacancyId = Long.parseLong((String)request.getAttribute(Attributes.VACANCY_ID));

        Optional<Vacancy> optionalVacancy = vacancyService.findById(vacancyId);
        if (!optionalVacancy.isPresent()) {
            return Router.forward(Pages.PAGE_NOT_FOUND);
        }

        Vacancy vacancy = optionalVacancy.get();
        List<JobApplication> jobApplications = applicationService.findApplicationsByVacancy(vacancyId);
        List<JobApplicationVacancyDto> dtos = new ArrayList<>();
        for (JobApplication jobApplication : jobApplications) {
            long idUser = jobApplication.getIdUser();
            Optional<User> userOptional = userService.findById(idUser);

            if (!userOptional.isPresent()) {
                continue;
            }

            User user = userOptional.get();
            String vacancyName = vacancy.getName();
            String vacancyShortDescription = vacancy.getShortDescription();
            JobApplicationDto dto = new JobApplicationDto(jobApplication, vacancyName, vacancyShortDescription);

            String userName = user.getName();
            String userLastName = user.getLastName();
            String userPatronymic = user.getPatronymic();
            JobApplicationVacancyDto vacancyDto = new JobApplicationVacancyDto(dto, userName, userLastName, userPatronymic);

            dtos.add(vacancyDto);
        }

        request.setAttribute(Attributes.JOB_APPLICATION_DTOS, dtos);

        return Router.forward(Pages.JOB_APPLICATIONS_VACANCY);
    }
}
