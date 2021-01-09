package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.AbstractDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.data.mapper.impl.VerificationTokenMapper;
import com.epam.hr.domain.model.VerificationToken;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class VerificationTokenDao extends AbstractDao<VerificationToken> {
    private static final String TABLE = "verification_token";
    private static final String INSERT_QUERY = String.format("insert into %s (id_user, code, expiration_date) values (?, ?, ?);", TABLE) ;
    private static final String UPDATE_QUERY = String.format("update %s set code = ? where id = ?;", TABLE);
    private static final String TOKENS_BY_USER_ID = String.format("select * from %s where id_user = ?;", TABLE);
    private static final String REMOVE_EXPIRED_TOKENS = String.format("delete from %s where id_user = ? and expiration_date < NOW();", TABLE);

    private final Mapper<VerificationToken> mapper = new VerificationTokenMapper();

    public VerificationTokenDao(Connection connection) {
        super(connection);
    }

    @Override
    public Optional<VerificationToken> getById(long id) throws DaoException {
        return super.findById(TABLE, mapper, id);
    }

    public List<VerificationToken> findByUserId(long userId) throws DaoException {
        return executeQueryPrepared(TOKENS_BY_USER_ID, mapper, userId);
    }

    @Override
    public List<VerificationToken> findAll(int start, int count) throws DaoException {
        return super.findAll(TABLE, mapper, start, count);
    }

    @Override
    public void removeById(long id) throws DaoException {
        super.removeById(TABLE, id);
    }

    public void removeRemoveExpiredTokens(long idUser) throws DaoException {
        super.executeNoResultQueryPrepared(REMOVE_EXPIRED_TOKENS, idUser);
    }

    @Override
    public int findQuantity() throws DaoException {
        return super.findQuantity(TABLE);
    }

    @Override
    public void save(VerificationToken verificationToken) throws DaoException {
        long id = verificationToken.getId();
        long idUser = verificationToken.getIdUser();
        String code = verificationToken.getCode();
        Date expirationDate = verificationToken.getExpirationDate();

        Optional<VerificationToken> optional = getById(id);
        if (optional.isPresent()) {
            executeNoResultQueryPrepared(UPDATE_QUERY, code, id);
        } else {
            executeNoResultQueryPrepared(INSERT_QUERY, idUser, code, expirationDate);
        }
    }
}
