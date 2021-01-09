package com.epam.hr.data.mapper.impl;

import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.VerificationToken;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class VerificationTokenMapper implements Mapper<VerificationToken> {
    private static final String ID = "id";
    private static final String ID_USER = "id_user";
    private static final String CODE = "code";
    private static final String EXPIRATION_DATE = "expiration_date";

    @Override
    public VerificationToken map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        long idUser = resultSet.getLong(ID_USER);
        String code = resultSet.getString(CODE);
        Date expirationDate = resultSet.getDate(EXPIRATION_DATE);

        return new VerificationToken(id, idUser, code, expirationDate);
    }

}
