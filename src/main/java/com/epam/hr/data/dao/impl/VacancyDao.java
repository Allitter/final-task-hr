package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.AbstractDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class VacancyDao extends AbstractDao<Vacancy> {
    private static final String TABLE = "vacancy";
    private static final String UPDATE_QUERY = String.format("update %s set name = ?, short_description = ?, description = ?, closed = ?, removed = ? where id = ?;", TABLE);
    private static final String INSERT_QUERY = String.format("insert into %s (name, short_description, description) values (?, ?, ?);", TABLE);
    private final Mapper<Vacancy> mapper;

    public VacancyDao(Connection connection, Mapper<Vacancy> mapper) {
        super(connection);
        this.mapper = mapper;
    }

    @Override
    public Optional<Vacancy> findById(long id) throws DaoException {
        return findById(TABLE, mapper,id);
    }

    @Override
    public List<Vacancy> findAll(int start, int count) throws DaoException {
        return findAll(TABLE, mapper, start, count);
    }

    @Override
    public void removeById(long id) throws DaoException {
        removeById(TABLE, id);
    }

    @Override
    public int getRowCount() throws DaoException {
        return getRowCount(TABLE);
    }

    @Override
    public void save(Vacancy vacancy) throws DaoException {
        long id = vacancy.getId();
        String name = vacancy.getName();
        String shortDescription = vacancy.getShortDescription();
        String description = vacancy.getDescription();
        boolean closed = vacancy.isClosed();
        boolean removed = vacancy.isRemoved();

        Optional<Vacancy> optional = findById(TABLE, mapper, id);
        if (optional.isPresent()) {
            executeNoResultQueryPrepared(UPDATE_QUERY, name, shortDescription, description, closed, removed, id);
        } else {
            executeNoResultQueryPrepared(INSERT_QUERY, name, shortDescription, description);
        }
    }
}
