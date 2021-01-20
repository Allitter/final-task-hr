package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.AbstractDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.Ban;
import com.epam.hr.exception.DaoException;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class BanDao extends AbstractDao<Ban> {
    private static final String TABLE = "ban";
    private static final String INSERT_QUERY = String.format("insert into %s (reason, id_target, id_administrant) values (?, ?, ?);", TABLE);
    private static final String UPDATE_QUERY = String.format("update %s set reason = ? where id = ?;", TABLE);
    private static final String LAST_BAN_QUERY = String.format("select *, max(time) from %s GROUP BY id_target having id_target = ?;", TABLE);
    private final Mapper<Ban> mapper;

    public BanDao(Connection connection, Mapper<Ban> mapper) {
        super(connection);
        this.mapper = mapper;
    }

    @Override
    public Optional<Ban> findById(long id) throws DaoException {
        return findById(TABLE, mapper, id);
    }

    public Optional<Ban> findLast(long idTarget) throws DaoException {
        return executeSingleResultQueryPrepared(LAST_BAN_QUERY, mapper, idTarget);
    }

    @Override
    public List<Ban> findAll(int start, int count) throws DaoException {
        return findAll(TABLE, mapper, start, count);
    }

    @Override
    public void save(Ban item) throws DaoException {
        String message = item.getReason();
        long idTarget = item.getIdTarget();
        long idAdministrant = item.getIdAdministrant();
        long id = item.getId();

        Optional<Ban> optional = findById(id);
        if (!optional.isPresent()) {
            executeNoResultQueryPrepared(INSERT_QUERY, message, idTarget, idAdministrant);
        } else {
            executeNoResultQueryPrepared(UPDATE_QUERY, message, id);
        }
    }

    @Override
    public void removeById(long id) throws DaoException {
        removeById(TABLE, id);
    }

    @Override
    public int getRowCount() throws DaoException {
        return getRowCount(TABLE);
    }
}
