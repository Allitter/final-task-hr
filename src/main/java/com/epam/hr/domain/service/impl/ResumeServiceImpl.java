package com.epam.hr.domain.service.impl;

import com.epam.hr.data.dao.ResumeDao;
import com.epam.hr.data.dao.factory.impl.ResumeDaoFactory;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.validator.ResumeValidator;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.EntityNotFoundException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ResumeServiceImpl implements ResumeService {
    private static final String RESUME_LIMIT_EXCEEDED_MESSAGE = "noMoreResumes";
    private static final int DEFAULT_ID = -1;
    private static final int MAX_RESUMES_COUNT_PER_USER = 10;
    private final ResumeValidator resumeValidator;
    private final ResumeDaoFactory resumeDaoFactory;

    public ResumeServiceImpl(ResumeValidator resumeValidator, ResumeDaoFactory resumeDaoFactory) {
        this.resumeValidator = resumeValidator;
        this.resumeDaoFactory = resumeDaoFactory;
    }

    @Override
    public Optional<Resume> findById(long idResume) throws ServiceException {
        try (ResumeDao dao = resumeDaoFactory.create()) {
            return dao.findById(idResume);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Resume tryFindById(long idResume) throws ServiceException {
        Optional<Resume> optionalResume = findById(idResume);
        if (!optionalResume.isPresent()) {
            throw new EntityNotFoundException("Resume doesn't exist");
        }

        return optionalResume.get();
    }

    @Override
    public List<Resume> findUserResumes(long idUser) throws ServiceException {
        try (ResumeDao dao = resumeDaoFactory.create()) {
            return dao.findByUserId(idUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void addResume(long idUser, String name, String text) throws ServiceException {
        validateAndThrowExceptionIfFail(name, text);
        Resume resume = new Resume.Builder(idUser)
                .setName(name)
                .setText(text)
                .build(true);

        try (ResumeDao dao = resumeDaoFactory.create()) {
            int count = dao.getUserResumesCount(idUser);
            if (count >= MAX_RESUMES_COUNT_PER_USER) {
                throw new ValidationException(Collections.singletonList(RESUME_LIMIT_EXCEEDED_MESSAGE));
            }

            dao.save(resume);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateResume(long idResume, String name, String text) throws ServiceException {
        validateAndThrowExceptionIfFail(name, text);
        Resume resume = new Resume.Builder(idResume, DEFAULT_ID)
                .setName(name)
                .setText(text)
                .build(true);

        try (ResumeDao dao = resumeDaoFactory.create()) {
            dao.save(resume);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void validateAndThrowExceptionIfFail(String name, String text) throws ValidationException {
        List<String> fails = resumeValidator.getValidationFails(name, text);
        if (!fails.isEmpty()) {
            throw new ValidationException(fails);
        }
    }

    @Override
    public void remove(long idResume) throws ServiceException {
        try (ResumeDao dao = resumeDaoFactory.create()) {
            dao.removeById(idResume);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
