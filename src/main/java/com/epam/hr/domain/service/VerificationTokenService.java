package com.epam.hr.domain.service;

import com.epam.hr.domain.model.VerificationToken;
import com.epam.hr.exception.ServiceException;

import java.util.List;

public interface VerificationTokenService {
    List<VerificationToken> findUserTokens(long idUser) throws ServiceException;

    void remove(VerificationToken verificationToken) throws ServiceException;

    void save(VerificationToken verificationToken) throws ServiceException;

    void removeExpiredTokens(long idUser) throws ServiceException;
}
