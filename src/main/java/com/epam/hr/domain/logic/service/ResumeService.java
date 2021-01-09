package com.epam.hr.domain.logic.service;

import com.epam.hr.data.dao.factory.impl.ResumeDaoFactory;
import com.epam.hr.data.dao.impl.ResumeDao;
import com.epam.hr.domain.logic.validator.ResumeValidator;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ResumeService {
    private static final String RESUME_LIMIT_EXCEEDED = "noMoreResumes";
    private final ResumeValidator resumeValidator;
    private final ResumeDaoFactory resumeDaoFactory;

    public ResumeService(ResumeValidator resumeValidator, ResumeDaoFactory resumeDaoFactory) {
        this.resumeValidator = resumeValidator;
        this.resumeDaoFactory = resumeDaoFactory;
    }

    public Optional<Resume> findById(long id) throws ServiceException {
        try (ResumeDao dao = resumeDaoFactory.create()) {
            return dao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Resume> findUserResumes(long userId) throws ServiceException {
        try (ResumeDao dao = resumeDaoFactory.create()) {
            return dao.findByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void saveResume(Resume resume) throws ServiceException {
        try (ResumeDao dao = resumeDaoFactory.create()) {
            List<String> fails = resumeValidator.validate(resume);
            if (!fails.isEmpty()) {
                throw new ValidationException(fails);
            }
            long idUser = resume.getIdUser();
            List<Resume> resumes = dao.findByUserId(idUser);
            if (resumes.size() > 9) {
                throw new ValidationException(Collections.singletonList(RESUME_LIMIT_EXCEEDED));
            }

            dao.save(resume);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void remove(long id) throws ServiceException {
        try (ResumeDao dao = resumeDaoFactory.create()) {
            dao.removeById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
