package com.epam.hr.domain.logic.command.impl.vacancy;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.VacancyService;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class VacancyUpdateCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final VacancyService service;

    public VacancyUpdateCommand(VacancyService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String idString = (String) request.getAttribute(Attributes.VACANCY_ID);
        long id = Long.parseLong(idString);
        String name = (String) request.getAttribute(Attributes.VACANCY_NAME);
        String shortDescription = (String) request.getAttribute(Attributes.VACANCY_SHORT_DESCRIPTION);
        String longDescription = (String) request.getAttribute(Attributes.LONG_DESCRIPTION);
        Vacancy vacancy = new Vacancy(id, name, shortDescription, longDescription);

        try {
            service.save(vacancy);

            String path = request.getContextPath() + request.getServletPath();
            return Router.redirect(path);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            request.setAttribute(Attributes.FAILS, fails);
            request.setAttribute(Attributes.VACANCY, vacancy);
            return Router.forward(Pages.VACANCY_UPDATE);
        } catch (ServiceException e) {
            LOGGER.warn("Unable to update vacancy", e);
            return Router.forward(Pages.SERVER_ERROR);
        }
    }
}
