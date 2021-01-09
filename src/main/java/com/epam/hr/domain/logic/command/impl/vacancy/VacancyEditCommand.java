package com.epam.hr.domain.logic.command.impl.vacancy;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.VacancyService;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class VacancyEditCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final VacancyService service;

    public VacancyEditCommand(VacancyService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String idString = (String) request.getAttribute(Attributes.VACANCY_ID);
        long id = Long.parseLong(idString);

        try {
            Optional<Vacancy> optional = service.findById(id);
            if (optional.isPresent()) {
                Vacancy vacancy = optional.get();
                request.setAttribute(Attributes.VACANCY, vacancy);

                return Router.forward(Pages.VACANCY_UPDATE);
            } else {
                LOGGER.warn("Attempt to edit not existing vacancy from {}", request.getRemoteAddr());
                return  Router.forward(Pages.PAGE_NOT_FOUND);
            }
        } catch (ServiceException e) {
            return Router.forward(Pages.SERVER_ERROR);
        }
    }
}
