package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.dao.ResumeDao;
import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.ResumeDaoImpl;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.data.mapper.impl.ResumeMapper;
import com.epam.hr.data.pool.ConnectionPool;
import com.epam.hr.domain.model.Resume;

import java.sql.Connection;

public class ResumeDaoFactory implements DaoFactory<ResumeDao> {
    private static final Mapper<Resume> MAPPER = new ResumeMapper();
    private final ConnectionPool pool;

    public ResumeDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public ResumeDao create() {
        return new ResumeDaoImpl(pool.getConnection(), MAPPER);
    }

    @Override
    public ResumeDao create(Connection connection) {
        return new ResumeDaoImpl(connection, MAPPER, false);
    }
}
