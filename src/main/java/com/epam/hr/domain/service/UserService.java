package com.epam.hr.domain.service;

import com.epam.hr.domain.model.Ban;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Provides ability to works with User entities
 */
public interface UserService {
    /**
     * @param idUser user's id
     * @return optional with user if such exists or empty optional
     * @throws ServiceException if error occurs
     */
    Optional<User> findById(long idUser) throws ServiceException;

    /**
     * @param idUser user's id
     * @return user if such exists or throws exception
     * @throws com.epam.hr.exception.EntityNotFoundException if user doesn't exist
     * @throws ServiceException                              if error occurs
     */
    User tryFindById(long idUser) throws ServiceException;

    /**
     * @param start first entity number inclusive
     * @param count entities count
     * @return list of employees or empty list if none found
     * @throws ServiceException if error occurs
     */
    List<User> findEmployees(int start, int count) throws ServiceException;

    /**
     * @param start first entity number inclusive
     * @param count entities count
     * @return list of job seekers or empty list if none found
     * @throws ServiceException if error occurs
     */
    List<User> findJobSeekers(int start, int count) throws ServiceException;

    /**
     * @return total quantity of employees
     * @throws ServiceException if error occurs
     */
    int findEmployeesQuantity() throws ServiceException;

    /**
     * @return total quantity of job seekers
     * @throws ServiceException if error occurs
     */
    int findJobSeekersQuantity() throws ServiceException;

    /**
     * @param idTarget       id of user to ban
     * @param idAdministrant id of administrator
     * @param reason         ban reason
     * @return banned user entity
     * @throws ServiceException if error occurs
     */
    Optional<User> banUser(long idTarget, long idAdministrant, String reason) throws ServiceException;

    /**
     * @param idUser id of user to unban
     * @return optional with unbanned user or empty optional if user not exists
     * @throws ServiceException if error occurs
     */
    Optional<User> unbanUser(long idUser) throws ServiceException;

    /**
     * @param idUser user's id
     * @return Ban entity or throws exception if none found
     * @throws com.epam.hr.exception.EntityNotFoundException if entity not found
     * @throws ServiceException                              if error occurs
     */
    Ban tryFindLastBan(long idUser) throws ServiceException;

    /**
     * Adds user
     *
     * @param user user to add
     * @return added user with id set
     * @throws com.epam.hr.exception.ValidationException if some of user's fields are incorrect
     * @throws ServiceException                          if error occurs
     */
    User addUser(User user) throws ServiceException;

    /**
     * Update user
     *
     * @param user user to add
     * @throws com.epam.hr.exception.ValidationException     if some of user's fields are incorrect
     * @throws com.epam.hr.exception.EntityNotFoundException on attempt to update not existing user
     * @throws ServiceException                              if error
     */
    void updateUser(User user) throws ServiceException;

    /**
     * Sets enabled status to user
     *
     * @param idUser user's id
     * @return enabled user
     * @throws ServiceException if error occurs
     */
    User enable(long idUser) throws ServiceException;

    /**
     * Sets avatar path to user
     *
     * @param idUser     user's id
     * @param avatarPath avatar path
     * @return user with avatar set
     * @throws com.epam.hr.exception.ValidationException     if user can't change avatar
     * @throws com.epam.hr.exception.EntityNotFoundException if user not found
     * @throws ServiceException                              if error occurs
     */
    User setAvatar(long idUser, String avatarPath) throws ServiceException;

    /**
     * @param idUser user's id
     * @return true if user can change avatar
     * @throws ServiceException if error occurs
     */
    boolean canChangeAvatar(long idUser) throws ServiceException;

    /**
     * @param login    user's login
     * @param password user's password
     * @return user if login and password are correct or throws exception
     * @throws com.epam.hr.exception.ValidationException if login or password incorrect
     * @throws ServiceException                          if error occurs
     */
    User authenticateUser(String login, String password) throws ServiceException;
}
