package com.epam.hr.domain.logic.command.impl;

import com.epam.hr.domain.logic.Router;
import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.Command;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.logic.service.ApplicationService;
import com.epam.hr.domain.logic.service.ResumeService;
import com.epam.hr.domain.logic.service.VacancyService;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class VacancyApplyCommand implements Command {
    private final ApplicationService applicationService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

    public VacancyApplyCommand(ApplicationService applicationService,
                               ResumeService resumeService,
                               VacancyService vacancyService) {
        this.applicationService = applicationService;
        this.resumeService = resumeService;
        this.vacancyService = vacancyService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        // get vacancy id
        String idString = (String) request.getAttribute(Attributes.VACANCY_ID);
        long vacancyId = Long.parseLong(idString);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long userId = user.getId();

        // check if already applied if not to application page else to main page
        if (applicationService.isAlreadyApplied(userId, vacancyId)) {
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

        return Router.forward(Pages.JOB_APPLICATION);
    }
}
