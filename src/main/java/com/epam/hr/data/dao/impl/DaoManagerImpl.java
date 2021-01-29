package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.Dao;
import com.epam.hr.data.dao.DaoManager;
import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.pool.ConnectionPool;
import com.epam.hr.domain.model.Entity;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoManagerImpl implements DaoManager {
    private final Connection connection;
    private boolean autocommit;

    public DaoManagerImpl() {
        this.connection = ConnectionPool.getInstance().getConnection();
        autocommit = true;
    }

    public <T extends Dao<? extends Entity>> T addDao(DaoFactory<T> factory) {
        return factory.create(connection);
    }

    @Override
    public void beginTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
            autocommit = false;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    void endTransaction() throws DaoException {
        try {
            connection.setAutoCommit(true);
            autocommit = true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    void rollback() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void close() throws DaoException {
        if (!autocommit) {
            rollback();
            endTransaction();
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException();
        }
    }
}
