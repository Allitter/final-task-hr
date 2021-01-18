package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.VerificationTokenDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.data.mapper.impl.VerificationTokenMapper;
import com.epam.hr.data.pool.ConnectionPool;
import com.epam.hr.domain.model.VerificationToken;

import java.sql.Connection;

public class VerificationTokenDaoFactory implements DaoFactory<VerificationTokenDao> {
    private static final Mapper<VerificationToken> MAPPER = new VerificationTokenMapper();
    private final ConnectionPool pool;

    public VerificationTokenDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public VerificationTokenDao create() {
        return new VerificationTokenDao(pool.getConnection(), MAPPER);
    }

    @Override
    public VerificationTokenDao create(Connection connection) {
        return new VerificationTokenDao(connection, MAPPER);
    }
}
