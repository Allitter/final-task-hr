package com.epam.hr.data.dao;

import com.epam.hr.exception.DaoException;

import java.io.Closeable;
import java.util.List;
import java.util.Optional;

public interface Dao<T> extends Closeable {

    Optional<T> getById(long id) throws DaoException;

    List<T> findAll(int start, int count) throws DaoException;

    void save(T item) throws DaoException;

    void removeById(long id) throws DaoException;

    int findQuantity() throws DaoException;
}
