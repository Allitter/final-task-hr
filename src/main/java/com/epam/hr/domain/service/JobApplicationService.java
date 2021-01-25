package com.epam.hr.domain.service;

import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Service to work with JobApplication entities
 */
public interface JobApplicationService {
    /**
     * @param idJobApplication job application's id
     * @return optional with job application if such exists or empty optional
     * @throws ServiceException if error occurs
     */
    Optional<JobApplication> findById(long idJobApplication) throws ServiceException;

    /**
     * @param idJobApplication job application's id
     * @return job application if such exists or throws exception
     * @throws com.epam.hr.exception.EntityNotFoundException if entity not found
     * @throws ServiceException                              if error occurs
     */
    JobApplication tryFindById(long idJobApplication) throws ServiceException;

    /**
     * @param idUser    user's id
     * @param idVacancy vacancy's id
     * @return optional with job application if such exists or empty optional
     * @throws ServiceException if error occurs
     */
    Optional<JobApplication> findByUserAndVacancyId(long idUser, long idVacancy) throws ServiceException;

    /**
     * @param idUser    user's id
     * @param idVacancy vacancy's id
     * @return ob application if such exists or throws exception
     * @throws com.epam.hr.exception.EntityNotFoundException if entity not found
     * @throws ServiceException                              if error occurs
     */
    JobApplication tryFindByUserAndVacancyId(long idUser, long idVacancy) throws ServiceException;

    /**
     * @param start first entity number inclusive
     * @param count entities count
     * @return list of job applications or empty list if none found
     * @throws ServiceException if error occurs
     */
    List<JobApplication> findAll(int start, int count) throws ServiceException;

    /**
     * @param idUser user's id
     * @param start  first entity number inclusive
     * @param count  entities count
     * @return list of job applications or empty list if none found
     * @throws ServiceException if error occurs
     */
    List<JobApplication> findUserApplications(long idUser, int start, int count) throws ServiceException;

    /**
     * @param idVacancy vacancy's id
     * @param start     first entity number inclusive
     * @param count     entities count
     * @return list of job applications or empty list if none found
     * @throws ServiceException if error occurs
     */
    List<JobApplication> findApplicationsByVacancy(long idVacancy, int start, int count) throws ServiceException;

    /**
     * Creates job application and stores it
     *
     * @param idUser    user's id
     * @param idVacancy vacancy's id
     * @param idResume  resume's id
     * @throws com.epam.hr.exception.EntityNotFoundException if user, vacancy or resume doesn't exist
     * @throws ServiceException                              if error occurs
     */
    void addJobApplication(long idUser, long idVacancy, long idResume) throws ServiceException;

    /**
     * @param idJobApplication         job application's id
     * @param preliminaryInterviewNote preliminary interview note
     * @throws com.epam.hr.exception.EntityNotFoundException if job application doesn't exist
     * @throws com.epam.hr.exception.ValidationException     if preliminary interview note is not valid
     * @throws ServiceException                              if error occurs
     */
    void updatePreliminaryInterviewNote(long idJobApplication, String preliminaryInterviewNote) throws ServiceException;

    /**
     * @param idJobApplication       job application's id
     * @param technicalInterviewNote technical interview note
     * @throws com.epam.hr.exception.EntityNotFoundException if job application doesn't exist
     * @throws com.epam.hr.exception.ValidationException     if technical interview note is not valid
     * @throws ServiceException                              if error occurs
     */
    void updateTechnicalInterviewNote(long idJobApplication, String technicalInterviewNote) throws ServiceException;

    /**
     * @param idJobApplication job application's id
     * @param state            job application's state
     * @throws com.epam.hr.exception.EntityNotFoundException if job application doesn't exist
     * @throws ServiceException                              if error occurs
     */
    void updateState(long idJobApplication, JobApplication.State state) throws ServiceException;

    /**
     * @param idUser    user's id
     * @param idVacancy vacancy's id
     * @return true if user already applied
     * @throws ServiceException if error occurs
     */
    boolean isAlreadyApplied(long idUser, long idVacancy) throws ServiceException;

    /**
     * removes the job application
     *
     * @param idJobApplication job application's id
     * @throws ServiceException if error occurs
     */
    void remove(long idJobApplication) throws ServiceException;

    /**
     * @param idUser user's id
     * @return user's job applications count
     * @throws ServiceException if error occurs
     */
    int countUserJobApplications(long idUser) throws ServiceException;

    /**
     * @param idVacancy vacancy's id
     * @return job applications count for vacancy
     * @throws ServiceException if error occurs
     */
    int countVacancyJobApplications(long idVacancy) throws ServiceException;

    /**
     * @return job applications count
     * @throws ServiceException if error occurs
     */
    int countJobApplications() throws ServiceException;
}
