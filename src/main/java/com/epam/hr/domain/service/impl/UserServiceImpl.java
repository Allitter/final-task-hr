package com.epam.hr.domain.service.impl;

import com.epam.hr.data.dao.TransactionHelper;
import com.epam.hr.data.dao.factory.impl.BanDaoFactory;
import com.epam.hr.data.dao.factory.impl.UserDaoFactory;
import com.epam.hr.data.dao.impl.BanDao;
import com.epam.hr.data.dao.impl.UserDao;
import com.epam.hr.domain.model.Ban;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.domain.validator.UserValidator;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import java.util.*;

public class UserServiceImpl implements UserService {
    private static final String LOGIN_NOT_UNIQUE_FAIL = "loginNotUnique";
    private static final String INCORRECT_LOGIN_OR_PASSWORD = "incorrectLoginOrPassword";
    private final UserValidator userValidator;
    private final UserDaoFactory userDaoFactory;
    private final BanDaoFactory banDaoFactory;

    public UserServiceImpl(UserValidator userValidator, UserDaoFactory userDaoFactory, BanDaoFactory banDaoFactory) {
        this.userValidator = userValidator;
        this.userDaoFactory = userDaoFactory;
        this.banDaoFactory = banDaoFactory;
    }

    @Override
    public User authenticateUser(String login, String password) throws ServiceException {
        boolean isValid = userValidator.validateLogin(login)
                && userValidator.validatePassword(password);
        if (!isValid) {
            throw new ValidationException(INCORRECT_LOGIN_OR_PASSWORD);
        }

        try(UserDao dao = userDaoFactory.create()) {
            Optional<User> optionalUser = dao.findUserByLoginAndPassword(login, password);
            if (!optionalUser.isPresent()) {
                throw new ValidationException(INCORRECT_LOGIN_OR_PASSWORD);
            }

            return optionalUser.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> banUser(long idTarget, long idAdministrant, String message) throws ServiceException {
        Ban ban = new Ban.Builder()
                .setReason(message)
                .setIdTarget(idTarget)
                .setIdAdministrant(idAdministrant)
                .build(true);

        try(TransactionHelper transactionHelper = new TransactionHelper()) {
            BanDao banDao = transactionHelper.addDao(banDaoFactory);
            UserDao userDao = transactionHelper.addDao(userDaoFactory);

            transactionHelper.beginTransaction();

            banDao.save(ban);
            userDao.banUser(idTarget);

            transactionHelper.commit();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return findById(idTarget);
    }

    @Override
    public Optional<User> unbanUser(long id) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            dao.unbanUser(id);
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<Ban> findLastBan(long idUser) throws ServiceException {
        try(BanDao dao = banDaoFactory.create()) {
            return dao.findLast(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Ban tryFindLastBan(long idUser) throws ServiceException {
        Optional<Ban> optional = findLastBan(idUser);
        if (!optional.isPresent()) {
            throw new ServiceException("No ban records found");
        }

        return optional.get();
    }

    @Override
    public Optional<User> findById(long id) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User tryFindById(long id) throws ServiceException {
        Optional<User> optionalUser = findById(id);
        if (!optionalUser.isPresent()) {
            throw new ServiceException("User doesn't exist");
        }

        return optionalUser.get();
    }

    @Override
    public List<User> findEmployees(int start, int count) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findAll(User.Role.EMPLOYEE, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findJobSeekers(int start, int count) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findAll(User.Role.JOB_SEEKER, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findEmployeesQuantity() throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findQuantity(User.Role.EMPLOYEE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findJobSeekersQuantity() throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findQuantity(User.Role.JOB_SEEKER);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User addUser(User user) throws ServiceException {
        List<String> validationFails = userValidator.getValidationFailsForRegister(user);
        user = new User.Builder(user)
                .setRole(User.Role.JOB_SEEKER)
                .setEnabled(false)
                .build();

        try(UserDao dao = userDaoFactory.create()) {
            Optional<User> userOptional = dao.findByLogin(user.getLogin());
            if (userOptional.isPresent()) {
                validationFails.add(LOGIN_NOT_UNIQUE_FAIL);
            }
            if (!validationFails.isEmpty()) {
                throw new ValidationException(validationFails);
            }

            dao.save(user);
            Optional<User> optionalUser = dao.findByLogin(user.getLogin());
            return optionalUser.get(); // Always present as it is recently saved
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        List<String> validationFails = userValidator.getValidationFailsForUpdate(user);

        try(UserDao dao = userDaoFactory.create()) {
            Optional<User> userOptional = dao.findByLogin(user.getLogin());
            if (userOptional.isPresent()) {
                User userByEmail = userOptional.get();
                if (userByEmail.getId() != user.getId()) {
                    validationFails.add(LOGIN_NOT_UNIQUE_FAIL);
                }
            }
            if (!validationFails.isEmpty()) {
                throw new ValidationException(validationFails);
            }

            dao.save(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User enable(long id) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            Optional<User> optionalUser = dao.findById(id);
            if (!optionalUser.isPresent()) {
                throw new ServiceException("User not exists");
            }
            User user = optionalUser.get();
            user = new User.Builder(user)
                    .setEnabled(true)
                    .build();

            dao.save(user);

            return user;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
