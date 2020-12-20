package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.service.VacancyService;
import com.epam.hr.domain.model.Vacancy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class VacanciesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int NUMBER_OF_RECORDS_PER_PAGE = 10;
    private final VacancyService vacancyService;

    public VacanciesCommand(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String pageParameter = (String) request.getAttribute(Attributes.PAGE);
        int page = pageParameter != null ? Integer.parseInt(pageParameter) : 0;
        page = Math.max(page, 0);

        try {
            // TODO
            int totalQuantity = vacancyService.findQuantity();
            int from = page * NUMBER_OF_RECORDS_PER_PAGE;

            if (totalQuantity != 0) {
                if (from >= totalQuantity) {
                    page = totalQuantity / NUMBER_OF_RECORDS_PER_PAGE;
                    from = page * NUMBER_OF_RECORDS_PER_PAGE;
                }

                List<Vacancy> vacancies = vacancyService.findVacancies(from, NUMBER_OF_RECORDS_PER_PAGE);
                request.setAttribute(Attributes.VACANCIES, vacancies);
                request.setAttribute(Attributes.PAGE, page);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            return  Router.forward(Pages.SERVER_ERROR);
        }

        return Router.forward(Pages.VACANCIES);
    }
}
