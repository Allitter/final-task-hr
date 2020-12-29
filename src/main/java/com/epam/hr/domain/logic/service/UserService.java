package com.epam.hr.domain.logic.service;

import com.epam.hr.data.dao.UserDao;
import com.epam.hr.domain.model.UserRole;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.domain.model.User;

import java.util.List;
import java.util.Optional;

public class UserService {

    public Optional<User> authenticateUser(String login, String password) throws ServiceException {
        Optional<User> result;
        try(UserDao dao = new UserDao()) {
            result = dao.getUserByLoginAndPassword(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    public List<User> findEmployees(int start, int count) throws ServiceException {
        List<User> result;
        try(UserDao dao = new UserDao()) {
            result = dao.findAll(UserRole.EMPLOYEE, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    public List<User> findJobSeekers(int start, int count) throws ServiceException {
        List<User> result;
        try(UserDao dao = new UserDao()) {
            result = dao.findAll(UserRole.JOB_SEEKER, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    public int findEmployeesQuantity() throws ServiceException {
        int result;
        try(UserDao dao = new UserDao()) {
            result = dao.findQuantity(UserRole.EMPLOYEE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    public int findJobSeekersQuantity() throws ServiceException {
        int result;
        try(UserDao dao = new UserDao()) {
            result = dao.findQuantity(UserRole.JOB_SEEKER);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    public boolean isLoginUnique(String login) {
        // TODO implement
        return true;
    }

    public void saveUser(User user) throws ServiceException {
        try(UserDao dao = new UserDao()) {
            dao.save(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
