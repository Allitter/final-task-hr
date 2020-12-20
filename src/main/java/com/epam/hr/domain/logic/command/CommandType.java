package com.epam.hr.domain.logic.command;

import com.epam.hr.domain.model.UserRole;
import com.epam.hr.domain.model.User;
import java.util.*;
import static com.epam.hr.domain.model.UserRole.*;

// TODO think about roles
public enum CommandType {
    DEFAULT_COMMAND(DEFAULT, JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    AUTHENTICATION(DEFAULT, JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    LOGIN(DEFAULT, JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    CHANGE_LANGUAGE(DEFAULT, JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    LOGOUT(JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    VACANCIES(JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    VACANCY_INFO(JOB_SEEKER, EMPLOYEE, ADMINISTRATOR),
    VACANCY_EDIT(EMPLOYEE),
    VACANCY_UPDATE(EMPLOYEE),
    JOB_SEEKERS(EMPLOYEE, ADMINISTRATOR),
    EMPLOYEES(ADMINISTRATOR);

    private final Set<UserRole> permittedRoles;

    public static Optional<CommandType> getCommand(String name) {
        if (name == null) {
            return Optional.empty();
        }

        CommandType command = Arrays.stream(CommandType.values())
                .filter(register -> register.name().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);

        return command == null ? Optional.empty() : Optional.of(command);
    }

    CommandType(UserRole... roles) {
        this.permittedRoles = new HashSet<>();
        permittedRoles.addAll(Arrays.asList(roles));
    }

    public boolean hasAccessToCommand(User user) {
        UserRole userRole = user.getRole();
        return permittedRoles.contains(userRole);
    }
}
