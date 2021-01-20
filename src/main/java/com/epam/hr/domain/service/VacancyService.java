package com.epam.hr.domain.service;

import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface VacancyService {
    List<Vacancy> findVacancies(int start, int end) throws ServiceException;

    Optional<Vacancy> findById(long id) throws ServiceException;

    Vacancy tryFindById(long id) throws ServiceException;

    int findQuantity() throws ServiceException;

    void saveVacancy(Vacancy vacancy) throws ServiceException;

    void close(long idVacancy) throws ServiceException;
}
