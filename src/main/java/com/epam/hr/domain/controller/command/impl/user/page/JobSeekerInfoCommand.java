package com.epam.hr.domain.controller.command.impl.user.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.user.AbstractUserCommand;
import com.epam.hr.domain.model.Ban;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class JobSeekerInfoCommand extends AbstractUserCommand {
    private final UserService userService;
    private final ResumeService resumeService;

    public JobSeekerInfoCommand(UserService userService, ResumeService resumeService) {
        this.userService = userService;
        this.resumeService = resumeService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        long idUser = Long.parseLong((String) request.getAttribute(Attributes.USER_ID));

        User user = userService.tryFindById(idUser);
        if (user.isBanned()) {
            Ban ban = userService.tryFindLastBan(idUser);
            request.setAttribute(Attributes.BAN, ban);
        }
        request.setAttribute(Attributes.JOB_SEEKER, user);

        List<Resume> resumes = resumeService.findUserResumes(idUser);
        request.setAttribute(Attributes.RESUMES, resumes);

        return Router.forward(Pages.JOB_SEEKER_INFO);
    }
}
