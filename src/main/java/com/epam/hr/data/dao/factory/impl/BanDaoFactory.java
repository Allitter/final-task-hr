package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.BanDaoImpl;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.data.mapper.impl.BanMapper;
import com.epam.hr.data.pool.ConnectionPool;
import com.epam.hr.domain.model.Ban;

import java.sql.Connection;

public class BanDaoFactory implements DaoFactory<BanDaoImpl> {
    private static final Mapper<Ban> mapper = new BanMapper();
    private final ConnectionPool pool;

    public BanDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public BanDaoImpl create() {
        return new BanDaoImpl(pool.getConnection(), mapper);
    }

    @Override
    public BanDaoImpl create(Connection connection) {
        return new BanDaoImpl(connection, mapper, false);
    }
}
