package com.epam.hr.domain.controller.command.impl.vacancy;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.exception.ControllerException;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class VacancyRemoveCommand extends AbstractVacancyCommand {
    private static final String CONTROLLER_COMMAND_VACANCIES = "/controller?command=vacancies";
    private final VacancyService vacancyService;

    public VacancyRemoveCommand(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException, ControllerException {
        long idVacancy = Long.parseLong((String) request.getAttribute(Attributes.VACANCY_ID));

        Vacancy vacancy = vacancyService.tryFindById(idVacancy);
        if (!vacancy.isClosed()) {
            vacancyService.closeVacancy(idVacancy);
        }

        vacancyService.remove(idVacancy);

        String path = request.getContextPath() + CONTROLLER_COMMAND_VACANCIES;
        return Router.redirect(path);
    }
}
