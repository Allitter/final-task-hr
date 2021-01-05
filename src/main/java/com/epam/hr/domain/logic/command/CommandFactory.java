package com.epam.hr.domain.logic.command;

import com.epam.hr.domain.logic.command.impl.*;
import com.epam.hr.domain.logic.command.impl.application.*;
import com.epam.hr.domain.logic.command.impl.resume.*;
import com.epam.hr.domain.logic.command.impl.user.*;
import com.epam.hr.domain.logic.command.impl.vacancy.*;
import com.epam.hr.domain.logic.service.JobApplicationService;
import com.epam.hr.domain.logic.service.ResumeService;
import com.epam.hr.domain.logic.service.UserService;
import com.epam.hr.domain.logic.service.VacancyService;
import com.epam.hr.domain.logic.validation.VacancyValidator;

public final class CommandFactory {
    private static final VacancyService VACANCY_SERVICE = new VacancyService();
    private static final UserService USER_SERVICE = new UserService();
    private static final JobApplicationService JOB_APPLICATION_SERVICE = new JobApplicationService();
    private static final ResumeService RESUME_SERVICE = new ResumeService();

    private CommandFactory() {
    }

    public static Command create(CommandType commandType) {
        switch (commandType) {
            case LOGIN :
                return new LoginCommand(USER_SERVICE);
            case REGISTRATION_PAGE:
                return new RegistrationPageCommand();
            case REGISTRATION:
                return new RegistrationCommand(USER_SERVICE);
            case VACANCIES :
                return new VacanciesCommand(VACANCY_SERVICE);
            case VACANCY_INFO :
                return new VacancyInfoCommand(VACANCY_SERVICE);
            case VACANCY_EDIT :
                return new VacancyEditCommand(VACANCY_SERVICE);
            case VACANCY_UPDATE :
                return new VacancyUpdateCommand(VACANCY_SERVICE);
            case VACANCY_ADD :
                return new VacancyAddCommand(VACANCY_SERVICE);
            case VACANCY_ADD_ACCEPT :
                return new VacancyAddAcceptCommand(new VacancyValidator(), VACANCY_SERVICE);
            case VACANCY_APPLY :
                return new VacancyApplyCommand(JOB_APPLICATION_SERVICE, RESUME_SERVICE, VACANCY_SERVICE);
            case VACANCY_APPLY_ACCEPT :
                return new VacancyApplyAcceptCommand(JOB_APPLICATION_SERVICE, RESUME_SERVICE);
            case RESUME_ADD :
                return new ResumeAddCommand();
            case RESUME_ADD_ACCEPT :
                return new ResumeAddAcceptCommand(RESUME_SERVICE);
            case RESUME_EDIT :
                return new ResumeEditCommand(RESUME_SERVICE);
            case RESUME_EDIT_ACCEPT :
                return new ResumeEditAcceptCommand(RESUME_SERVICE);
            case RESUME_REMOVE:
                return new ResumeRemoveCommand(RESUME_SERVICE);
            case JOB_SEEKER_INFO:
                return new JobSeekerInfoCommand(USER_SERVICE, RESUME_SERVICE);
            case EMPLOYEE_INFO:
                return new EmployeeInfoCommand(USER_SERVICE);
            case  EMPLOYEES :
                return new EmployeesCommand(USER_SERVICE);
            case  JOB_SEEKERS :
                return new JobSeekersCommand(USER_SERVICE);
            case  JOB_APPLICATIONS:
                return new JobApplicationsCommand(JOB_APPLICATION_SERVICE, VACANCY_SERVICE);
            case  JOB_APPLICATIONS_FOR_VACANCY:
                return new JobApplicationsForVacancyCommand(JOB_APPLICATION_SERVICE, VACANCY_SERVICE, USER_SERVICE);
            case  JOB_APPLICATION_INFO:
                return new JobApplicationInfoCommand(USER_SERVICE, JOB_APPLICATION_SERVICE);
            case ASSIGN_PRELIMINARY_INTERVIEW:
                return new AssignPreliminaryInterviewCommand(JOB_APPLICATION_SERVICE);
            case ASSIGN_TECHNICAL_INTERVIEW:
                return new AssignTechnicalInterviewCommand(JOB_APPLICATION_SERVICE);
            case ASSIGN_FOR_JOB:
                return new AssignForJobCommand(JOB_APPLICATION_SERVICE);
            case BLOCK_JOB_APPLICATION:
                return new BlockJobApplicationCommand(JOB_APPLICATION_SERVICE);
            case UPDATE_PRELIMINARY_NOTE:
                return new UpdatePreliminaryNoteCommand(JOB_APPLICATION_SERVICE);
            case UPDATE_TECHNICAL_NOTE:
                return new UpdateTechnicalNoteCommand(JOB_APPLICATION_SERVICE);
            case AUTHENTICATION :
                return new AuthenticationCommand();
            case CHANGE_LANGUAGE :
                return new ChangeLanguageCommand();
            case USER_BAN :
                return new UserBanCommand(USER_SERVICE);
            case USER_UNBAN :
                return new UserUnbanCommand(USER_SERVICE);
            case ACCOUNT :
                return new AccountCommand(RESUME_SERVICE);
            case ACCOUNT_UPDATE :
                return new AccountUpdateCommand(USER_SERVICE);
            case RESUME_INFO:
                return new ResumeInfoCommand(RESUME_SERVICE, USER_SERVICE);
            case LOGOUT :
                return new LogoutCommand();
            default :
                return new DefaultCommand();
        }
    }

}
