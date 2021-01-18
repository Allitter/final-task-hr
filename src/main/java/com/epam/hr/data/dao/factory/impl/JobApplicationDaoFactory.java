package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.JobApplicationDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.data.mapper.impl.JobApplicationMapper;
import com.epam.hr.data.mapper.impl.UserMapper;
import com.epam.hr.data.mapper.impl.VacancyMapper;
import com.epam.hr.data.pool.ConnectionPool;
import com.epam.hr.domain.model.JobApplication;

import java.sql.Connection;

public class JobApplicationDaoFactory implements DaoFactory<JobApplicationDao> {
    private static final Mapper<JobApplication> MAPPER = new JobApplicationMapper(new VacancyMapper(), new UserMapper());
    private final ConnectionPool pool;

    public JobApplicationDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public JobApplicationDao create() {
        return new JobApplicationDao(pool.getConnection(), MAPPER);
    }

    @Override
    public JobApplicationDao create(Connection connection) {
        return new JobApplicationDao(connection, MAPPER);
    }
}
