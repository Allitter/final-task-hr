package com.epam.hr.domain.controller.command;

import com.epam.hr.data.ConnectionPool;
import com.epam.hr.data.dao.factory.impl.*;
import com.epam.hr.domain.controller.command.impl.AuthenticationCommand;
import com.epam.hr.domain.controller.command.impl.ChangeLanguageCommand;
import com.epam.hr.domain.controller.command.impl.DefaultCommand;
import com.epam.hr.domain.controller.command.impl.application.*;
import com.epam.hr.domain.controller.command.impl.application.page.JobApplicationInfoCommand;
import com.epam.hr.domain.controller.command.impl.application.page.JobApplicationsForSeekerCommand;
import com.epam.hr.domain.controller.command.impl.application.page.JobApplicationsForVacancyCommand;
import com.epam.hr.domain.controller.command.impl.application.page.VacancyApplyCommand;
import com.epam.hr.domain.controller.command.impl.resume.*;
import com.epam.hr.domain.controller.command.impl.resume.page.ResumeAddCommand;
import com.epam.hr.domain.controller.command.impl.resume.page.ResumeEditCommand;
import com.epam.hr.domain.controller.command.impl.resume.page.ResumeInfoCommand;
import com.epam.hr.domain.controller.command.impl.user.*;
import com.epam.hr.domain.controller.command.impl.user.page.*;
import com.epam.hr.domain.controller.command.impl.vacancy.*;
import com.epam.hr.domain.controller.command.impl.vacancy.page.VacanciesCommand;
import com.epam.hr.domain.controller.command.impl.vacancy.page.VacancyAddCommand;
import com.epam.hr.domain.controller.command.impl.vacancy.page.VacancyEditCommand;
import com.epam.hr.domain.controller.command.impl.vacancy.page.VacancyInfoCommand;
import com.epam.hr.domain.service.*;
import com.epam.hr.domain.validator.JobApplicationValidator;
import com.epam.hr.domain.validator.ResumeValidator;
import com.epam.hr.domain.validator.UserValidator;
import com.epam.hr.domain.validator.VacancyValidator;
import com.epam.hr.domain.util.VerificationCodeGenerator;

public final class CommandFactory {
    private static final ConnectionPool POOL = ConnectionPool.getInstance();

    private static final VacancyDaoFactory VACANCY_DAO_FACTORY = new VacancyDaoFactory(POOL);
    private static final UserDaoFactory USER_DAO_FACTORY = new UserDaoFactory(POOL);
    private static final JobApplicationDaoFactory JOB_APPLICATION_DAO_FACTORY = new JobApplicationDaoFactory(POOL);
    private static final ResumeDaoFactory RESUME_DAO_FACTORY = new ResumeDaoFactory(POOL);
    private static final VerificationTokenDaoFactory VERIFICATION_TOKEN_DAO_FACTORY = new VerificationTokenDaoFactory(POOL);

    private static final UserValidator USER_VALIDATOR = new UserValidator();
    private static final VacancyValidator VACANCY_VALIDATOR = new VacancyValidator();
    private static final JobApplicationValidator JOB_APPLICATION_VALIDATOR = new JobApplicationValidator();
    private static final ResumeValidator RESUME_VALIDATOR = new ResumeValidator();

    private static final VacancyService VACANCY_SERVICE = new VacancyService(VACANCY_VALIDATOR, VACANCY_DAO_FACTORY);
    private static final UserService USER_SERVICE = new UserService(USER_VALIDATOR, USER_DAO_FACTORY);
    private static final JobApplicationService JOB_APPLICATION_SERVICE = new JobApplicationService(JOB_APPLICATION_VALIDATOR, JOB_APPLICATION_DAO_FACTORY);
    private static final ResumeService RESUME_SERVICE = new ResumeService(RESUME_VALIDATOR, RESUME_DAO_FACTORY);
    private static final MailingService MAILING_SERVICE = new MailingService();
    private static final VerificationTokenService VERIFICATION_TOKEN_SERVICE = new VerificationTokenService(VERIFICATION_TOKEN_DAO_FACTORY);

    private static final VerificationCodeGenerator VERIFICATION_CODE_GENERATOR = new VerificationCodeGenerator();

    private CommandFactory() {
    }

    public static Command create(CommandType commandType) {
        switch (commandType) {
            case LOGIN :
                return new LoginCommand(USER_SERVICE);
            case VACANCIES :
                return new VacanciesCommand(VACANCY_SERVICE);
            case VACANCY_INFO :
                return new VacancyInfoCommand(VACANCY_SERVICE);
            case VACANCY_EDIT :
                return new VacancyEditCommand(VACANCY_SERVICE);
            case VACANCY_UPDATE :
                return new VacancyUpdateCommand(VACANCY_SERVICE);
            case VACANCY_ADD :
                return new VacancyAddCommand();
            case VACANCY_ADD_ACCEPT :
                return new VacancyAddAcceptCommand(VACANCY_SERVICE);
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
            case EMPLOYEES :
                return new EmployeesCommand(USER_SERVICE);
            case JOB_SEEKERS :
                return new JobSeekersCommand(USER_SERVICE);
            case JOB_APPLICATIONS_FOR_SEEKER:
                return new JobApplicationsForSeekerCommand(JOB_APPLICATION_SERVICE, VACANCY_SERVICE);
            case JOB_APPLICATIONS_FOR_VACANCY:
                return new JobApplicationsForVacancyCommand(JOB_APPLICATION_SERVICE, VACANCY_SERVICE, USER_SERVICE);
            case JOB_APPLICATION_INFO:
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
            case VERIFICATION:
                return new VerificationCommand(USER_SERVICE, VERIFICATION_TOKEN_SERVICE);
            case VERIFICATION_PAGE:
                return new VerificationPageCommand(MAILING_SERVICE, VERIFICATION_TOKEN_SERVICE, VERIFICATION_CODE_GENERATOR);
            case CHANGE_LANGUAGE :
                return new ChangeLanguageCommand();
            case USER_BAN :
                return new UserBanCommand(USER_SERVICE);
            case USER_UNBAN :
                return new UserUnbanCommand(USER_SERVICE);
            case ACCOUNT :
                return new AccountCommand(RESUME_SERVICE);
            case ACCOUNT_UPDATE :
                return new AccountUpdateCommand(USER_SERVICE, RESUME_SERVICE);
            case RESUME_INFO:
                return new ResumeInfoCommand(RESUME_SERVICE, USER_SERVICE);
            case LOGOUT :
                return new LogoutCommand();
            case REGISTRATION_PAGE:
                return new RegistrationPageCommand();
            case REGISTRATION:
                return new RegistrationCommand(USER_SERVICE);
            default :
                return new DefaultCommand();
        }
    }

}
