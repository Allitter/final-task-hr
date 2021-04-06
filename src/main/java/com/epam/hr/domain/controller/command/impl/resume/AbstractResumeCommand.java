package com.epam.hr.domain.controller.command.impl.resume;

import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.impl.AbstractCommand;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class AbstractResumeCommand extends AbstractCommand {

    protected boolean isCurrentUserResumeOwner(HttpServletRequest request, Resume resume) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        return user.getId() == resume.getIdUser();
    }
}
