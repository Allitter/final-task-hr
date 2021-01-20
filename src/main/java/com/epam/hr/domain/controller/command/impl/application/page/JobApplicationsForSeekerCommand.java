package com.epam.hr.domain.controller.command.impl.application.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.application.AbstractJobApplicationCommand;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.Page;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class JobApplicationsForSeekerCommand extends AbstractJobApplicationCommand {
    private static final int NUMBER_OF_RECORDS_PER_PAGE = 10;

    private final JobApplicationService jobApplicationService;

    public JobApplicationsForSeekerCommand(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String pageParameter = (String) request.getAttribute(Attributes.PAGE);
        int queriedPage = pageParameter != null ? Integer.parseInt(pageParameter) : 0;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long idUser = user.getId();

        if (!User.Role.JOB_SEEKER.equals(user.getRole())) {
            return Router.forward(Pages.ACCESS_DENIED);
        }

        int totalQuantity = jobApplicationService.countUserJobApplications(idUser);
        Page page = getClosestExistingPage(queriedPage, totalQuantity);

        int from = page.getCurrentPage() * NUMBER_OF_RECORDS_PER_PAGE;
        List<JobApplication> jobApplications = jobApplicationService.findUserApplications(idUser, from, NUMBER_OF_RECORDS_PER_PAGE);
        request.setAttribute(Attributes.JOB_APPLICATIONS, jobApplications);
        request.setAttribute(Attributes.PAGE, page);

        return Router.forward(Pages.JOB_APPLICATIONS);
    }
}
