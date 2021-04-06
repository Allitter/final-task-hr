package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.BanDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.Ban;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class BanDaoImpl extends AbstractDao<Ban> implements BanDao {
    private static final String TABLE = "ban";
    private static final String INSERT_QUERY = String.format("insert into %s (reason, id_target, id_administrant) values (?, ?, ?);", TABLE);
    private static final String UPDATE_QUERY = String.format("update %s set reason = ? where id = ?;", TABLE);
    private static final String LAST_BAN_QUERY = String.format("select * from %s where id_target = ? and `ban`.time = (select max(time) from ban where id_target = ?) and removed = 0;", TABLE);
    private final Mapper<Ban> mapper;

    public BanDaoImpl(Connection connection, Mapper<Ban> mapper) {
        this(connection, mapper, true);
    }

    public BanDaoImpl(Connection connection, Mapper<Ban> mapper, boolean canCloseConnection) {
        super(canCloseConnection, connection);
        this.mapper = mapper;
    }

    @Override
    public Optional<Ban> findById(long id) throws DaoException {
        return findById(TABLE, mapper, id);
    }

    @Override
    public Optional<Ban> findLast(long idTarget) throws DaoException {
        return executeSingleResultQueryPrepared(LAST_BAN_QUERY, mapper, idTarget, idTarget);
    }

    @Override
    public List<Ban> findAll(int start, int count) throws DaoException {
        return findAll(TABLE, mapper, start, count);
    }

    @Override
    public void save(Ban ban) throws DaoException {
        if (!ban.isValid()) {
            throw new DaoException("attempt to save invalid object");
        }

        String message = ban.getReason();
        long idTarget = ban.getIdTarget();
        long idAdministrant = ban.getIdAdministrant();
        long id = ban.getId();

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
