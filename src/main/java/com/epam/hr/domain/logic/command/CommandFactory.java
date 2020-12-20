package com.epam.hr.domain.logic.command;

import com.epam.hr.domain.logic.command.impl.*;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.logic.service.VacancyService;

public final class CommandFactory {
    private CommandFactory() {
    }

    public static Command create(CommandType commandType) {
        switch (commandType) {
            case LOGIN :
                return new LoginCommand(new UserService());
            case VACANCIES :
                return new VacanciesCommand(new VacancyService());
            case VACANCY_INFO :
                return new VacancyInfoCommand(new VacancyService());
            case VACANCY_EDIT :
                return new VacancyEditCommand(new VacancyService());
            case VACANCY_UPDATE :
                return new VacancyUpdateCommand(new VacancyService());
            case  EMPLOYEES :
                return new EmployeesCommand(new UserService());
            case  JOB_SEEKERS :
                return new JobSeekersCommand(new UserService());
            case AUTHENTICATION :
                return new AuthenticationCommand();
            case CHANGE_LANGUAGE :
                return new ChangeLanguageCommand();
            case LOGOUT :
                return new LogoutCommand();
            default :
                return new DefaultCommand();
        }
    }

}
