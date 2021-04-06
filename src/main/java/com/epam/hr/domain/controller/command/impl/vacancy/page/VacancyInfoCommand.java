package com.epam.hr.domain.controller.command.impl.vacancy.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.vacancy.AbstractVacancyCommand;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class VacancyInfoCommand extends AbstractVacancyCommand {
    private final VacancyService vacancyService;
    private final JobApplicationService jobApplicationService;

    public VacancyInfoCommand(VacancyService vacancyService, JobApplicationService jobApplicationService) {
        this.vacancyService = vacancyService;
        this.jobApplicationService = jobApplicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idVacancy = Long.parseLong((String) request.getAttribute(Attributes.VACANCY_ID));
        Vacancy vacancy = vacancyService.tryFindById(idVacancy);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long idUser = user.getId();
        if (user.getRole() == User.Role.JOB_SEEKER &&
                jobApplicationService.isAlreadyApplied(idUser, vacancy.getId())) {

            JobApplication jobApplication = jobApplicationService.tryFindByUserAndVacancyId(idUser, idVacancy);
            request.setAttribute(Attributes.ALREADY_APPLIED_FOR_VACANCY, true);
            request.setAttribute(Attributes.JOB_APPLICATION, jobApplication);
        }

        request.setAttribute(Attributes.VACANCY, vacancy);

        return Router.forward(Pages.VACANCY_INFO);
    }
}
