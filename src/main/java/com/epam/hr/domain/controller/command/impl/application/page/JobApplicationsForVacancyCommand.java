package com.epam.hr.domain.controller.command.impl.application.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.application.AbstractJobApplicationCommand;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.Page;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class JobApplicationsForVacancyCommand extends AbstractJobApplicationCommand {
    private final JobApplicationService jobApplicationService;

    public JobApplicationsForVacancyCommand(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String pageParameter = (String) request.getAttribute(Attributes.PAGE);
        long idVacancy = Long.parseLong((String) request.getAttribute(Attributes.VACANCY_ID));
        int queriedPage = pageParameter != null ? Integer.parseInt(pageParameter) : 0;

        int totalQuantity = jobApplicationService.countVacancyJobApplications(idVacancy);
        Page page = getClosestExistingPage(queriedPage, totalQuantity);

        int from = page.getFirstRecordNumber();
        List<JobApplication> jobApplications = jobApplicationService.findApplicationsByVacancy(idVacancy, from, DEFAULT_NUMBER_OF_RECORDS_PER_PAGE);
        request.setAttribute(Attributes.JOB_APPLICATIONS, jobApplications);
        request.setAttribute(Attributes.PAGE, page);

        return Router.forward(Pages.JOB_APPLICATIONS_VACANCY);
    }
}
