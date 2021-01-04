package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.AbstractDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.data.mapper.impl.ResumeMapper;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.exception.DaoException;
import java.util.List;
import java.util.Optional;

public class ResumeDao extends AbstractDao<Resume> {
    private static final String TABLE = "resume";
    private static final String INSERT_QUERY = String.format("insert into %s (id_user, text, name) values (?, ?, ?);", TABLE) ;
    private static final String UPDATE_QUERY = String.format("update %s set name = ?, text = ? where id = ?;", TABLE);
    private static final String RESUMES_BY_USER_ID = String.format("select * from %s where id_user = ?;", TABLE);

    private final Mapper<Resume> mapper = new ResumeMapper();

    @Override
    public Optional<Resume> getById(long id) throws DaoException {
        return super.findById(TABLE, mapper, id);
    }

    public List<Resume> findByUserId(long userId) throws DaoException {
        return executeQueryPrepared(RESUMES_BY_USER_ID, mapper, userId);
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
    public int findQuantity() throws DaoException {
        return super.findQuantity(TABLE);
    }

    @Override
    public void save(Resume resume) throws DaoException {
        long id = resume.getId();
        long idUser = resume.getIdUser();
        String text = resume.getText();
        String name = resume.getName();

        Optional<Resume> optional = getById(id);
        if (optional.isPresent()) {
            executeNoResultQueryPrepared(UPDATE_QUERY, name, text, id);
        } else {
            executeNoResultQueryPrepared(INSERT_QUERY, idUser, text, name);
        }
    }
}
