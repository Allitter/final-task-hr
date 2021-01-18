package com.epam.hr.data.mapper.impl;

import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.VerificationToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class VerificationTokenMapper implements Mapper<VerificationToken> {
    private static final String ATTRIBUTE_PREFIX = "vt.";
    private static final String ID = "id";
    private static final String ID_USER = "id_user";
    private static final String CODE = "code";
    private static final String EXPIRATION_DATE = "expiration_date";

    @Override
    public VerificationToken map(ResultSet resultSet) throws SQLException {
        return map(resultSet, "");
    }

    @Override
    public VerificationToken mapForAnotherEntity(ResultSet resultSet) throws SQLException {
        return map(resultSet, ATTRIBUTE_PREFIX);
    }

    private VerificationToken map(ResultSet resultSet, String attributePrefix) throws SQLException {
        long id = resultSet.getLong(attributePrefix + ID);
        long idUser = resultSet.getLong(attributePrefix + ID_USER);
        String code = resultSet.getString(attributePrefix + CODE);
        Date expirationDate = resultSet.getDate(attributePrefix + EXPIRATION_DATE);

        return new VerificationToken(id, idUser, code, expirationDate);
    }

}
