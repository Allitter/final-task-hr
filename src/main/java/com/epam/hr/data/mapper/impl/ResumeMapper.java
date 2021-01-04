package com.epam.hr.data.mapper.impl;

import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.Resume;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResumeMapper implements Mapper<Resume> {
    private static final String ID = "id";
    private static final String ID_USER = "id_user";
    private static final String TEXT = "text";
    private static final String NAME = "name";

    @Override
    public Resume map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        long idUser = resultSet.getLong(ID_USER);
        String name = resultSet.getString(NAME);
        String text = resultSet.getString(TEXT);

        return new Resume(id, idUser, name, text);
    }

}
