package com.epam.hr.data.mapper.impl;

import com.epam.hr.data.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DateMapper implements Mapper<Date> {
    private static final String DATE = "date";

    @Override
    public Date map(ResultSet resultSet) throws SQLException {
        return resultSet.getDate(DATE);
    }

    @Override
    public Date mapForAnotherEntity(ResultSet resultSet) throws SQLException {
        return resultSet.getDate(DATE);
    }
}
