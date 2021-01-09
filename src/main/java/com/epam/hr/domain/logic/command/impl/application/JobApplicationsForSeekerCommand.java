package com.epam.hr.domain.logic.command.impl.application;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.JobApplicationService;
import com.epam.hr.domain.logic.service.VacancyService;
import com.epam.hr.domain.model.*;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JobApplicationsForSeekerCommand implements Command {
    private static final int NUMBER_OF_RECORDS_PER_PAGE = 10;

    private final JobApplicationService jobApplicationService;
    private final VacancyService vacancyService;

    public JobApplicationsForSeekerCommand(JobApplicationService jobApplicationService, VacancyService vacancyService) {
        this.jobApplicationService = jobApplicationService;
        this.vacancyService = vacancyService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String pageParameter = (String) request.getAttribute(Attributes.PAGE);
        int page = pageParameter != null ? Integer.parseInt(pageParameter) : 0;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long idUser = user.getId();

        if (!UserRole.JOB_SEEKER.equals(user.getRole())) {
            return Router.forward(Pages.ACCESS_DENIED);
        }

        try {
            int totalQuantity = jobApplicationService.countUserJobApplications(idUser);

            int numberOfPages = totalQuantity / NUMBER_OF_RECORDS_PER_PAGE;
            numberOfPages = totalQuantity % NUMBER_OF_RECORDS_PER_PAGE == 0 ? numberOfPages : numberOfPages + 1;

            if (numberOfPages != 0 && page >= numberOfPages) {
                page--;
            }

            int from = page * NUMBER_OF_RECORDS_PER_PAGE;

            List<JobApplication> jobApplications = jobApplicationService.findUserApplications(idUser, from, NUMBER_OF_RECORDS_PER_PAGE);
            List<JobApplicationVacancyDto> dtos = new ArrayList<>();
            for (JobApplication jobApplication : jobApplications) {
                long idVacancy = jobApplication.getIdVacancy();
                Optional<Vacancy> vacancyOptional = vacancyService.findById(idVacancy);

                if (!vacancyOptional.isPresent()) {
                    continue;
                }

                Vacancy vacancy = vacancyOptional.get();
                JobApplicationVacancyDto dto = new JobApplicationVacancyDto(jobApplication, vacancy);

                dtos.add(dto);
            }

            request.setAttribute(Attributes.JOB_APPLICATION_DTOS, dtos);
            request.setAttribute(Attributes.NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(Attributes.PAGE, page);

            return Router.forward(Pages.JOB_APPLICATIONS);
        } catch (ServiceException e) {
            return Router.forward(Pages.SERVER_ERROR);
        }
    }
}
