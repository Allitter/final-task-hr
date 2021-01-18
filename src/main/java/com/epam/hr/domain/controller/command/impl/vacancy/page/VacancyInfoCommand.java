package com.epam.hr.domain.controller.command.impl.vacancy.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.vacancy.AbstractVacancyCommand;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class VacancyInfoCommand extends AbstractVacancyCommand {
    private final VacancyService service;

    public VacancyInfoCommand(VacancyService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        return tryAccessVacancy(request, service, Pages.VACANCY_INFO);
    }
}
