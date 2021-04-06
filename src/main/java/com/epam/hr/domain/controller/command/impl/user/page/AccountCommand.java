package com.epam.hr.domain.controller.command.impl.user.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.user.AbstractUserCommand;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AccountCommand extends AbstractUserCommand {
    private final ResumeService resumeService;
    private final UserService userService;

    public AccountCommand(ResumeService resumeService, UserService userService) {
        this.resumeService = resumeService;
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long idUser = user.getId();

        List<Resume> resumes = resumeService.findUserResumes(idUser);
        request.setAttribute(Attributes.RESUMES, resumes);

        boolean canChangeAvatar = userService.canChangeAvatar(idUser);
        request.setAttribute(Attributes.CAN_CHANGE_AVATAR, canChangeAvatar);

        return Router.forward(Pages.ACCOUNT);
    }
}
