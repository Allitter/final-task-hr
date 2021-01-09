package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.ConnectionPool;
import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.JobApplicationDao;

import java.sql.Connection;

public class JobApplicationDaoFactory implements DaoFactory<JobApplicationDao> {
    private final ConnectionPool pool;

    public JobApplicationDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public JobApplicationDao create() {
        return new JobApplicationDao(pool.getConnection());
    }

    @Override
    public JobApplicationDao create(Connection connection) {
        return new JobApplicationDao(connection);
    }
}
