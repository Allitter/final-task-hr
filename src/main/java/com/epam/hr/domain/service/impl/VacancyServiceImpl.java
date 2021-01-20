package com.epam.hr.domain.service.impl;

import com.epam.hr.data.dao.TransactionHelper;
import com.epam.hr.data.dao.factory.impl.JobApplicationDaoFactory;
import com.epam.hr.data.dao.factory.impl.VacancyDaoFactory;
import com.epam.hr.data.dao.impl.JobApplicationDao;
import com.epam.hr.data.dao.impl.VacancyDao;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.domain.validator.VacancyValidator;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;
import java.util.List;
import java.util.Optional;

public class VacancyServiceImpl implements VacancyService {
    private final VacancyValidator vacancyValidator;
    private final VacancyDaoFactory vacancyDaoFactory;
    private final JobApplicationDaoFactory jobApplicationDaoFactory;

    public VacancyServiceImpl(VacancyValidator vacancyValidator,
                              VacancyDaoFactory vacancyDaoFactory,
                              JobApplicationDaoFactory jobApplicationDaoFactory) {
        this.vacancyValidator = vacancyValidator;
        this.vacancyDaoFactory = vacancyDaoFactory;
        this.jobApplicationDaoFactory = jobApplicationDaoFactory;
    }

    @Override
    public List<Vacancy> findVacancies(int start, int end) throws ServiceException {
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            return dao.findAll(start, end);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Vacancy> findById(long id) throws ServiceException {
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Vacancy tryFindById(long id) throws ServiceException {
        Optional<Vacancy> optionalVacancy = findById(id);
        if (!optionalVacancy.isPresent()) {
            throw new ServiceException("Vacancy doesn't exist");
        }

        return optionalVacancy.get();
    }

    @Override
    public int findQuantity() throws ServiceException {
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            return dao.getRowCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    // todo change parameters to vacancy
    @Override
    public void saveVacancy(Vacancy vacancy) throws ServiceException {
        validateVacancy(vacancy);
        vacancy = new Vacancy.Builder(vacancy).build(true);

        try (VacancyDao dao = vacancyDaoFactory.create()) {
            dao.save(vacancy);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void close(long idVacancy) throws ServiceException {
        // find vacancy
        // find attached job application
        // set blocked status to all non-applied job applications

        Vacancy vacancy = tryFindById(idVacancy);
        try (TransactionHelper transactionHelper = new TransactionHelper()) {
            VacancyDao vacancyDao = transactionHelper.addDao(vacancyDaoFactory);
            JobApplicationDao jobApplicationDao = transactionHelper.addDao(jobApplicationDaoFactory);

            transactionHelper.beginTransaction();

            jobApplicationDao.blockNonAppliedJobApplicationsByVacancyId(idVacancy);
            vacancy = vacancy.changeClosed(true);
            vacancyDao.save(vacancy);

            transactionHelper.commit();

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void validateVacancy(Vacancy vacancy) throws ValidationException {
        List<String> fails = vacancyValidator.getValidationFails(vacancy);
        if (!fails.isEmpty()) {
            throw new ValidationException(fails);
        }
    }
}
