package com.epam.hr.domain.controller.command.impl.vacancy;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.impl.AbstractCommand;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;

public abstract class AbstractVacancyCommand extends AbstractCommand {

    protected Router tryAccessVacancy(HttpServletRequest request, VacancyService vacancyService,
                                      String successDestination) throws ServiceException {
        long id = Long.parseLong((String) request.getAttribute(Attributes.VACANCY_ID));
        Vacancy vacancy = vacancyService.tryFindById(id);
        request.setAttribute(Attributes.VACANCY, vacancy);

        return Router.forward(successDestination);
    }

}
