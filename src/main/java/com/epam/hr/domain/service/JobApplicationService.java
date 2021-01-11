package com.epam.hr.domain.service;

import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.JobApplicationDao;
import com.epam.hr.domain.validator.JobApplicationValidator;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.JobApplicationState;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import java.util.List;
import java.util.Optional;

public class JobApplicationService {
    private final JobApplicationValidator jobApplicationValidator;
    private final DaoFactory<JobApplicationDao> jobApplicationDaoFactory;

    public JobApplicationService(JobApplicationValidator jobApplicationValidator,
                                 DaoFactory<JobApplicationDao> jobApplicationDaoFactory) {
        this.jobApplicationValidator = jobApplicationValidator;
        this.jobApplicationDaoFactory = jobApplicationDaoFactory;
    }

    public List<JobApplication> findUserApplications(long userId, int start, int count) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.findByUserId(userId, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void save(JobApplication jobApplication) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            List<String> fails = jobApplicationValidator.validate(jobApplication);
            if (!fails.isEmpty()) {
                throw new ValidationException(fails);
            }

            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addJobApplication(long idUser, long idVacancy, String resumeText) throws ServiceException {
        JobApplication jobApplication = new JobApplication.Builder()
                .setIdUser(idUser)
                .setIdVacancy(idVacancy)
                .setState(JobApplicationState.RECENTLY_CREATED)
                .setResumeText(resumeText)
                .build();

        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void updatePreliminaryInterviewNote(long idJobApplication, String preliminaryInterviewNote) throws ServiceException {
        Optional<JobApplication> optionalJobApplication = findById(idJobApplication);
        if (!optionalJobApplication.isPresent()) {
            throw new ServiceException(String.format("JobApplication with id %d doesn't exist", idJobApplication));
        }
        JobApplication jobApplication = optionalJobApplication.get();

        List<String> fails = jobApplicationValidator.validatePreliminaryInterviewNote(preliminaryInterviewNote);
        if (!fails.isEmpty()) {
            throw new ValidationException(fails);
        }

        jobApplication = new JobApplication.Builder(jobApplication)
                .setPreliminaryInterviewNote(preliminaryInterviewNote)
                .build();

        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void updateTechnicalInterviewNote(long idJobApplication, String technicalInterviewNote) throws ServiceException {
        Optional<JobApplication> optionalJobApplication = findById(idJobApplication);
        if (!optionalJobApplication.isPresent()) {
            throw new ServiceException(String.format("JobApplication with id %d doesn't exist", idJobApplication));
        }
        JobApplication jobApplication = optionalJobApplication.get();

        List<String> fails = jobApplicationValidator.validateTechnicalInterviewNote(technicalInterviewNote);
        if (!fails.isEmpty()) {
            throw new ValidationException(fails);
        }

        jobApplication = new JobApplication.Builder(jobApplication)
                .setTechnicalInterviewNote(technicalInterviewNote)
                .build();

        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void updateState(JobApplication jobApplication, JobApplicationState state) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            jobApplication = new JobApplication.Builder(jobApplication)
                    .setState(state)
                    .build();

            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    public boolean isAlreadyApplied(long userId, long vacancyId) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            Optional<JobApplication> optional = dao.findByUserAndVacancyId(userId, vacancyId);
            return optional.isPresent();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<JobApplication> findApplicationsByVacancy(long vacancyId, int start, int count) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.findByVacancyId(vacancyId, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<JobApplication> findById(long id) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int countUserJobApplications(long idUser) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.findUserJobApplicationsQuantity(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public int countVacancyJobApplications(long idVacancy) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.findVacancyJobApplicationsQuantity(idVacancy);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
