package com.epam.hr.domain.logic.command.impl.application;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.JobApplicationService;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.logic.service.VacancyService;
import com.epam.hr.domain.model.*;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobApplicationsForVacancyCommand implements Command {
    private static final int NUMBER_OF_RECORDS_PER_PAGE = 10;

    private final JobApplicationService jobApplicationService;
    private final VacancyService vacancyService;
    private final UserService userService;

    public JobApplicationsForVacancyCommand(JobApplicationService jobApplicationService, VacancyService vacancyService, UserService userService) {
        this.jobApplicationService = jobApplicationService;
        this.vacancyService = vacancyService;
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String pageParameter = (String) request.getAttribute(Attributes.PAGE);
        int page = pageParameter != null ? Integer.parseInt(pageParameter) : 0;
        long idVacancy = Long.parseLong((String)request.getAttribute(Attributes.VACANCY_ID));

        Optional<Vacancy> optionalVacancy = vacancyService.findById(idVacancy);
        if (!optionalVacancy.isPresent()) {
            return Router.forward(Pages.PAGE_NOT_FOUND);
        }
        Vacancy vacancy = optionalVacancy.get();

        int totalQuantity = jobApplicationService.countUserJobApplications(idVacancy);
        int numberOfPages = totalQuantity / NUMBER_OF_RECORDS_PER_PAGE;
        numberOfPages = totalQuantity % NUMBER_OF_RECORDS_PER_PAGE == 0 ? numberOfPages : numberOfPages + 1;

        if (page >= numberOfPages) {
            page--;
        }

        int from = page * NUMBER_OF_RECORDS_PER_PAGE;
        List<JobApplication> jobApplications = jobApplicationService.findApplicationsByVacancy(idVacancy, from, NUMBER_OF_RECORDS_PER_PAGE);
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
        request.setAttribute(Attributes.NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(Attributes.PAGE, page);

        return Router.forward(Pages.JOB_APPLICATIONS_VACANCY);
    }
}
