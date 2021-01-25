package com.epam.hr.data.dao;

import com.epam.hr.domain.model.User;
import com.epam.hr.exception.DaoException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao<User> {
    /**
     * @param login    user's login
     * @param password user's password
     * @return optional of user
     * @throws DaoException if sql error occurs
     */
    Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException;

    /**
     * @param idUser user's id
     * @throws DaoException if sql error occurs
     */
    void banUser(long idUser) throws DaoException;

    /**
     * @param idUser user's id
     * @throws DaoException if sql error occurs
     */
    void unbanUser(long idUser) throws DaoException;

    /**
     * @param role  user's role
     * @param start first entity position inclusive
     * @param count entities count
     * @return list of users with particular role or empty list
     * @throws DaoException if sql error occurs
     */
    List<User> findAll(User.Role role, int start, int count) throws DaoException;

    /**
     * @param idUser user's id
     * @return optional of user
     * @throws DaoException if sql error occurs
     */
    @Override
    Optional<User> findById(long idUser) throws DaoException;

    /**
     * @param start first entity position inclusive
     * @param count entities count
     * @return list of users or empty list
     * @throws DaoException if sql error occurs
     */
    @Override
    List<User> findAll(int start, int count) throws DaoException;

    /**
     * @param login user's login
     * @return optional of user
     * @throws DaoException if sql error occurs
     */
    Optional<User> findByLogin(String login) throws DaoException;

    /**
     * @param idUser user's id
     * @throws DaoException if sql error occurs
     */
    @Override
    void removeById(long idUser) throws DaoException;

    /**
     * @return count of users
     * @throws DaoException if sql error occurs
     */
    @Override
    int getRowCount() throws DaoException;

    /**
     * Sets avatar path attribute in database and update avatar last change date
     *
     * @param idUser user's id
     * @param path   avatar path
     * @throws DaoException if sql error occurs
     */
    void setAvatarPath(long idUser, String path) throws DaoException;

    /**
     * @param role user's role
     * @return count of users with particular role
     * @throws DaoException if sql error occurs
     */
    int getUsersCountByRole(User.Role role) throws DaoException;

    /**
     * @param idUser user's id
     * @return avatar last change date
     * @throws DaoException if sql error occurs
     */
    Date getAvatarLastChangeDate(long idUser) throws DaoException;

    /**
     * @param user user to add or update
     * @throws DaoException if sql error occurs
     */
    @Override
    void save(User user) throws DaoException;
}
