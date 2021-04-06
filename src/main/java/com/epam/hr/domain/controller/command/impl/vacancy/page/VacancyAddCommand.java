package com.epam.hr.domain.controller.command.impl.vacancy.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.vacancy.AbstractVacancyCommand;

import javax.servlet.http.HttpServletRequest;

public class VacancyAddCommand extends AbstractVacancyCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        return Router.forward(Pages.VACANCY_ADD);
    }

}
