package com.epam.hr.domain.service;

import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.model.User;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private final Map<Long, HttpSession> sessions = new ConcurrentHashMap<>();

    public void bindSession(long idUser, HttpSession session) {
        if (sessions.containsKey(idUser)) {
            HttpSession oldSession = sessions.get(idUser);
            if (oldSession != session) {
                oldSession.invalidate();
            }
        }

        sessions.put(idUser, session);
    }

    public void unbindSession(long idUser) {
        sessions.remove(idUser);
    }

    public HttpSession getSession(long idUser) {
        return sessions.get(idUser);
    }

    public void renewUserIfSessionExists(User user) {
        long id = user.getId();
        if (sessions.containsKey(id)) {
            HttpSession session = sessions.get(id);
            session.setAttribute(Attributes.USER, user);
        }
    }
}
