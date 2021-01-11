package com.epam.hr.domain.controller.command.impl.user.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AccountCommand implements Command {
    private final ResumeService service;

    public AccountCommand(ResumeService service) {
        this.service = service;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long userId = user.getId();

        List<Resume> resumes = service.findUserResumes(userId);
        request.setAttribute(Attributes.RESUMES, resumes);

        return Router.forward(Pages.ACCOUNT);
    }
}