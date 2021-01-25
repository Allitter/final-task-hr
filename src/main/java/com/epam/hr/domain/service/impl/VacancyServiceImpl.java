package com.epam.hr.domain.service.impl;

import com.epam.hr.data.dao.DaoManager;
import com.epam.hr.data.dao.JobApplicationDao;
import com.epam.hr.data.dao.VacancyDao;
import com.epam.hr.data.dao.factory.impl.JobApplicationDaoFactory;
import com.epam.hr.data.dao.factory.impl.VacancyDaoFactory;
import com.epam.hr.data.dao.impl.DaoManagerImpl;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.domain.service.VacancyService;
import com.epam.hr.domain.validator.VacancyValidator;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.EntityNotFoundException;
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
    public List<Vacancy> findVacancies(int start, int count) throws ServiceException {
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            return dao.findAll(start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Vacancy> findById(long idVacancy) throws ServiceException {
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            return dao.findById(idVacancy);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Vacancy tryFindById(long idVacancy) throws ServiceException {
        Optional<Vacancy> optionalVacancy = findById(idVacancy);
        if (!optionalVacancy.isPresent()) {
            throw new ServiceException("Vacancy doesn't exist");
        }

        return optionalVacancy.get();
    }

    @Override
    public int getVacanciesCount() throws ServiceException {
        try (VacancyDao dao = vacancyDaoFactory.create()) {
            return dao.getRowCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

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

    public void closeVacancy(long idVacancy) throws ServiceException {
        try (DaoManager daoManager = new DaoManagerImpl()) {
            VacancyDao vacancyDao = daoManager.addDao(vacancyDaoFactory);
            JobApplicationDao jobApplicationDao = daoManager.addDao(jobApplicationDaoFactory);

            daoManager.beginTransaction();

            jobApplicationDao.blockNonAppliedJobApplicationsByVacancyId(idVacancy);
            Optional<Vacancy> optionalVacancy = vacancyDao.findById(idVacancy);
            Vacancy vacancy = optionalVacancy.orElseThrow(EntityNotFoundException::new);
            vacancy = vacancy.changeClosed(true);
            vacancyDao.save(vacancy);

            daoManager.commit();

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void remove(long idVacancy) throws ServiceException {
        try (VacancyDao vacancyDao = vacancyDaoFactory.create()) {
            vacancyDao.removeById(idVacancy);
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
