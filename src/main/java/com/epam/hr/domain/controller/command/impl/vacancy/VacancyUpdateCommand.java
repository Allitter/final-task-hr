package com.epam.hr.domain.controller.command.impl.vacancy;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class VacancyUpdateCommand implements Command {
    private final VacancyService service;

    public VacancyUpdateCommand(VacancyService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long id = Long.parseLong((String) request.getAttribute(Attributes.VACANCY_ID));
        String name = (String) request.getAttribute(Attributes.VACANCY_NAME);
        String shortDescription = (String) request.getAttribute(Attributes.VACANCY_SHORT_DESCRIPTION);
        String longDescription = (String) request.getAttribute(Attributes.LONG_DESCRIPTION);

        try {
            service.updateVacancy(id, name, shortDescription, longDescription);

            String path = request.getContextPath() + request.getServletPath();
            return Router.redirect(path);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            Vacancy vacancy = new Vacancy(id, name, shortDescription, longDescription);
            request.setAttribute(Attributes.VACANCY, vacancy);
            return Router.forward(Pages.VACANCY_UPDATE);
        }
    }
}
