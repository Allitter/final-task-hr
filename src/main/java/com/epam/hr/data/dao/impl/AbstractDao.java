package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.Dao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.Entity;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<E extends Entity> implements Dao<E> {
    private static final String SELECT_ALL_QUERY = "select * from %s where removed = 0;";
    private static final String SELECT_ALL_WITH_LIMIT_QUERY = "select %s from %s where %sremoved = 0 limit ?, ?;";
    private static final String FIND_BY_ID_QUERY = "select %s from %s where id = ? and removed = 0;";
    private static final String REMOVE_BY_ID_QUERY = "update %s set removed = 1 where id = ?;";
    private static final String COUNT_ROWS_QUERY = "select count(*) as c from %s where removed = 0;";
    private static final String COUNT_ROWS_QUERY_WITH_CONDITION = "select count(*) as c from %s where %s and removed = 0;";
    private static final String COUNT_ATTRIBUTE_NAME = "c";
    private static final int FIRST_ENTITY_INDEX = 0;
    private static final String ALL_ATTRIBUTES = "*";
    private static final String DEFAULT_TABLE_PREFIX = "";
    private final boolean canCloseConnection;
    private final Connection connection;

    protected AbstractDao(boolean canCloseConnection, Connection connection) {
        this.canCloseConnection = canCloseConnection;
        this.connection = connection;
    }

    protected <T> List<T> executeQueryPrepared(String query, Mapper<T> mapper, Object... params) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 1; i <= params.length; i++) {
                statement.setObject(i, params[i - 1]);
            }

            ResultSet resultSet = statement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected <T> Optional<T> executeSingleResultQueryPrepared(String query, Mapper<T> mapper, Object... params) throws DaoException {
        List<T> entities = executeQueryPrepared(query, mapper, params);

        Optional<T> optional;
        if (!entities.isEmpty()) {
            T entity = entities.get(FIRST_ENTITY_INDEX);
            optional = Optional.of(entity);
        } else {
            optional = Optional.empty();
        }

        return optional;
    }

    protected void executeNoResultQueryPrepared(String query, Object... params) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 1; i <= params.length; i++) {
                statement.setObject(i, params[i - 1]);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected int getRowCount(String tableName) throws DaoException {
        String query = String.format(COUNT_ROWS_QUERY, tableName);
        return countRows(query);
    }

    protected int getRowCount(String tableName, String condition, Object... params) throws DaoException {
        String query = String.format(COUNT_ROWS_QUERY_WITH_CONDITION, tableName, condition);
        return countRows(query, params);
    }

    private int countRows(String query, Object... params) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 1; i <= params.length; i++) {
                statement.setObject(i, params[i - 1]);
            }

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(COUNT_ATTRIBUTE_NAME);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected Optional<E> findById(String tableName, Mapper<E> mapper, long id) throws DaoException {
        return findById(tableName, ALL_ATTRIBUTES, mapper, id);
    }

    protected Optional<E> findById(String tableName, String attributes, Mapper<E> mapper, long id) throws DaoException {
        String query = String.format(FIND_BY_ID_QUERY, attributes, tableName);
        return executeSingleResultQueryPrepared(query, mapper, id);
    }


    protected void removeById(String tableName, long id) throws DaoException {
        String query = String.format(REMOVE_BY_ID_QUERY, tableName);
        executeNoResultQueryPrepared(query, id);
    }

    protected List<E> findAll(String tableName, Mapper<E> mapper) throws DaoException {
        String query = String.format(SELECT_ALL_QUERY, tableName);
        return executeQueryPrepared(query, mapper);
    }

    protected List<E> findAll(String table, Mapper<E> mapper, int start, int count) throws DaoException {
        return findAll(table, ALL_ATTRIBUTES, mapper, start, count);
    }

    protected List<E> findAll(String table, String attributes, Mapper<E> mapper, int start, int count) throws DaoException {
        return findAll(table, attributes, DEFAULT_TABLE_PREFIX, mapper, start, count);
    }

    protected List<E> findAll(String table, String attributes, String tablePrefix, Mapper<E> mapper, int start, int count) throws DaoException {
        String query = String.format(SELECT_ALL_WITH_LIMIT_QUERY, attributes, table, tablePrefix);
        return executeQueryPrepared(query, mapper, start, count);
    }

    @Override
    public void close() throws DaoException {
        if (canCloseConnection) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }
}
