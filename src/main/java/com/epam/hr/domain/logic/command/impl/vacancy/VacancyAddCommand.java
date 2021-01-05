package com.epam.hr.domain.logic.command.impl.vacancy;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.VacancyService;
import com.epam.hr.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;

public class VacancyAddCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final VacancyService service;

    public VacancyAddCommand(VacancyService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String path = Pages.VACANCY_ADD;
        return Router.forward(path);
    }
}
