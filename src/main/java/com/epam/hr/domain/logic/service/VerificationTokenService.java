package com.epam.hr.domain.logic.service;

import com.epam.hr.data.dao.factory.impl.VerificationTokenDaoFactory;
import com.epam.hr.data.dao.impl.VerificationTokenDao;
import com.epam.hr.domain.model.VerificationToken;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;

import java.util.List;

public class VerificationTokenService {
    private final VerificationTokenDaoFactory verificationTokenDaoFactory;

    public VerificationTokenService(VerificationTokenDaoFactory verificationTokenDaoFactory) {
        this.verificationTokenDaoFactory = verificationTokenDaoFactory;
    }

    public List<VerificationToken> findUserTokens(long idUser) throws ServiceException {
        try (VerificationTokenDao dao = verificationTokenDaoFactory.create()) {
            return dao.findByUserId(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void remove(VerificationToken verificationToken) throws ServiceException {
        try (VerificationTokenDao dao = verificationTokenDaoFactory.create()) {
            long id = verificationToken.getId();
            dao.removeById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void save(VerificationToken verificationToken) throws ServiceException {
        try (VerificationTokenDao dao = verificationTokenDaoFactory.create()) {
            dao.save(verificationToken);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void removeExpiredTokens(long idUser) throws ServiceException {
        try (VerificationTokenDao dao = verificationTokenDaoFactory.create()) {
            dao.removeRemoveExpiredTokens(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
