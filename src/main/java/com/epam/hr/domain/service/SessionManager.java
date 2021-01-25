package com.epam.hr.domain.service;

import com.epam.hr.domain.model.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Provides ability to store and work with logged in user's sessions
 */
public interface SessionManager {
    /**
     * Binds user's session to manger, should be called on user's login
     *
     * @param idUser  user's id
     * @param session user's session
     */
    void bindSession(long idUser, HttpSession session);

    /**
     * Unbinds user's session from manger, should be called on user's logout
     *
     * @param idUser user's id
     */
    void unbindSession(long idUser);

    /**
     * @param idUser user's id
     * @return optional with session or empty optional if user not logged in
     */
    Optional<HttpSession> getSession(long idUser);

    /**
     * This method will look up if this user's session was bonded to manager
     * if so the 'users' attribute in session will be renewed
     *
     * @param user renewed user
     */
    void renewUserIfSessionExists(User user);
}
