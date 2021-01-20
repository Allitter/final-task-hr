package com.epam.hr.domain.service.impl;

import com.epam.hr.data.dao.factory.impl.ResumeDaoFactory;
import com.epam.hr.data.dao.impl.ResumeDao;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.validator.ResumeValidator;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ResumeServiceImpl implements ResumeService {
    private static final String RESUME_LIMIT_EXCEEDED = "noMoreResumes";
    private static final int DEFAULT_ID = -1;
    private static final int MAX_RESUMES_COUNT_PER_USER = 10;
    private final ResumeValidator resumeValidator;
    private final ResumeDaoFactory resumeDaoFactory;

    public ResumeServiceImpl(ResumeValidator resumeValidator, ResumeDaoFactory resumeDaoFactory) {
        this.resumeValidator = resumeValidator;
        this.resumeDaoFactory = resumeDaoFactory;
    }

    @Override
    public Optional<Resume> findById(long id) throws ServiceException {
        try (ResumeDao dao = resumeDaoFactory.create()) {
            return dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Resume tryFindById(long id) throws ServiceException {
        Optional<Resume> optionalResume = findById(id);
        if (!optionalResume.isPresent()) {
            throw new ServiceException("Resume doesn't exist");
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

        try (ResumeDao dao = resumeDaoFactory.create()) {
            List<Resume> resumes = dao.findByUserId(idUser);
            if (resumes.size() > MAX_RESUMES_COUNT_PER_USER) {
                throw new ValidationException(Collections.singletonList(RESUME_LIMIT_EXCEEDED));
            }
            Resume resume = new Resume(DEFAULT_ID, idUser, name, text);

            dao.save(resume);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateResume(long id, String name, String text) throws ServiceException {
        validateAndThrowExceptionIfFail(name, text);

        try (ResumeDao dao = resumeDaoFactory.create()) {
            Resume resume = new Resume(id, DEFAULT_ID, name, text);

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
    public void remove(long id) throws ServiceException {
        try (ResumeDao dao = resumeDaoFactory.create()) {
            dao.removeById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
