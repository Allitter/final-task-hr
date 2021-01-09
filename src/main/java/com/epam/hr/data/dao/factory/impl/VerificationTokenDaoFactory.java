package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.ConnectionPool;
import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.VerificationTokenDao;

import java.sql.Connection;

public class VerificationTokenDaoFactory implements DaoFactory<VerificationTokenDao> {
    private final ConnectionPool pool;

    public VerificationTokenDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public VerificationTokenDao create() {
        return new VerificationTokenDao(pool.getConnection());
    }

    @Override
    public VerificationTokenDao create(Connection connection) {
        return new VerificationTokenDao(connection);
    }
}
