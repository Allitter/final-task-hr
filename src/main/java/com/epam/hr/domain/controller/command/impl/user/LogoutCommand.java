package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.SessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand extends AbstractUserCommand {


    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SessionManager sessionManager = (SessionManager) session.getServletContext()
                .getAttribute(Attributes.SESSION_MANAGER);

        User user = (User) session.getAttribute(Attributes.USER);
        sessionManager.unbindSession(user.getId());
        session.invalidate();

        String path = request.getContextPath() + request.getServletPath();
        return Router.redirect(path);
    }
}
