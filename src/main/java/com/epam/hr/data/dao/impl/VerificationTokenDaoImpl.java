package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.VerificationTokenDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.VerificationToken;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class VerificationTokenDaoImpl extends AbstractDao<VerificationToken> implements VerificationTokenDao {
    private static final String TABLE = "verification_token";
    private static final String INSERT_QUERY = String.format("insert into %s (id_user, code, expiration_date) values (?, ?, ?);", TABLE);
    private static final String UPDATE_QUERY = String.format("update %s set code = ? where id = ?;", TABLE);
    private static final String TOKENS_BY_USER_ID = String.format("select * from %s where id_user = ? and removed = 0;", TABLE);
    private static final String REMOVE_EXPIRED_TOKENS = String.format("update %s set removed = 1 where id_user = ? and expiration_date < NOW();", TABLE);
    private final Mapper<VerificationToken> mapper;

    public VerificationTokenDaoImpl(Connection connection, Mapper<VerificationToken> mapper) {
        this(connection, mapper, true);
    }

    public VerificationTokenDaoImpl(Connection connection, Mapper<VerificationToken> mapper, boolean canCloseConnection) {
        super(canCloseConnection, connection);
        this.mapper = mapper;
    }

    @Override
    public Optional<VerificationToken> findById(long id) throws DaoException {
        return super.findById(TABLE, mapper, id);
    }

    @Override
    public List<VerificationToken> findByUserId(long idUser) throws DaoException {
        return executeQueryPrepared(TOKENS_BY_USER_ID, mapper, idUser);
    }

    @Override
    public List<VerificationToken> findAll(int start, int count) throws DaoException {
        return super.findAll(TABLE, mapper, start, count);
    }

    @Override
    public void removeById(long id) throws DaoException {
        super.removeById(TABLE, id);
    }

    @Override
    public void removeRemoveExpiredTokens(long idUser) throws DaoException {
        super.executeNoResultQueryPrepared(REMOVE_EXPIRED_TOKENS, idUser);
    }

    @Override
    public int getRowCount() throws DaoException {
        return super.getRowCount(TABLE);
    }

    @Override
    public void save(VerificationToken verificationToken) throws DaoException {
        if (!verificationToken.isValid()) {
            throw new DaoException("attempt to save invalid object");
        }

        long id = verificationToken.getId();
        long idUser = verificationToken.getIdUser();
        String code = verificationToken.getCode();
        Date expirationDate = verificationToken.getExpirationDate();

        Optional<VerificationToken> optional = findById(id);
        if (optional.isPresent()) {
            executeNoResultQueryPrepared(UPDATE_QUERY, code, id);
        } else {
            executeNoResultQueryPrepared(INSERT_QUERY, idUser, code, expirationDate);
        }
    }
}
