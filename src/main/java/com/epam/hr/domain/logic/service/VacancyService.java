package com.epam.hr.domain.logic.service;

import com.epam.hr.data.dao.factory.impl.VacancyDaoFactory;
import com.epam.hr.data.dao.impl.VacancyDao;
import com.epam.hr.domain.logic.validator.VacancyValidator;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import java.util.List;
import java.util.Optional;

public class VacancyService {
    private final VacancyValidator vacancyValidator;
    private final VacancyDaoFactory vacancyDaoFactory;

    public VacancyService(VacancyValidator vacancyValidator, VacancyDaoFactory vacancyDaoFactory) {
        this.vacancyValidator = vacancyValidator;
        this.vacancyDaoFactory = vacancyDaoFactory;
    }

    public List<Vacancy> findVacancies(int start, int end) throws ServiceException {
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            return dao.findAll(start, end);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<Vacancy> findById(long id) throws ServiceException {
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            return dao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int findQuantity() throws ServiceException {
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            return dao.findQuantity();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void save(Vacancy vacancy) throws ServiceException {
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            List<String> fails = vacancyValidator.validate(vacancy);
            if (!fails.isEmpty()) {
                throw new ValidationException(fails);
            }

            dao.save(vacancy);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
