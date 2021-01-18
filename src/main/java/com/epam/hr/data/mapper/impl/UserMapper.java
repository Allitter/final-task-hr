package com.epam.hr.data.mapper.impl;

import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.UserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserMapper implements Mapper<User> {
    private static final String ATTRIBUTE_PREFIX = "u.";
    private static final String ID = "id";
    private static final String LOGIN = "login";
    private static final String NAME = "name";
    private static final String LAST_NAME = "last_name";
    private static final String PATRONYMIC = "patronymic";
    private static final String BIRTH_DATE = "birth_date";
    private static final String ROLE = "role";
    private static final String IS_BANNED = "banned";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String ENABLED = "enabled";

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        return map(resultSet, "");
    }

    @Override
    public User mapForAnotherEntity(ResultSet resultSet) throws SQLException {
        return map(resultSet, ATTRIBUTE_PREFIX);
    }

    public User map(ResultSet resultSet, String attributePrefix) throws SQLException {
        long id = resultSet.getLong(attributePrefix + ID);
        String login = resultSet.getString(attributePrefix + LOGIN);
        String name = resultSet.getString(attributePrefix + NAME);
        String lastName = resultSet.getString(attributePrefix + LAST_NAME);
        String patronymic = resultSet.getString(attributePrefix + PATRONYMIC);
        String email = resultSet.getString(attributePrefix + EMAIL);
        String phone = resultSet.getString(attributePrefix + PHONE);
        Date birthDate = resultSet.getDate(attributePrefix + BIRTH_DATE);
        boolean isBanned = resultSet.getBoolean(attributePrefix + IS_BANNED);
        boolean enabled = resultSet.getBoolean(attributePrefix + ENABLED);

        String roleName = resultSet.getString(attributePrefix + ROLE);
        UserRole role = UserRole.valueOf(roleName);

        return new User.Builder()
                .setId(id)
                .setRole(role)
                .setLogin(login)
                .setName(name)
                .setLastName(lastName)
                .setPatronymic(patronymic)
                .setEmail(email)
                .setPhone(phone)
                .setBirthDate(birthDate)
                .setBanned(isBanned)
                .setEnabled(enabled)
                .build();
    }
}
