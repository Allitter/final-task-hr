package com.epam.hr.data.dao;

import com.epam.hr.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface Dao<T> extends AutoCloseable {

    Optional<T> findById(long id) throws DaoException;

    List<T> findAll(int start, int count) throws DaoException;

    void save(T item) throws DaoException;

    void removeById(long id) throws DaoException;

    int getRowCount() throws DaoException;
}
