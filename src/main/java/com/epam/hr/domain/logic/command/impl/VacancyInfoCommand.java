package com.epam.hr.domain.logic.command.impl;

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

public class VacancyInfoCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final VacancyService service;

    public VacancyInfoCommand(VacancyService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String idString = (String) request.getAttribute(Attributes.VACANCY_ID);
        long id = Long.parseLong(idString);
        Optional<Vacancy> optional = service.findById(id);
        if (optional.isPresent()) {
            Vacancy vacancy = optional.get();
            request.setAttribute(Attributes.VACANCY, vacancy);

            String path = Pages.VACANCY_INFO;
            return Router.forward(path);
        } else {
            LOGGER.warn("Attempt to view not existing vacancy from"); // TODO maybe add ip
            return  Router.forward(Pages.SERVER_ERROR);
        }
    }

}
