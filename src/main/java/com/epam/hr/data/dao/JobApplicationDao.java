package com.epam.hr.data.dao;

import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface JobApplicationDao extends Dao<JobApplication> {
    /**
     * @param idJobApplication entity's idJobApplication
     * @return optional of job application
     * @throws DaoException if sql error occurs
     */
    @Override
    Optional<JobApplication> findById(long idJobApplication) throws DaoException;

    /**
     * @param idUser user's idUser
     * @param start  first entity position inclusive
     * @param count  entities count
     * @return list of job applications by user idUser or empty list
     * @throws DaoException if sql error occurs
     */
    List<JobApplication> findByUserId(long idUser, int start, int count) throws DaoException;

    /**
     * @param idVacancy vacancy's idVacancy
     * @param start     first entity position inclusive
     * @param count     entities count
     * @return list of job applications by vacancy idVacancy or empty list
     * @throws DaoException if sql error occurs
     */
    List<JobApplication> findByVacancyId(long idVacancy, int start, int count) throws DaoException;

    /**
     * @param idUser    user's id
     * @param idVacancy vacancy's id
     * @return optional of job application
     * @throws DaoException if sql error occurs
     */
    Optional<JobApplication> findByUserAndVacancyId(long idUser, long idVacancy) throws DaoException;

    /**
     * Sets blocked status to all job applications with status not equal to Applied
     * for vacancy with provided id
     *
     * @param idVacancy vacancy's id
     * @throws DaoException if sql error occurs
     */
    void blockNonAppliedJobApplicationsByVacancyId(long idVacancy) throws DaoException;

    /**
     * @param start first entity position inclusive
     * @param count entities count
     * @return list of job applications or empty list
     * @throws DaoException if sql error occurs
     */
    @Override
    List<JobApplication> findAll(int start, int count) throws DaoException;

    /**
     * @param idJobApplication job application's id
     * @throws DaoException if sql error occurs
     */
    @Override
    void removeById(long idJobApplication) throws DaoException;

    /**
     * @return count of job applications
     * @throws DaoException if sql error occurs
     */
    @Override
    int getRowCount() throws DaoException;

    /**
     * @param idUser user's id
     * @return count of user's job applications
     * @throws DaoException if sql error occurs
     */
    int getUserJobApplicationsCount(long idUser) throws DaoException;

    /**
     * @param idVacancy vacancy's if
     * @return count of job applications for vacancy
     * @throws DaoException if sql error occurs
     */
    int getVacancyJobApplicationsCount(long idVacancy) throws DaoException;

    /**
     * @param jobApplication jop application to add or update
     * @throws DaoException if sql error occurs
     */
    @Override
    void save(JobApplication jobApplication) throws DaoException;
}
