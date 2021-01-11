package com.epam.hr.data.dao;

import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.exception.DaoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionHelper implements AutoCloseable {
    private final Connection connection;
    private final List<AbstractDao> registeredDaos;

    public TransactionHelper(Connection connection) {
        this.connection = connection;
        registeredDaos = new ArrayList<>();
    }

    public <T extends AbstractDao> T addDao(DaoFactory<T> factory) {
        T dao = factory.create(connection);
        registeredDaos.add(dao);
        return dao;
    }

    public void beginTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    void endTransaction() throws DaoException {
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

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
        registeredDaos.forEach(AbstractDao::eraseConnection);
        rollback();
        endTransaction();
    }
}
