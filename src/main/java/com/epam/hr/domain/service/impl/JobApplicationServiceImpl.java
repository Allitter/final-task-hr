package com.epam.hr.domain.service.impl;

import com.epam.hr.data.dao.*;
import com.epam.hr.data.dao.factory.DaoFactory;
import com.epam.hr.data.dao.impl.DaoManagerImpl;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.Vacancy;
import com.epam.hr.domain.service.JobApplicationService;
import com.epam.hr.domain.validator.JobApplicationValidator;
import com.epam.hr.domain.validator.ValidationFieldNames;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.EntityNotFoundException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import java.util.List;
import java.util.Optional;

public class JobApplicationServiceImpl implements JobApplicationService {
    private final JobApplicationValidator jobApplicationValidator;
    private final DaoFactory<JobApplicationDao> jobApplicationDaoFactory;
    private final DaoFactory<UserDao> userDaoFactory;
    private final DaoFactory<VacancyDao> vacancyDaoFactory;
    private final DaoFactory<ResumeDao> resumeDaoFactory;

    public JobApplicationServiceImpl(JobApplicationValidator jobApplicationValidator,
                                     DaoFactory<JobApplicationDao> jobApplicationDaoFactory,
                                     DaoFactory<UserDao> userDaoFactory,
                                     DaoFactory<VacancyDao> vacancyDaoFactory,
                                     DaoFactory<ResumeDao> resumeDaoFactory) {

        this.jobApplicationValidator = jobApplicationValidator;
        this.jobApplicationDaoFactory = jobApplicationDaoFactory;
        this.userDaoFactory = userDaoFactory;
        this.vacancyDaoFactory = vacancyDaoFactory;
        this.resumeDaoFactory = resumeDaoFactory;
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
    public List<JobApplication> findAll(int start, int count) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.findAll(start, count);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addJobApplication(long idUser, long idVacancy, long idResume) throws ServiceException {
        try (DaoManager daoManager = new DaoManagerImpl()) {
            JobApplicationDao jobApplicationDao = daoManager.addDao(jobApplicationDaoFactory);
            UserDao userDao = daoManager.addDao(userDaoFactory);
            VacancyDao vacancyDao = daoManager.addDao(vacancyDaoFactory);
            ResumeDao resumeDao = daoManager.addDao(resumeDaoFactory);

            Optional<Resume> optionalResume = resumeDao.findById(idResume);
            Resume resume = optionalResume.orElseThrow(EntityNotFoundException::new);

            Optional<Vacancy> optionalVacancy = vacancyDao.findById(idVacancy);
            Vacancy vacancy = optionalVacancy.orElseThrow(EntityNotFoundException::new);

            Optional<User> optionalUser = userDao.findById(idUser);
            User user = optionalUser.orElseThrow(EntityNotFoundException::new);

            JobApplication jobApplication = new JobApplication.Builder()
                    .setUser(user)
                    .setVacancy(vacancy)
                    .setState(JobApplication.State.RECENTLY_CREATED)
                    .setResumeText(resume.getText())
                    .build(true);

            jobApplicationDao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updatePreliminaryInterviewNote(long idJobApplication, String preliminaryInterviewNote) throws ServiceException {
        boolean isValid = jobApplicationValidator.validatePreliminaryInterviewNote(preliminaryInterviewNote);
        if (!isValid) {
            throw new ValidationException(ValidationFieldNames.PRELIMINARY_INTERVIEW_NOTE);
        }

        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            Optional<JobApplication> optional = dao.findById(idJobApplication);
            JobApplication jobApplication = optional.orElseThrow(EntityNotFoundException::new);

            jobApplication = new JobApplication.Builder(jobApplication)
                    .setPreliminaryInterviewNote(preliminaryInterviewNote).build(true);

            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateTechnicalInterviewNote(long idJobApplication, String technicalInterviewNote) throws ServiceException {
        boolean isValid = jobApplicationValidator.validateTechnicalInterviewNote(technicalInterviewNote);
        if (!isValid) {
            throw new ValidationException(ValidationFieldNames.TECHNICAL_INTERVIEW_NOTE);
        }

        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            Optional<JobApplication> optional = dao.findById(idJobApplication);
            JobApplication jobApplication = optional.orElseThrow(EntityNotFoundException::new);

            jobApplication = new JobApplication.Builder(jobApplication)
                    .setTechnicalInterviewNote(technicalInterviewNote).build(true);


            dao.save(jobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateState(long idJobApplication, JobApplication.State state) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            Optional<JobApplication> optional = dao.findById(idJobApplication);
            JobApplication jobApplication = optional.orElseThrow(EntityNotFoundException::new);

            jobApplication = new JobApplication.Builder(jobApplication)
                    .setState(state).build(true);

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
            throw new EntityNotFoundException("Job application doesn't exist");
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
    public Optional<JobApplication> findById(long idJobApplication) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.findById(idJobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public JobApplication tryFindById(long idJobApplication) throws ServiceException {
        Optional<JobApplication> optionalJobApplication = findById(idJobApplication);
        if (!optionalJobApplication.isPresent()) {
            throw new EntityNotFoundException("Job application doesn't exist");
        }

        return optionalJobApplication.get();
    }

    @Override
    public void remove(long idJobApplication) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            dao.removeById(idJobApplication);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countUserJobApplications(long idUser) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.getUserJobApplicationsCount(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countVacancyJobApplications(long idVacancy) throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.getVacancyJobApplicationsCount(idVacancy);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int countJobApplications() throws ServiceException {
        try (JobApplicationDao dao = jobApplicationDaoFactory.create()) {
            return dao.getRowCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
