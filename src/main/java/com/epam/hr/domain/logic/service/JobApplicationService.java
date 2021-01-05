package com.epam.hr.domain.logic.service;

import com.epam.hr.data.dao.impl.JobApplicationDao;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import java.util.List;
import java.util.Optional;

public class JobApplicationService {
    public List<JobApplication> findUserApplications(long userId, int start, int count) throws ServiceException {
        try (JobApplicationDao dao = new JobApplicationDao()) {
            return dao.findByUserId(userId, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void save(JobApplication jobApplication) throws ServiceException {
        try (JobApplicationDao dao = new JobApplicationDao()) {
            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean isAlreadyApplied(long userId, long vacancyId) throws ServiceException {
        try (JobApplicationDao dao = new JobApplicationDao()) {
            Optional<JobApplication> optional = dao.findByUserAndVacancyId(userId, vacancyId);
            return optional.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<JobApplication> findApplicationsByVacancy(long vacancyId, int start, int count) throws ServiceException {
        try (JobApplicationDao dao = new JobApplicationDao()) {
            return dao.findByVacancyId(vacancyId, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<JobApplication> findById(long id) throws ServiceException {
        try (JobApplicationDao dao = new JobApplicationDao()) {
            return dao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int countUserJobApplications(long idUser) throws ServiceException {
        try (JobApplicationDao dao = new JobApplicationDao()) {
            return dao.findUserJobApplicationsQuantity(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int countVacancyJobApplications(long idVacancy) throws ServiceException {
        try (JobApplicationDao dao = new JobApplicationDao()) {
            return dao.findVacancyJobApplicationsQuantity(idVacancy);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
