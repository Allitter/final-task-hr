package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.service.VacancyService;
import com.epam.hr.domain.logic.validation.Validator;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class VacancyAddAcceptCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Validator<Vacancy> validator;
    private final VacancyService service;

    public VacancyAddAcceptCommand(Validator<Vacancy> validator, VacancyService service) {
        this.validator = validator;
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String name = (String) request.getAttribute(Attributes.VACANCY_NAME);
        String shortDescription = (String) request.getAttribute(Attributes.VACANCY_SHORT_DESCRIPTION);
        String longDescription = (String) request.getAttribute(Attributes.LONG_DESCRIPTION);

        Vacancy vacancy = new Vacancy(-1, name, shortDescription, longDescription);

        if (!validator.isValid(vacancy)) {
            String path = request.getHeader(Attributes.REFERER);
            return Router.redirect(path);
        }

        service.save(vacancy);

        String path = request.getContextPath() + request.getServletPath();
        return Router.redirect(path);
    }
}
