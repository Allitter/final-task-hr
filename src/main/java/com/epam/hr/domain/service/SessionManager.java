package com.epam.hr.domain.service;

import com.epam.hr.domain.model.User;

import javax.servlet.http.HttpSession;

public interface SessionManager {
    void bindSession(long idUser, HttpSession session);

    void unbindSession(long idUser);

    HttpSession getSession(long idUser);

    void renewUserIfSessionExists(User user);
}
