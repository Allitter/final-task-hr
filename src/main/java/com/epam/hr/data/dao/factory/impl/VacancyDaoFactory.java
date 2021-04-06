package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.dao.VacancyDao;
import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.VacancyDaoImpl;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.data.mapper.impl.VacancyMapper;
import com.epam.hr.data.pool.ConnectionPool;
import com.epam.hr.domain.model.Vacancy;

import java.sql.Connection;

public class VacancyDaoFactory implements DaoFactory<VacancyDao> {
    private static final Mapper<Vacancy> MAPPER = new VacancyMapper();
    private final ConnectionPool pool;

    public VacancyDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public VacancyDao create() {
        return new VacancyDaoImpl(pool.getConnection(), MAPPER);
    }

    @Override
    public VacancyDao create(Connection connection) {
        return new VacancyDaoImpl(connection, MAPPER, false);
    }
}
