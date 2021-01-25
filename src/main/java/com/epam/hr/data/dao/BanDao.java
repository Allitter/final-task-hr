package com.epam.hr.data.dao;

import com.epam.hr.domain.model.Ban;
import com.epam.hr.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BanDao extends Dao<Ban> {
    /**
     * @param id entity's id
     * @return optional of Ban
     * @throws DaoException if sql error occurs
     */
    @Override
    Optional<Ban> findById(long id) throws DaoException;

    /**
     * @param idTarget id of banned user
     * @return optional of Ban
     * @throws DaoException if sql error occurs
     */
    Optional<Ban> findLast(long idTarget) throws DaoException;

    /**
     * @param start first entity position inclusive
     * @param count entities count
     * @return list of Ban entities or empty list
     * @throws DaoException if sql error occurs
     */
    @Override
    List<Ban> findAll(int start, int count) throws DaoException;

    /**
     * @param ban entity to add or update
     * @throws DaoException if sql error occurs
     */
    @Override
    void save(Ban ban) throws DaoException;

    /**
     * @param id id of entity to remove
     * @throws DaoException if sql error occurs
     */
    @Override
    void removeById(long id) throws DaoException;

    /**
     * @return number Ban entities
     * @throws DaoException if sql error occurs
     */
    @Override
    int getRowCount() throws DaoException;
}
