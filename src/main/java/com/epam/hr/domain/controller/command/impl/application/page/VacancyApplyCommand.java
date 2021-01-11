package com.epam.hr.domain.controller.command.impl.application.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class VacancyApplyCommand implements Command {
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
        long vacancyId = Long.parseLong(idString);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long userId = user.getId();

        if (jobApplicationService.isAlreadyApplied(userId, vacancyId)) {
            String path = request.getContextPath() + request.getServletPath();
            return Router.redirect(path);
        }

        Optional<Vacancy> optionalVacancy = vacancyService.findById(vacancyId);
        if (!optionalVacancy.isPresent()) {
            return Router.forward(Pages.PAGE_NOT_FOUND);
        }

        Vacancy vacancy = optionalVacancy.get();
        request.setAttribute(Attributes.VACANCY, vacancy);

        List<Resume> resumes = resumeService.findUserResumes(userId);
        request.setAttribute(Attributes.RESUMES, resumes);

        return Router.forward(Pages.VACANCY_APPLY);
    }
}
