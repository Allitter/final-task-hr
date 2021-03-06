package com.epam.hr.domain.service.impl;

import com.epam.hr.data.dao.VerificationTokenDao;
import com.epam.hr.data.dao.factory.impl.VerificationTokenDaoFactory;
import com.epam.hr.domain.model.VerificationToken;
import com.epam.hr.domain.service.VerificationTokenService;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;

import java.util.List;

public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenDaoFactory verificationTokenDaoFactory;

    public VerificationTokenServiceImpl(VerificationTokenDaoFactory verificationTokenDaoFactory) {
        this.verificationTokenDaoFactory = verificationTokenDaoFactory;
    }

    @Override
    public List<VerificationToken> findUserTokens(long idUser) throws ServiceException {
        try (VerificationTokenDao dao = verificationTokenDaoFactory.create()) {
            return dao.findByUserId(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void remove(long idVerificationToken) throws ServiceException {
        try (VerificationTokenDao dao = verificationTokenDaoFactory.create()) {
            dao.removeById(idVerificationToken);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(VerificationToken verificationToken) throws ServiceException {
        try (VerificationTokenDao dao = verificationTokenDaoFactory.create()) {
            dao.save(verificationToken);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeExpiredTokens(long idUser) throws ServiceException {
        try (VerificationTokenDao dao = verificationTokenDaoFactory.create()) {
            dao.removeRemoveExpiredTokens(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
