package com.epam.hr.data.mapper.impl;

import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.Ban;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BanMapper implements Mapper<Ban> {
    private static final String ATTRIBUTE_PREFIX = "b.";
    private static final String EMPTY_PREFIX = "";
    private static final String ID = "id";
    private static final String REASON = "reason";
    private static final String ID_TARGET = "id_target";
    private static final String ID_ADMINISTRANT = "id_administrant";
    private static final String TIME = "time";

    @Override
    public Ban map(ResultSet resultSet) throws SQLException {
        return map(resultSet, EMPTY_PREFIX);
    }

    @Override
    public Ban mapForAnotherEntity(ResultSet resultSet) throws SQLException {
        return map(resultSet, ATTRIBUTE_PREFIX);
    }

    private Ban map(ResultSet resultSet, String attributePrefix) throws SQLException {
        long id = resultSet.getLong(attributePrefix + ID);
        long idTarget = resultSet.getLong(attributePrefix + ID_TARGET);
        long idAdministrant = resultSet.getLong(attributePrefix + ID_ADMINISTRANT);
        String message = resultSet.getString(attributePrefix + REASON);
        String dateTime = resultSet.getString(attributePrefix + TIME);

        return new Ban.Builder(id)
                .setReason(message)
                .setIdTarget(idTarget)
                .setIdAdministrant(idAdministrant)
                .setDateTime(dateTime)
                .build(true);
    }
}
