package com.epam.hr.data.dao.factory;

import com.epam.hr.data.dao.Dao;
import com.epam.hr.domain.model.Entity;

import java.sql.Connection;

/**
 * @param <T> particular dao interface
 */
public interface DaoFactory<T extends Dao<? extends Entity>> {

    /**
     * Creates doa with own connection
     *
     * @return dao T
     */
    T create();

    /**
     * Creates doa with provided connection
     * this connection won't be closed by method close() in dao
     *
     * @param connection provided connection
     * @return dao T
     */
    T create(Connection connection);

}
