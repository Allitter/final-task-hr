package com.epam.hr.data.dao;

import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.DaoException;

import java.util.List;
import java.util.Optional;


public interface VacancyDao extends Dao<Vacancy> {
    /**
     * @param id entity's id
     * @return optional of vacancy
     * @throws DaoException if sql error occurs
     */
    @Override
    Optional<Vacancy> findById(long id) throws DaoException;

    /**
     * @param start first entity position inclusive
     * @param count entities count
     * @return list of vacancies or empty list
     * @throws DaoException if sql error occurs
     */
    @Override
    List<Vacancy> findAll(int start, int count) throws DaoException;

    /**
     * @param id id of entity to removes
     * @throws DaoException if sql error occurs
     */
    @Override
    void removeById(long id) throws DaoException;

    /**
     * @return count of vacancies
     * @throws DaoException if sql error occurs
     */
    @Override
    int getRowCount() throws DaoException;

    /**
     * @param vacancy entity to add or update
     * @throws DaoException if sql error occurs
     */
    @Override
    void save(Vacancy vacancy) throws DaoException;
}
