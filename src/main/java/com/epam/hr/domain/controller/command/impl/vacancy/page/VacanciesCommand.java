package com.epam.hr.domain.controller.command.impl.vacancy.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.vacancy.AbstractVacancyCommand;
import com.epam.hr.domain.model.Page;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class VacanciesCommand extends AbstractVacancyCommand {
    private static final int NUMBER_OF_RECORDS_PER_PAGE = 10;
    private final VacancyService vacancyService;

    public VacanciesCommand(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String pageParameter = (String) request.getAttribute(Attributes.PAGE);
        int queriedPage = pageParameter != null ? Integer.parseInt(pageParameter) : 0;
        int totalQuantity = vacancyService.getVacanciesCount();
        Page page = getClosestExistingPage(queriedPage, totalQuantity);

        int from = page.getCurrentPage() * NUMBER_OF_RECORDS_PER_PAGE;
        List<Vacancy> vacancies = vacancyService.findVacancies(from, NUMBER_OF_RECORDS_PER_PAGE);
        request.setAttribute(Attributes.VACANCIES, vacancies);
        request.setAttribute(Attributes.PAGE, page);

        return Router.forward(Pages.VACANCIES);
    }
}
