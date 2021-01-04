package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.service.ApplicationService;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.JobApplicationState;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class VacancyApplyAcceptCommand implements Command {
    public static final int DEFAULT_ID = -1;
    private final ApplicationService applicationService;

    public VacancyApplyAcceptCommand(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        // extract id vacancy, user id and resume id
        long idVacancy = Long.parseLong((String)request.getAttribute(Attributes.VACANCY_ID));
        long idResume = Long.parseLong((String)request.getAttribute(Attributes.RESUME_ID));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long idUser = user.getId();

        JobApplication jobApplication = new JobApplication(DEFAULT_ID, idUser, idVacancy, idResume, new Date(), JobApplicationState.RECENTLY_CREATED, "", "");
        applicationService.saveApplication(jobApplication);

        String path = request.getContextPath() + request.getServletPath();
        return Router.redirect(path);
    }
}
