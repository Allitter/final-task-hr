package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.AbstractDao;
import com.epam.hr.data.mapper.impl.JobApplicationMapper;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.JobApplicationState;
import com.epam.hr.exception.DaoException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ApplicationDao extends AbstractDao<JobApplication> {
    private static final String TABLE = "job_application";
    private static final String UPDATE_QUERY = String.format("update %s set state = ?, preliminary_interview_note = ?, technical_interview_note = ? where id = ?;", TABLE);
    private static final String INSERT_QUERY = String.format("insert into %s (id_user, id_vacancy, date, state, id_resume, preliminary_interview_note, technical_interview_note) values (?, ?, ?, ?, ?, ?, ?);", TABLE) ;
    private static final String APPLICATIONS_BY_USER_ID = String.format("select * from %s where id_user = ?;", TABLE);
    private static final String APPLICATIONS_BY_VACANCY_ID = String.format("select * from %s where id_vacancy = ?;", TABLE) ;
    private static final String APPLICATIONS_BY_USER_ID_AND_VACANCY_ID = String.format("select * from %s where id_user = ? and id_vacancy = ?;", TABLE) ;
    private final JobApplicationMapper mapper = new JobApplicationMapper();

    @Override
    public Optional<JobApplication> getById(long id) throws DaoException {
        return findById(TABLE, mapper, id);
    }

    public List<JobApplication> findByUserId(long id) throws DaoException {
        return executeQueryPrepared(APPLICATIONS_BY_USER_ID, mapper, id);
    }

    public List<JobApplication> findByVacancyId(long id) throws DaoException {
        return executeQueryPrepared(APPLICATIONS_BY_VACANCY_ID, mapper, id);
    }

    public Optional<JobApplication> findByUserAndVacancyId(long userId, long vacancyId) throws DaoException {
        return executeSingleResultQueryPrepared(APPLICATIONS_BY_USER_ID_AND_VACANCY_ID, mapper, userId, vacancyId);
    }

    @Override
    public List<JobApplication> findAll(int start, int count) throws DaoException {
        return findAll(TABLE, mapper, start, count);
    }

    @Override
    public void removeById(long id) throws DaoException {
        removeById(TABLE, id);
    }

    @Override
    public int findQuantity() throws DaoException {
        return findQuantity(TABLE);
    }

    @Override
    public void save(JobApplication jobApplication) throws DaoException {
        long id = jobApplication.getId();
        long idUser = jobApplication.getIdUser();
        long idVacancy = jobApplication.getIdVacancy();
        long idResume = jobApplication.getIdResume();
        Date date = jobApplication.getDate();
        JobApplicationState state = jobApplication.getState();
        String stateName = state.name();
        String preliminaryInterviewNote = jobApplication.getPreliminaryInterviewNote();
        String technicalInterviewNote = jobApplication.getTechnicalInterviewNote();


        Optional<JobApplication> optional = getById(id);

        if (optional.isPresent()) {
            executeNoResultQueryPrepared(UPDATE_QUERY, stateName,
                    preliminaryInterviewNote, technicalInterviewNote, id);
        } else {
            executeNoResultQueryPrepared(INSERT_QUERY, idUser, idVacancy, date,
                    stateName, idResume, preliminaryInterviewNote, technicalInterviewNote);
        }
    }
}
