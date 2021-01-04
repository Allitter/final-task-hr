package com.epam.hr.data.mapper.impl;

import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.UserRole;
import com.epam.hr.domain.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserMapper implements Mapper<User> {
    private static final String ID = "id";
    private static final String LOGIN = "login";
    private static final String NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String PATRONYMIC = "patronymic";
    private static final String BIRTH_DATE = "birth_date";
    private static final String ROLE = "role";
    private static final String IS_BANNED = "banned";

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        String login = resultSet.getString(LOGIN);
        String name = resultSet.getString(NAME);
        String lastName = resultSet.getString(LAST_NAME);
        String patronymic = resultSet.getString(PATRONYMIC);
        Date birthDate = resultSet.getDate(BIRTH_DATE);
        boolean isBanned = resultSet.getBoolean(IS_BANNED);

        String roleName = resultSet.getString(ROLE);
        UserRole role = UserRole.valueOf(roleName);

        return new User(id, role, login, null, name, lastName, patronymic, birthDate, isBanned);
    }
}
