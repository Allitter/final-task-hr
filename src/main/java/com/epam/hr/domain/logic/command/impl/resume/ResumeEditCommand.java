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
import java.util.Optional;

public class ResumeEditCommand implements Command {
    private final ResumeService resumeService;

    public ResumeEditCommand(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long resumeId = Long.parseLong((String) request.getAttribute(Attributes.RESUME_ID));
        Optional<Resume> optional = resumeService.findById(resumeId);

        if (!optional.isPresent()) {
            return Router.forward(Pages.PAGE_NOT_FOUND);
        }

        Resume resume = optional.get();
        long resumeUserId = resume.getIdUser();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);

        if (resumeUserId != user.getId()) {
            return Router.forward(Pages.ACCESS_DENIED);
        }

        request.setAttribute(Attributes.RESUME, resume);

        return Router.forward(Pages.RESUME_EDIT);
    }
}
