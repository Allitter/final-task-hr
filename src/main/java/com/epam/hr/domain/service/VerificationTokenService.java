package com.epam.hr.domain.service;

import com.epam.hr.domain.model.VerificationToken;
import com.epam.hr.exception.ServiceException;

import java.util.List;

/**
 * Provides ability to works with VerificationToken entities
 */
public interface VerificationTokenService {
    /**
     * @param idUser user's id
     * @return list with user's tokens or empty list
     * @throws ServiceException if error occurs
     */
    List<VerificationToken> findUserTokens(long idUser) throws ServiceException;

    /**
     * Remove token if exists
     *
     * @param idVerificationToken verification's token id
     * @throws ServiceException if error occurs
     */
    void remove(long idVerificationToken) throws ServiceException;

    /**
     * Removes all user tokens with expired time
     *
     * @param idUser user's id
     * @throws ServiceException if error occurs
     */
    void removeExpiredTokens(long idUser) throws ServiceException;

    /**
     * Adds or updates token
     *
     * @param verificationToken verification token
     * @throws ServiceException if error occurs
     */
    void save(VerificationToken verificationToken) throws ServiceException;
}
