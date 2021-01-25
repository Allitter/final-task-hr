package com.epam.hr.domain.controller.command.impl.vacancy;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class VacancyCloseCommand extends AbstractVacancyCommand {
    private final VacancyService vacancyService;

    public VacancyCloseCommand(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idVacancy = Long.parseLong((String) request.getAttribute(Attributes.VACANCY_ID));
        vacancyService.closeVacancy(idVacancy);

        String path = request.getHeader(Attributes.REFERER);
        return Router.redirect(path);
    }

}
