package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.AbstractDao;
import com.epam.hr.exception.DaoException;
import com.epam.hr.data.mapper.impl.VacancyMapper;
import com.epam.hr.domain.model.Vacancy;
import java.util.List;
import java.util.Optional;

public class VacanciesDao extends AbstractDao<Vacancy> {
    private static final String TABLE = "vacancy";
    private static final String UPDATE_QUERY = String.format("update %s set name = ?, short_description = ?, description = ? where id = ?;", TABLE);
    private static final String INSERT_QUERY = String.format("insert into %s (name, short_description, description) values (?, ?, ?);", TABLE);

    private final VacancyMapper mapper = new VacancyMapper();

    @Override
    public Optional<Vacancy> getById(long id) throws DaoException {
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
    public int findQuantity() throws DaoException {
        return findQuantity(TABLE);
    }

    @Override
    public void save(Vacancy vacancy) throws DaoException {
        long id = vacancy.getId();
        String name = vacancy.getName();
        String shortDescription = vacancy.getShortDescription();
        String description = vacancy.getDescription();

        Optional<Vacancy> optional = findById(TABLE, mapper, id);
        if (optional.isPresent()) {
            executeNoResultQueryPrepared(UPDATE_QUERY, name, shortDescription, description, id);
        } else {
            executeNoResultQueryPrepared(INSERT_QUERY, name, shortDescription, description);
        }
    }
}