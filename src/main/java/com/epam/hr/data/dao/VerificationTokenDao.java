package com.epam.hr.data.dao;

import com.epam.hr.domain.model.VerificationToken;
import com.epam.hr.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenDao extends Dao<VerificationToken> {
    /**
     * @param id entity's id
     * @return optional of verification token
     * @throws DaoException if sql error occurs
     */
    @Override
    Optional<VerificationToken> findById(long id) throws DaoException;

    /**
     * @param idUser user's id
     * @return list of user's verification tokens
     * @throws DaoException if sql error occurs
     */
    List<VerificationToken> findByUserId(long idUser) throws DaoException;

    /**
     * @param start first entity position inclusive
     * @param count entities count
     * @return list of verification tokens
     * @throws DaoException if sql error occurs
     */
    @Override
    List<VerificationToken> findAll(int start, int count) throws DaoException;

    /**
     * @param id id of entity to removes
     * @throws DaoException if sql error occurs
     */
    @Override
    void removeById(long id) throws DaoException;

    /**
     * Removes all tokens with expired date
     *
     * @param idUser user's id
     * @throws DaoException if sql error occurs
     */
    void removeRemoveExpiredTokens(long idUser) throws DaoException;

    /**
     * @return count of verification tokens
     * @throws DaoException if sql error occurs
     */
    @Override
    int getRowCount() throws DaoException;

    /**
     * @param verificationToken entity to add or update
     * @throws DaoException if sql error occurs
     */
    @Override
    void save(VerificationToken verificationToken) throws DaoException;
}
