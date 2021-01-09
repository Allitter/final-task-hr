package com.epam.hr.data.dao;

import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> implements Dao<T> {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SELECT_ALL_QUERY = "select * from %s;";
    private static final String SELECT_ALL_WITH_LIMIT_QUERY = "select * from %s limit ?, ?;";
    private static final String FIND_BY_ID_QUERY = "select * from %s where id = ?;";
    private static final String REMOVE_BY_ID_QUERY = "delete from %s where id = ?;";
    public static final String COUNT_ROWS_QUERY = "select count(*) as c from %s;";
    public static final String COUNT_ROWS_QUERY_WITH_CONDITION = "select count(*) as c from %s where %s;";
    public static final String COUNT_ATTRIBUTE_NAME = "c";
    public static final int FIRST_ENTITY_INDEX = 0;

    private Connection connection;

    protected AbstractDao(Connection connection) {
        this.connection = connection;
    }

    protected List<T> executeQuery(String query, Mapper<T> mapper) throws DaoException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
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

    protected List<T> executeQueryPrepared(String query, Mapper<T> mapper, Object... params) throws DaoException {
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

    protected Optional<T> executeSingleResultQueryPrepared(String query, Mapper<T> mapper, Object... params) throws DaoException {
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

            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected int findQuantity(String tableName) throws DaoException {
        String query = String.format(COUNT_ROWS_QUERY, tableName);
        return countRows(query);
    }

    protected int findQuantity(String tableName, String condition) throws DaoException {
        String query = String.format(COUNT_ROWS_QUERY_WITH_CONDITION, tableName, condition);
        return countRows(query);
    }

    private int countRows(String query) throws DaoException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(COUNT_ATTRIBUTE_NAME);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    protected Optional<T> findById(String tableName, Mapper<T> mapper, long id) throws DaoException {
        String query = String.format(FIND_BY_ID_QUERY, tableName);
        return executeSingleResultQueryPrepared(query, mapper, id);
    }

    protected void removeById(String tableName, long id) throws DaoException {
        String query = String.format(REMOVE_BY_ID_QUERY, tableName);
        executeNoResultQueryPrepared(query, id);
    }

    protected List<T> findAll(String tableName, Mapper<T> mapper) throws DaoException {
        String query = String.format(SELECT_ALL_QUERY, tableName);
        return executeQueryPrepared(query, mapper);
    }

    protected List<T> findAll(String tableName, Mapper<T> mapper, int start, int count) throws DaoException {
        String query = String.format(SELECT_ALL_WITH_LIMIT_QUERY, tableName);
        return executeQueryPrepared(query, mapper, start, count);
    }

    @Override
    public void close() {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    /* package private access for Transaction helper */
    void eraseConnection() {
        this.connection = null;
    }
}
