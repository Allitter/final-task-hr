package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.ResumeDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ResumeDaoImpl extends AbstractDao<Resume> implements ResumeDao {
    private static final String TABLE = "resume";
    private static final String INSERT_QUERY = String.format("insert into %s (id_user, text, name) values (?, ?, ?);", TABLE);
    private static final String UPDATE_QUERY = String.format("update %s set name = ?, text = ? where id = ?;", TABLE);
    private static final String RESUMES_BY_USER_ID = String.format("select * from %s where id_user = ? and removed = 0;", TABLE);
    private static final String USER_RESUMES_COUNT_CONDITION = "id_user = ?";
    private final Mapper<Resume> mapper;

    public ResumeDaoImpl(Connection connection, Mapper<Resume> mapper) {
        this(connection, mapper, true);
    }

    public ResumeDaoImpl(Connection connection, Mapper<Resume> mapper, boolean canCloseConnection) {
        super(canCloseConnection, connection);
        this.mapper = mapper;
    }

    @Override
    public Optional<Resume> findById(long id) throws DaoException {
        return super.findById(TABLE, mapper, id);
    }

    @Override
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
    public int getUserResumesCount(long idUser) throws DaoException {
        return getRowCount(TABLE, USER_RESUMES_COUNT_CONDITION);
    }

    @Override
    public void save(Resume resume) throws DaoException {
        if (!resume.isValid()) {
            throw new DaoException("attempt to save invalid object");
        }

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
