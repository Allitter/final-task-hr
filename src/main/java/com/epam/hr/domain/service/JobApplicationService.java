package com.epam.hr.domain.service;

import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface JobApplicationService {
    List<JobApplication> findUserApplications(long idUser, int start, int count) throws ServiceException;

    void save(JobApplication jobApplication) throws ServiceException;

    void addJobApplication(User user, Vacancy vacancy, String resumeText) throws ServiceException;

    void updatePreliminaryInterviewNote(long idJobApplication, String preliminaryInterviewNote) throws ServiceException;

    void updateTechnicalInterviewNote(long idJobApplication, String technicalInterviewNote) throws ServiceException;

    void updateState(JobApplication jobApplication, JobApplication.State state) throws ServiceException;

    boolean isAlreadyApplied(long idUser, long idVacancy) throws ServiceException;

    List<JobApplication> findApplicationsByVacancy(long idVacancy, int start, int count) throws ServiceException;

    Optional<JobApplication> findById(long id) throws ServiceException;

    JobApplication tryFindById(long id) throws ServiceException;

    int countUserJobApplications(long idUser) throws ServiceException;

    int countVacancyJobApplications(long idVacancy) throws ServiceException;

    Optional<JobApplication> findByUserAndVacancyId(long idUser, long idVacancy) throws ServiceException;

    JobApplication tryFindByUserAndVacancyId(long idUser, long idVacancy) throws ServiceException;
}
