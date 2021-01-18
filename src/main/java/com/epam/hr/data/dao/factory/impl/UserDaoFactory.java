package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.UserDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.data.mapper.impl.UserMapper;
import com.epam.hr.data.pool.ConnectionPool;
import com.epam.hr.domain.model.User;

import java.sql.Connection;

public class UserDaoFactory implements DaoFactory<UserDao> {
    private static final Mapper<User> MAPPER = new UserMapper();
    private final ConnectionPool pool;

    public UserDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public UserDao create() {
        return new UserDao(pool.getConnection(), MAPPER);
    }

    @Override
    public UserDao create(Connection connection) {
        return new UserDao(connection, MAPPER);
    }
}
