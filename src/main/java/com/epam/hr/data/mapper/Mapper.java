package com.epam.hr.data.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * maps entity from result set
 *
 * @param <T> can be any object
 */
public interface Mapper<T> {

    /**
     * @param resultSet is result set provided by dao
     * @return object mapped
     * @throws SQLException if sql error occurs
     */
    T map(ResultSet resultSet) throws SQLException;

    /**
     * Provides ability to map entity from another mapper
     *
     * @param resultSet is result set provided by dao
     * @return object mapped
     * @throws SQLException if sql error occurs
     */
    T mapForAnotherEntity(ResultSet resultSet) throws SQLException;
}
