package com.epam.hr.data.mapper.impl;

import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.Vacancy;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VacancyMapper implements Mapper<Vacancy> {
    private static final String EMPTY_ATTRIBUTE_PREFIX = "";
    private static final String ATTRIBUTE_PREFIX = "v.";
    private static final String ID = "id";
    private static final String VACATION_NAME = "name";
    private static final String SHORT_DESCRIPTION = "short_description";
    private static final String DESCRIPTION = "description";
    private static final String CLOSED = "closed";
    private static final String REMOVED = "removed";

    @Override
    public Vacancy map(ResultSet resultSet) throws SQLException {
        return map(resultSet, EMPTY_ATTRIBUTE_PREFIX);
    }
    @Override
    public Vacancy mapForAnotherEntity(ResultSet resultSet) throws SQLException {
        return map(resultSet, ATTRIBUTE_PREFIX);
    }

    private Vacancy map(ResultSet resultSet, String attributePrefix) throws SQLException {
        long id = resultSet.getLong(attributePrefix + ID);
        String name = resultSet.getString(attributePrefix + VACATION_NAME);
        String shortDescription = resultSet.getString(attributePrefix + SHORT_DESCRIPTION);
        String description = resultSet.getString(attributePrefix + DESCRIPTION);
        boolean closed = resultSet.getBoolean(attributePrefix + CLOSED);
        boolean removed = resultSet.getBoolean(attributePrefix + REMOVED);

        return new Vacancy.Builder(id)
                .setName(name)
                .setShortDescription(shortDescription)
                .setDescription(description)
                .setClosed(closed)
                .setRemoved(removed)
                .build(true);
    }
}
