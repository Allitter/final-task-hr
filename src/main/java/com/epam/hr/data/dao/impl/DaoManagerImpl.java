package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.Dao;
import com.epam.hr.data.dao.DaoManager;
import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.pool.ConnectionPool;
import com.epam.hr.domain.model.Entity;
import com.epam.hr.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoManagerImpl implements DaoManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Connection connection;
    private final List<Dao<? extends Entity>> registeredDaos;
    private boolean autocommit;

    public DaoManagerImpl() {
        this.connection = ConnectionPool.getInstance().getConnection();
        registeredDaos = new ArrayList<>();
        autocommit = true;
    }

    public <T extends Dao<? extends Entity>> T addDao(DaoFactory<T> factory) {
        T dao = factory.create(connection);
        registeredDaos.add(dao);
        return dao;
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
