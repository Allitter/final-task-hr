package com.epam.hr.data.dao;

import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.domain.model.Entity;
import com.epam.hr.exception.DaoException;

/**
 * Provides ability to create daos with shared connection and
 * create transactions as well
 */
public interface DaoManager extends AutoCloseable {

    /**
     * Creates dao with shared connection
     *
     * @param factory dao factory {@link DaoFactory}
     * @param <T>     particular dao interface
     * @return dao with shared connection
     */
    <T extends Dao<? extends Entity>> T addDao(DaoFactory<T> factory);

    /**
     * Starts transaction
     *
     * @throws DaoException if error occurs
     */
    void beginTransaction() throws DaoException;

    /**
     * Commits transaction
     *
     * @throws DaoException if error occurs
     */
    void commit() throws DaoException;

    /**
     * ends transaction and closes shared connection
     *
     * @throws DaoException if error occurs
     */
    @Override
    void close() throws DaoException;
}
