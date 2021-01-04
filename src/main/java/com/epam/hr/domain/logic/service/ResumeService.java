package com.epam.hr.domain.logic.service;

import com.epam.hr.data.dao.impl.ResumeDao;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.exception.DaoException;
import com.epam.hr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public class ResumeService {

    public Optional<Resume> findById(long id) throws ServiceException {
        try (ResumeDao dao = new ResumeDao()) {
            return dao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Resume> findUserResumes(long userId) throws ServiceException {
        try (ResumeDao dao = new ResumeDao()) {
            return dao.findByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void saveResume(Resume resume) throws ServiceException {
        try (ResumeDao dao = new ResumeDao()) {
            dao.save(resume);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void remove(long id) throws ServiceException {
        try (ResumeDao dao = new ResumeDao()) {
            dao.removeById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
