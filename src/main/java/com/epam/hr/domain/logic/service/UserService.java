package com.epam.hr.domain.logic.service;

import com.epam.hr.data.dao.factory.impl.UserDaoFactory;
import com.epam.hr.data.dao.impl.UserDao;
import com.epam.hr.domain.logic.validator.UserValidator;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.UserRole;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private static final String LOGIN_NOT_UNIQUE_FAIL = "loginNotUnique";
    private static final String INCORRECT_LOGIN_OR_PASSWORD = "incorrectLoginOrPassword";
    private final UserValidator userValidator;
    private final UserDaoFactory userDaoFactory;

    public UserService(UserValidator userValidator, UserDaoFactory userDaoFactory) {
        this.userValidator = userValidator;
        this.userDaoFactory = userDaoFactory;
    }

    public User authenticateUser(String login, String password) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            List<String> fails = new LinkedList<>();
            fails.addAll(userValidator.validateLogin(login));
            fails.addAll(userValidator.validatePassword(password));
            if (!fails.isEmpty()) {
                throw new ValidationException(fails);
            }

            Optional<User> optionalUser = dao.getUserByLoginAndPassword(login, password);
            if (!optionalUser.isPresent()) {
                throw new ValidationException(Collections.singletonList(INCORRECT_LOGIN_OR_PASSWORD));
            }

            return optionalUser.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void banUser(long id) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
           dao.banUser(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void unbanUser(long id) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            dao.unbanUser(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<User> findById(long id) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<User> findEmployees(int start, int count) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findAll(UserRole.EMPLOYEE, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<User> findJobSeekers(int start, int count) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findAll(UserRole.JOB_SEEKER, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int findEmployeesQuantity() throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findQuantity(UserRole.EMPLOYEE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int findJobSeekersQuantity() throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            return dao.findQuantity(UserRole.JOB_SEEKER);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public User saveUser(User user) throws ServiceException {
        try(UserDao dao = userDaoFactory.create()) {
            long id = user.getId();
            List<String> fails;
            if (id == -1) {
                fails = userValidator.validateForRegister(user);
            } else {
                fails = userValidator.validateForUpdate(user);
            }
            if (fails.isEmpty()) {
                String login = user.getLogin();
                Optional<User> optionalUser = dao.findByLogin(login);
                if (optionalUser.isPresent() && id != optionalUser.get().getId()) {
                    fails.add(LOGIN_NOT_UNIQUE_FAIL);
                }
            }
            if (!fails.isEmpty()) {
                throw new ValidationException(fails);
            }

            dao.save(user);
            String login = user.getLogin();
            Optional<User> optionalUser = dao.findByLogin(login);
            return optionalUser.get();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
