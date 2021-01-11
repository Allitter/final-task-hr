package com.epam.hr.domain.controller.command.impl.vacancy.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class VacancyEditCommand implements Command {
    private final VacancyService service;

    public VacancyEditCommand(VacancyService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long id = Long.parseLong((String) request.getAttribute(Attributes.VACANCY_ID));

        Optional<Vacancy> optional = service.findById(id);
        if (!optional.isPresent()){
            return  Router.forward(Pages.PAGE_NOT_FOUND);
        }
        Vacancy vacancy = optional.get();
        request.setAttribute(Attributes.VACANCY, vacancy);

        return Router.forward(Pages.VACANCY_UPDATE);
    }
}