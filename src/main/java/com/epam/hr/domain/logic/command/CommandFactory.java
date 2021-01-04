package com.epam.hr.domain.logic.command;

import com.epam.hr.domain.logic.command.impl.*;
import com.epam.hr.domain.logic.service.ApplicationService;
import com.epam.hr.domain.logic.service.ResumeService;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.logic.service.VacancyService;
import com.epam.hr.domain.logic.validation.VacancyValidator;

public final class CommandFactory {
    private CommandFactory() {
    }

    public static Command create(CommandType commandType) {
        switch (commandType) {
            case LOGIN :
                return new LoginCommand(new UserService());
            case REGISTRATION_PAGE:
                return new RegistrationPageCommand();
            case REGISTRATION:
                return new RegistrationCommand(new UserService());
            case VACANCIES :
                return new VacanciesCommand(new VacancyService());
            case VACANCY_INFO :
                return new VacancyInfoCommand(new VacancyService());
            case VACANCY_EDIT :
                return new VacancyEditCommand(new VacancyService());
            case VACANCY_UPDATE :
                return new VacancyUpdateCommand(new VacancyService());
            case VACANCY_ADD :
                return new VacancyAddCommand(new VacancyService());
            case VACANCY_ADD_ACCEPT :
                return new VacancyAddAcceptCommand(new VacancyValidator(), new VacancyService());
            case VACANCY_APPLY :
                return new VacancyApplyCommand(new ApplicationService(), new ResumeService(), new VacancyService());
            case VACANCY_APPLY_ACCEPT :
                return new VacancyApplyAcceptCommand(new ApplicationService());
            case RESUME_ADD :
                return new ResumeAddCommand();
            case RESUME_ADD_ACCEPT :
                return new ResumeAddAcceptCommand(new ResumeService());
            case RESUME_EDIT :
                return new ResumeEditCommand(new ResumeService());
            case RESUME_EDIT_ACCEPT :
                return new ResumeEditAcceptCommand(new ResumeService());
            case RESUME_REMOVE:
                return new ResumeRemoveCommand(new ResumeService());
            case JOB_SEEKER_INFO:
                return new JobSeekerInfoCommand(new UserService(), new ResumeService());
            case EMPLOYEE_INFO:
                return new EmployeeInfoCommand(new UserService());
            case  EMPLOYEES :
                return new EmployeesCommand(new UserService());
            case  JOB_SEEKERS :
                return new JobSeekersCommand(new UserService());
            case  JOB_APPLICATIONS:
                return new JobApplicationsCommand(new ApplicationService(), new VacancyService());
            case  JOB_APPLICATIONS_FOR_VACANCY:
                return new JobApplicationForVacancyCommand(new ApplicationService(), new VacancyService(), new UserService());
            case  JOB_APPLICATION_INFO:
                return new JobApplicationInfoCommand(new UserService(), new ResumeService(), new ApplicationService());
            case ASSIGN_PRELIMINARY_INTERVIEW:
                return new AssignPreliminaryInterviewCommand(new ApplicationService());
            case ASSIGN_TECHNICAL_INTERVIEW:
                return new AssignTechnicalInterviewCommand(new ApplicationService());
            case ASSIGN_FOR_JOB:
                return new AssignForJobCommand(new ApplicationService());
            case BLOCK_JOB_APPLICATION:
                return new BlockJobApplicationCommand(new ApplicationService());
            case AUTHENTICATION :
                return new AuthenticationCommand();
            case CHANGE_LANGUAGE :
                return new ChangeLanguageCommand();
            case USER_BAN :
                return new UserBanCommand(new UserService());
            case USER_UNBAN :
                return new UserUnbanCommand(new UserService());
            case ACCOUNT :
                return new AccountCommand(new ResumeService());
            case ACCOUNT_UPDATE :
                return new AccountUpdateCommand(new UserService());
            case RESUME_INFO:
                return new ResumeInfoCommand(new ResumeService(), new UserService());
            case LOGOUT :
                return new LogoutCommand();
            default :
                return new DefaultCommand();
        }
    }

}
