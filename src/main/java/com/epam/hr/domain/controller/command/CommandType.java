package com.epam.hr.domain.controller.command;

import com.epam.hr.domain.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.epam.hr.domain.model.User.Role.*;

public enum CommandType {
    DEFAULT_COMMAND(DEFAULT, JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    AUTHENTICATION(DEFAULT, JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    CHANGE_LANGUAGE(DEFAULT, JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),

    LOGIN(DEFAULT),
    REGISTRATION_PAGE(DEFAULT),
    REGISTRATION(DEFAULT),

    LOGOUT(JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    ACCOUNT(JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    ACCOUNT_UPDATE(JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    RESUME_INFO(JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    CONFIRMATION_PAGE(JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    CONFIRMATION_PAGE_WITH_MESSAGE(JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    CONFIRMATION(JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),

    VACANCIES(JOB_SEEKER, EMPLOYEE),
    VACANCY_INFO(JOB_SEEKER, EMPLOYEE),

    VERIFICATION_PAGE(JOB_SEEKER),
    VERIFICATION(JOB_SEEKER),
    VACANCY_APPLY(JOB_SEEKER),
    VACANCY_APPLY_ACCEPT(JOB_SEEKER),
    RESUME_ADD(JOB_SEEKER),
    RESUME_ADD_ACCEPT(JOB_SEEKER),
    RESUME_EDIT(JOB_SEEKER),
    RESUME_EDIT_ACCEPT(JOB_SEEKER),
    RESUME_REMOVE(JOB_SEEKER),
    JOB_APPLICATIONS_FOR_SEEKER(JOB_SEEKER),

    JOB_SEEKERS(EMPLOYEE, ADMINISTRATOR),
    JOB_SEEKER_INFO(EMPLOYEE, ADMINISTRATOR),

    VACANCY_EDIT(EMPLOYEE),
    VACANCY_UPDATE(EMPLOYEE),
    VACANCY_ADD(EMPLOYEE),
    VACANCY_ADD_ACCEPT(EMPLOYEE),
    VACANCY_CLOSE(EMPLOYEE),
    JOB_APPLICATIONS_FOR_VACANCY(EMPLOYEE),
    JOB_APPLICATION_INFO(JOB_SEEKER, EMPLOYEE),
    ASSIGN_PRELIMINARY_INTERVIEW(EMPLOYEE),
    ASSIGN_TECHNICAL_INTERVIEW(EMPLOYEE),
    ASSIGN_FOR_JOB(EMPLOYEE),
    BLOCK_JOB_APPLICATION(EMPLOYEE),
    UPDATE_PRELIMINARY_NOTE(EMPLOYEE),
    UPDATE_TECHNICAL_NOTE(EMPLOYEE),

    EMPLOYEES(ADMINISTRATOR),
    EMPLOYEE_INFO(ADMINISTRATOR),
    USER_BAN(ADMINISTRATOR),
    USER_UNBAN(ADMINISTRATOR);

    private final List<User.Role> permittedRoles;

    public static Optional<CommandType> getCommand(String name) {
        if (name == null) {
            return Optional.empty();
        }
        CommandType command = Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.name().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);

        return Optional.ofNullable(command);
    }

    CommandType(User.Role... roles) {
        this.permittedRoles = new ArrayList<>();
        permittedRoles.addAll(Arrays.asList(roles));
    }

    public boolean hasAccessToCommand(User.Role role) {
        return permittedRoles.contains(role);
    }
}
