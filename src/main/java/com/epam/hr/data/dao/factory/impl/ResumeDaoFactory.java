package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.ConnectionPool;
import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.ResumeDao;

import java.sql.Connection;

public class ResumeDaoFactory implements DaoFactory<ResumeDao> {
    private final ConnectionPool pool;

    public ResumeDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public ResumeDao create() {
        return new ResumeDao(pool.getConnection());
    }

    @Override
    public ResumeDao create(Connection connection) {
        return new ResumeDao(connection);
    }
}
