package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.BanDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.data.mapper.impl.BanMapper;
import com.epam.hr.data.pool.ConnectionPool;
import com.epam.hr.domain.model.Ban;

import java.sql.Connection;

public class BanDaoFactory implements DaoFactory<BanDao> {
    private static final Mapper<Ban> mapper = new BanMapper();
    private final ConnectionPool pool;

    public BanDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public BanDao create() {
        return new BanDao(pool.getConnection(), mapper);
    }

    @Override
    public BanDao create(Connection connection) {
        return new BanDao(connection, mapper);
    }
}
