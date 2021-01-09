package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.ConnectionPool;
import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.VacancyDao;

import java.sql.Connection;

public class VacancyDaoFactory implements DaoFactory<VacancyDao> {
    private final ConnectionPool pool;

    public VacancyDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public VacancyDao create() {
        return new VacancyDao(pool.getConnection());
    }

    @Override
    public VacancyDao create(Connection connection) {
        return new VacancyDao(connection);
    }
}
