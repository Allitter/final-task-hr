package com.epam.hr.domain.logic.service;

import com.epam.hr.data.dao.VacanciesDao;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.domain.model.Vacancy;
import java.util.List;
import java.util.Optional;

public class VacancyService {

    public List<Vacancy> findVacancies(int start, int end) throws ServiceException {
        try (VacanciesDao dao = new VacanciesDao()) {
            return dao.findAll(start, end);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<Vacancy> findById(long id) throws ServiceException {
        try (VacanciesDao dao = new VacanciesDao()) {
            return dao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int findQuantity() throws ServiceException {
        try (VacanciesDao dao = new VacanciesDao()) {
            return dao.findQuantity();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void save(Vacancy vacancy) throws ServiceException {
        try (VacanciesDao dao = new VacanciesDao()) {
            dao.save(vacancy);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
