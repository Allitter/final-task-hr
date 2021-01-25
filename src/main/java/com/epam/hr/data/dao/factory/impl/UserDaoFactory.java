package com.epam.hr.data.dao.factory.impl;

import com.epam.hr.data.dao.UserDao;
import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.UserDaoImpl;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.data.mapper.impl.DateMapper;
import com.epam.hr.data.mapper.impl.UserMapper;
import com.epam.hr.data.pool.ConnectionPool;
import com.epam.hr.domain.model.User;

import java.sql.Connection;
import java.util.Date;


public class UserDaoFactory implements DaoFactory<UserDao> {
    private static final Mapper<User> MAPPER = new UserMapper();
    private static final Mapper<Date> DATE_MAPPER = new DateMapper();
    private final ConnectionPool pool;

    public UserDaoFactory(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public UserDao create() {
        return new UserDaoImpl(pool.getConnection(), MAPPER, DATE_MAPPER);
    }

    @Override
    public UserDao create(Connection connection) {
        return new UserDaoImpl(connection, MAPPER, DATE_MAPPER, false);
    }
}
