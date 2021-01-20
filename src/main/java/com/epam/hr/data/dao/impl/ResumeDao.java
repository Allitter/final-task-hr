package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.AbstractDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ResumeDao extends AbstractDao<Resume> {
    private static final String TABLE = "resume";
    private static final String INSERT_QUERY = String.format("insert into %s (id_user, text, name) values (?, ?, ?);", TABLE) ;
    private static final String UPDATE_QUERY = String.format("update %s set name = ?, text = ? where id = ?;", TABLE);
    private static final String RESUMES_BY_USER_ID = String.format("select * from %s where id_user = ?;", TABLE);
    private final Mapper<Resume> mapper;

    public ResumeDao(Connection connection, Mapper<Resume> mapper) {
        super(connection);
        this.mapper = mapper;
    }

    @Override
    public Optional<Resume> findById(long id) throws DaoException {
        return super.findById(TABLE, mapper, id);
    }

    public List<Resume> findByUserId(long idUser) throws DaoException {
        return executeQueryPrepared(RESUMES_BY_USER_ID, mapper, idUser);
    }

    @Override
    public List<Resume> findAll(int start, int count) throws DaoException {
        return super.findAll(TABLE, mapper, start, count);
    }

    @Override
    public void removeById(long id) throws DaoException {
        super.removeById(TABLE, id);
    }

    @Override
    public int getRowCount() throws DaoException {
        return super.getRowCount(TABLE);
    }

    @Override
    public void save(Resume resume) throws DaoException {
        long id = resume.getId();
        long idUser = resume.getIdUser();
        String text = resume.getText();
        String name = resume.getName();

        Optional<Resume> optional = findById(id);
        if (optional.isPresent()) {
            executeNoResultQueryPrepared(UPDATE_QUERY, name, text, id);
        } else {
            executeNoResultQueryPrepared(INSERT_QUERY, idUser, text, name);
        }
    }
}
