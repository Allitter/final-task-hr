package com.epam.hr.domain.logic.service;

import com.epam.hr.data.dao.impl.ApplicationDao;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import java.util.List;
import java.util.Optional;

public class ApplicationService {
    public List<JobApplication> findUserApplications(long userId) throws ServiceException {
        try (ApplicationDao dao = new ApplicationDao()) {
            return dao.findByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void saveApplication(JobApplication jobApplication) throws ServiceException {
        try (ApplicationDao dao = new ApplicationDao()) {
            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean isAlreadyApplied(long userId, long vacancyId) throws ServiceException {
        try (ApplicationDao dao = new ApplicationDao()) {
            Optional<JobApplication> optional = dao.findByUserAndVacancyId(userId, vacancyId);
            return optional.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<JobApplication> findApplicationsByVacancy(long vacancyId) throws ServiceException {
        try (ApplicationDao dao = new ApplicationDao()) {
            return dao.findByVacancyId(vacancyId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<JobApplication> findById(long id) throws ServiceException {
        try (ApplicationDao dao = new ApplicationDao()) {
            return dao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
