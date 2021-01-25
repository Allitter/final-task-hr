package com.epam.hr.data.dao;

import com.epam.hr.domain.model.Entity;
import com.epam.hr.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Defines common dao methods
 *
 * @param <E> extends {@link Entity}
 */
public interface Dao<E extends Entity> extends AutoCloseable {

    /**
     * @param id entity's id
     * @return optional with entity if such exists or empty optional
     * @throws DaoException if sql error occurs
     */
    Optional<E> findById(long id) throws DaoException;

    /**
     * Recommended to use with {@link Dao#getRowCount()}
     *
     * @param start first entity position inclusive
     * @param count entities count
     * @return list of particular entities or empty list
     * @throws DaoException if sql error occurs
     */
    List<E> findAll(int start, int count) throws DaoException;

    /**
     * Adds or updates entity in database
     *
     * @param item entity to save or update
     * @throws DaoException if sql error occurs
     */
    void save(E item) throws DaoException;

    /**
     * @param id id of entity to removes
     * @throws DaoException if sql error occurs
     */
    void removeById(long id) throws DaoException;

    /**
     * @return count of entities
     * @throws DaoException if sql error occurs
     */
    int getRowCount() throws DaoException;

    /**
     * Closes the dao
     *
     * @throws DaoException if sql error occurs
     */
    @Override
    void close() throws DaoException;
}
