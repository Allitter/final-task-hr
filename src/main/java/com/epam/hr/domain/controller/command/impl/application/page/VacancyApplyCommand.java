package com.epam.hr.domain.controller.command.impl.application.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.application.AbstractJobApplicationCommand;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class VacancyApplyCommand extends AbstractJobApplicationCommand {
    private final JobApplicationService jobApplicationService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

    public VacancyApplyCommand(JobApplicationService jobApplicationService,
                               ResumeService resumeService,
                               VacancyService vacancyService) {
        this.jobApplicationService = jobApplicationService;
        this.resumeService = resumeService;
        this.vacancyService = vacancyService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String idString = (String) request.getAttribute(Attributes.VACANCY_ID);
        long idVacancy = Long.parseLong(idString);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long idUser = user.getId();

        Vacancy vacancy = vacancyService.tryFindById(idVacancy);
        if (vacancy.isClosed() || jobApplicationService.isAlreadyApplied(idUser, idVacancy)) {
            String path = request.getHeader(Attributes.REFERER);
            return Router.redirect(path);
        }

        request.setAttribute(Attributes.VACANCY, vacancy);

        List<Resume> resumes = resumeService.findUserResumes(idUser);
        request.setAttribute(Attributes.RESUMES, resumes);

        return Router.forward(Pages.VACANCY_APPLY);
    }
}
