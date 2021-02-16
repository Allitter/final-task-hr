package com.epam.hr.domain.controller.command.impl.vacancy;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class VacancyUpdateCommand extends AbstractVacancyCommand {
    private static final String CONTROLLER_COMMAND_VACANCY_INFO = "/controller?command=vacancy_info&vacancy_id=%d";
    private final VacancyService service;

    public VacancyUpdateCommand(VacancyService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idVacancy = Long.parseLong((String) request.getAttribute(Attributes.VACANCY_ID));
        String name = (String) request.getAttribute(Attributes.VACANCY_NAME);
        String shortDescription = (String) request.getAttribute(Attributes.VACANCY_SHORT_DESCRIPTION);
        String description = (String) request.getAttribute(Attributes.LONG_DESCRIPTION);

        Vacancy vacancy = service.tryFindById(idVacancy);
        vacancy = new Vacancy.Builder(vacancy)
                .setName(name)
                .setShortDescription(shortDescription)
                .setDescription(description)
                .build();

        try {
            service.saveVacancy(vacancy);
            String path = request.getContextPath() + String.format(CONTROLLER_COMMAND_VACANCY_INFO, idVacancy);
            return Router.redirect(path);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            request.setAttribute(Attributes.VACANCY, vacancy);
            return Router.forward(Pages.VACANCY_UPDATE);
        }
    }
}
