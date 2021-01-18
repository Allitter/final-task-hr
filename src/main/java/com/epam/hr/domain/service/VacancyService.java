package com.epam.hr.domain.service;

import com.epam.hr.data.dao.factory.impl.VacancyDaoFactory;
import com.epam.hr.data.dao.impl.VacancyDao;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.domain.validator.VacancyValidator;
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
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Vacancy tryFindById(long id) throws ServiceException {
        Optional<Vacancy> optionalVacancy = findById(id);
        if (!optionalVacancy.isPresent()) {
            throw new ServiceException("Vacancy doesn't exist");
        }

        return optionalVacancy.get();
    }

    public int findQuantity() throws ServiceException {
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            return dao.getRowCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void updateVacancy(long id, String name, String shortDescription, String description) throws ServiceException {
        validateVacancy(name, shortDescription, description);

        Vacancy vacancy = new Vacancy(id, name, shortDescription, description);
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            dao.save(vacancy);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addVacancy(String name, String shortDescription, String description) throws ServiceException {
        validateVacancy(name, shortDescription, description);

        Vacancy vacancy = new Vacancy(-1, name, shortDescription, description);
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            dao.save(vacancy);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void validateVacancy(String name, String shortDescription, String description) throws ValidationException {
        List<String> fails = vacancyValidator.validate(name, shortDescription, description);
        if (!fails.isEmpty()) {
            throw new ValidationException(fails);
        }
    }
}
