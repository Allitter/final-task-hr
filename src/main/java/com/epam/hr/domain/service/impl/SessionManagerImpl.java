package com.epam.hr.domain.service.impl;

import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.SessionManager;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManagerImpl implements SessionManager {
    private final Map<Long, HttpSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void bindSession(long idUser, HttpSession session) {
        if (sessions.containsKey(idUser)) {
            HttpSession oldSession = sessions.get(idUser);
            if (oldSession != session) {
                oldSession.invalidate();
            }
        }

        sessions.put(idUser, session);
    }

    @Override
    public void unbindSession(long idUser) {
        sessions.remove(idUser);
    }

    @Override
    public Optional<HttpSession> getSession(long idUser) {
        return Optional.ofNullable(sessions.get(idUser));
    }

    @Override
    public void renewUserIfSessionExists(User user) {
        long id = user.getId();
        if (sessions.containsKey(id)) {
            HttpSession session = sessions.get(id);
            session.setAttribute(Attributes.USER, user);
        }
    }
}
