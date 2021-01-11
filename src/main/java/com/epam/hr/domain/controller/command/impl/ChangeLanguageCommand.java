package com.epam.hr.domain.controller.command.impl;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        String newLanguage = (String)request.getAttribute(Attributes.LANGUAGE);

        HttpSession session = request.getSession();
        session.removeAttribute(Attributes.LANGUAGE);
        session.setAttribute(Attributes.LANGUAGE, newLanguage);

        String path = request.getHeader(Attributes.REFERER);
        return Router.redirect(path);
    }

}
