package com.epam.hr.domain.service;

import com.epam.hr.domain.model.Resume;
import com.epam.hr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ResumeService {
    Optional<Resume> findById(long id) throws ServiceException;

    Resume tryFindById(long id) throws ServiceException;

    List<Resume> findUserResumes(long idUser) throws ServiceException;

    void addResume(long idUser, String name, String text) throws ServiceException;

    void updateResume(long id, String name, String text) throws ServiceException;

    void remove(long id) throws ServiceException;
}
