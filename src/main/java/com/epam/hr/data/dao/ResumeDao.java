package com.epam.hr.data.dao;

import com.epam.hr.domain.model.Resume;
import com.epam.hr.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ResumeDao extends Dao<Resume> {
    /**
     * @param id entity's id
     * @return optional of resume
     * @throws DaoException if sql error occurs
     */
    @Override
    Optional<Resume> findById(long id) throws DaoException;

    /**
     * @param idUser user's id
     * @return list of user's resumes resumes by
     * @throws DaoException if sql error occurs
     */
    List<Resume> findByUserId(long idUser) throws DaoException;

    /**
     * @param start first entity position inclusive
     * @param count entities count
     * @return l
     * @throws DaoException if sql error occurs
     */
    @Override
    List<Resume> findAll(int start, int count) throws DaoException;

    /**
     * @param id id of entity to removes
     * @throws DaoException if sql error occurs
     */
    @Override
    void removeById(long id) throws DaoException;

    /**
     * @return count of resumes
     * @throws DaoException if sql error occurs
     */
    @Override
    int getRowCount() throws DaoException;

    /**
     * @param idUser user's id
     * @return count of user's resumes
     * @throws DaoException if sql error occurs
     */
    int getUserResumesCount(long idUser) throws DaoException;

    /**
     * @param resume entity to add or update
     * @throws DaoException if sql error occurs
     */
    @Override
    void save(Resume resume) throws DaoException;
}
