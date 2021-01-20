package com.epam.hr.domain.service.impl;

import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.JobApplicationDao;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.domain.validator.JobApplicationValidator;
import com.epam.hr.domain.validator.ValidationFieldNames;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import java.util.List;
import java.util.Optional;

public class JobApplicationServiceImpl implements JobApplicationService {
    private final JobApplicationValidator jobApplicationValidator;
    private final DaoFactory<JobApplicationDao> jobApplicationDaoFactory;

    public JobApplicationServiceImpl(JobApplicationValidator jobApplicationValidator,
                                     DaoFactory<JobApplicationDao> jobApplicationDaoFactory) {
        this.jobApplicationValidator = jobApplicationValidator;
        this.jobApplicationDaoFactory = jobApplicationDaoFactory;
    }

    @Override
    public List<JobApplication> findUserApplications(long idUser, int start, int count) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.findByUserId(idUser, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(JobApplication jobApplication) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            List<String> fails = jobApplicationValidator.getValidationFails(jobApplication);
            if (!fails.isEmpty()) {
                throw new ValidationException(fails);
            }

            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addJobApplication(User user, Vacancy vacancy, String resumeText) throws ServiceException {
        JobApplication jobApplication = new JobApplication.Builder()
                .setUser(user)
                .setVacancy(vacancy)
                .setState(JobApplication.State.RECENTLY_CREATED)
                .setResumeText(resumeText)
                .build();

        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updatePreliminaryInterviewNote(long idJobApplication, String preliminaryInterviewNote) throws ServiceException {
        Optional<JobApplication> optionalJobApplication = findById(idJobApplication);
        if (!optionalJobApplication.isPresent()) {
            throw new ServiceException(String.format("JobApplication with id %d doesn't exist", idJobApplication));
        }
        JobApplication jobApplication = optionalJobApplication.get();

        boolean isValid = jobApplicationValidator.validatePreliminaryInterviewNote(preliminaryInterviewNote);
        if (!isValid) {
            throw new ValidationException(ValidationFieldNames.PRELIMINARY_INTERVIEW_NOTE);
        }

        jobApplication = new JobApplication.Builder(jobApplication)
                .setPreliminaryInterviewNote(preliminaryInterviewNote).build();

        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateTechnicalInterviewNote(long idJobApplication, String technicalInterviewNote) throws ServiceException {
        JobApplication jobApplication = tryFindById(idJobApplication);
        boolean isValid = jobApplicationValidator.validateTechnicalInterviewNote(technicalInterviewNote);
        if (!isValid) {
            throw new ValidationException(ValidationFieldNames.TECHNICAL_INTERVIEW_NOTE);
        }

        jobApplication = new JobApplication.Builder(jobApplication)
                .setTechnicalInterviewNote(technicalInterviewNote).build();

        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateState(JobApplication jobApplication, JobApplication.State state) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            jobApplication = new JobApplication.Builder(jobApplication)
                    .setState(state).build();

            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean isAlreadyApplied(long idUser, long idVacancy) throws ServiceException {
        Optional<JobApplication> optional = findByUserAndVacancyId(idUser, idVacancy);
        return optional.isPresent();
    }

    @Override
    public Optional<JobApplication> findByUserAndVacancyId(long idUser, long idVacancy) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.findByUserAndVacancyId(idUser, idVacancy);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public JobApplication tryFindByUserAndVacancyId(long idUser, long idVacancy) throws ServiceException {
        Optional<JobApplication> optional = findByUserAndVacancyId(idUser, idVacancy);
        if (!optional.isPresent()) {
            throw new ServiceException("Job application doesn't exist");
        }

        return optional.get();
    }


    @Override
    public List<JobApplication> findApplicationsByVacancy(long idVacancy, int start, int count) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.findByVacancyId(idVacancy, start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<JobApplication> findById(long id) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public JobApplication tryFindById(long id) throws ServiceException {
        Optional<JobApplication> optionalJobApplication = findById(id);
        if (!optionalJobApplication.isPresent()) {
            throw new ServiceException("Job application doesn't exist");
        }

        return optionalJobApplication.get();
    }

    @Override
    public int countUserJobApplications(long idUser) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.findUserJobApplicationsQuantity(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countVacancyJobApplications(long idVacancy) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.findVacancyJobApplicationsQuantity(idVacancy);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
