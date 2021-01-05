package com.epam.hr.domain.logic.command.impl.resume;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.ResumeService;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ResumeEditAcceptCommand implements Command {
    private static final String COMMAND_ACCOUNT = "?command=account";
    private final ResumeService service;

    public ResumeEditAcceptCommand(ResumeService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String name = (String) request.getAttribute(Attributes.RESUME_NAME);
        String resumeText = (String) request.getAttribute(Attributes.LONG_DESCRIPTION);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long id = Long.parseLong((String) request.getAttribute(Attributes.RESUME_ID));
        long idUser = user.getId();

        Resume resume = new Resume(id, idUser, name, resumeText);

        // Todo validate
        service.saveResume(resume);

        String path = request.getContextPath() + request.getServletPath() + COMMAND_ACCOUNT;
        return Router.redirect(path);
    }

}
