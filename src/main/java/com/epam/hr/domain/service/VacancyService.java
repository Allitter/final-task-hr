package com.epam.hr.domain.service;

import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Provides ability to works with Vacancy entities
 */
public interface VacancyService {
    /**
     * @param start first entity number inclusive
     * @param count entities count
     * @return list of vacancies or empty list
     * @throws ServiceException if error occurs
     */
    List<Vacancy> findVacancies(int start, int count) throws ServiceException;

    /**
     * @param idVacancy vacancy's id
     * @return optional with vacancy or empty optional if none found
     * @throws ServiceException if error occurs
     */
    Optional<Vacancy> findById(long idVacancy) throws ServiceException;

    /**
     * @param idVacancy vacancy's id
     * @return vacancy or throws exception
     * @throws com.epam.hr.exception.EntityNotFoundException if vacancy not found
     * @throws ServiceException                              if error occurs
     */
    Vacancy tryFindById(long idVacancy) throws ServiceException;

    /**
     * @return vacancies count
     * @throws ServiceException if error occurs
     */
    int getVacanciesCount() throws ServiceException;

    /**
     * Adds or updates vacancy
     *
     * @param vacancy Vacancy to save
     * @throws com.epam.hr.exception.ValidationException if vacancy contains invalid fields
     * @throws ServiceException                          if error occurs
     */
    void saveVacancy(Vacancy vacancy) throws ServiceException;

    /**
     * Closes vacancy
     *
     * @param idVacancy vacancy's id
     * @throws ServiceException if error occurs
     */
    void closeVacancy(long idVacancy) throws ServiceException;

    /**
     * Removes vacancy if such exists
     *
     * @param idVacancy vacancy's id
     * @throws ServiceException if error occurs
     */
    void remove(long idVacancy) throws ServiceException;
}
