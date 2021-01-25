package com.epam.hr.domain.service;

import com.epam.hr.domain.model.Resume;
import com.epam.hr.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Provides ability to work with resume entities
 */
public interface ResumeService {
    /**
     * @param idResume resume's id
     * @return optional with resume if such exists or empty optional
     * @throws ServiceException if error occurs
     */
    Optional<Resume> findById(long idResume) throws ServiceException;

    /**
     * @param idResume resume's id
     * @return resume if such exists or throws exception
     * @throws com.epam.hr.exception.EntityNotFoundException if entity not found
     * @throws ServiceException                              if error occurs
     */
    Resume tryFindById(long idResume) throws ServiceException;

    /**
     * @param idUser user's id
     * @return list with user's resumes or empty list if none found
     * @throws ServiceException if error occurs
     */
    List<Resume> findUserResumes(long idUser) throws ServiceException;

    /**
     * @param idUser user's id
     * @param name   resume's name
     * @param text   resume text
     * @throws com.epam.hr.exception.ValidationException if resume's name or text invalid
     * @throws ServiceException                          if error occurs
     */
    void addResume(long idUser, String name, String text) throws ServiceException;

    /**
     * @param idResume resume's id
     * @param name     resume's name
     * @param text     resume's text
     * @throws com.epam.hr.exception.ValidationException if resume's name or text invalid
     * @throws ServiceException                          if error occurs
     */
    void updateResume(long idResume, String name, String text) throws ServiceException;

    /**
     * Removes resume with particular id
     *
     * @param idResume resume's id
     * @throws ServiceException if error occurs
     */
    void remove(long idResume) throws ServiceException;
}
