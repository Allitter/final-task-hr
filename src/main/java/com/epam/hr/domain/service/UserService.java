package com.epam.hr.domain.service;

import com.epam.hr.domain.model.Ban;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User authenticateUser(String login, String password) throws ServiceException;

    Optional<User> banUser(long idTarget, long idAdministrant, String message) throws ServiceException;

    Optional<User> unbanUser(long id) throws ServiceException;

    Ban tryFindLastBan(long idUser) throws ServiceException;

    Optional<User> findById(long id) throws ServiceException;

    User tryFindById(long id) throws ServiceException;

    List<User> findEmployees(int start, int count) throws ServiceException;

    List<User> findJobSeekers(int start, int count) throws ServiceException;

    int findEmployeesQuantity() throws ServiceException;

    int findJobSeekersQuantity() throws ServiceException;

    User addUser(User user) throws ServiceException;

    void updateUser(User user) throws ServiceException;

    User enable(long id) throws ServiceException;
}
