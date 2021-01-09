package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.ConnectionPool;
import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.UserDao;

import java.sql.Connection;

public class UserDaoFactory implements DaoFactory<UserDao> {
    private final ConnectionPool pool;

    public UserDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public UserDao create() {
        return new UserDao(pool.getConnection());
    }

    @Override
    public UserDao create(Connection connection) {
        return new UserDao(connection);
    }
}
