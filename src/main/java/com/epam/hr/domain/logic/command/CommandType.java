package com.epam.hr.domain.logic.command;

import com.epam.hr.domain.model.UserRole;
import java.util.*;

import static com.epam.hr.domain.model.UserRole.*;

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

    VACANCIES(JOB_SEEKER, EMPLOYEE),
    VACANCY_INFO(JOB_SEEKER, EMPLOYEE),

    VACANCY_APPLY(JOB_SEEKER),
    VACANCY_APPLY_ACCEPT(JOB_SEEKER),
    RESUME_ADD(JOB_SEEKER),
    RESUME_ADD_ACCEPT(JOB_SEEKER),
    RESUME_EDIT(JOB_SEEKER),
    RESUME_EDIT_ACCEPT(JOB_SEEKER),
    RESUME_REMOVE(JOB_SEEKER),
    JOB_APPLICATIONS(JOB_SEEKER),

    JOB_SEEKERS(EMPLOYEE, ADMINISTRATOR),
    JOB_SEEKER_INFO(EMPLOYEE, ADMINISTRATOR),

    VACANCY_EDIT(EMPLOYEE),
    VACANCY_UPDATE(EMPLOYEE),
    VACANCY_ADD(EMPLOYEE),
    VACANCY_ADD_ACCEPT(EMPLOYEE),
    JOB_APPLICATIONS_FOR_VACANCY(EMPLOYEE),
    JOB_APPLICATION_INFO(EMPLOYEE),
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

    private final List<UserRole> permittedRoles;

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

    CommandType(UserRole... roles) {
        this.permittedRoles = new ArrayList<>();
        permittedRoles.addAll(Arrays.asList(roles));
    }

    public boolean hasAccessToCommand(UserRole role) {
        return permittedRoles.contains(role);
    }
}
