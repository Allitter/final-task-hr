package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.AbstractDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.JobApplicationState;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JobApplicationDao extends AbstractDao<JobApplication> {
    private static final String TABLE = "job_application";
    private static final String FULL_TABLE = "job_application ja join user u on ja.id_user = u.id join vacancy v on ja.id_vacancy = v.id";
    private static final String FULL_ATTRIBUTES = "ja.*, u.id as `u.id`, u.login as `u.login`, u.role as `u.role`, u.name as `u.name`, u.last_name as `u.last_name`, u.patronymic as `u.patronymic`, u.birth_date as `u.birth_date`, u.banned as `u.banned`, u.email as `u.email`, u.phone as `u.phone`, u.enabled as `u.enabled`, v.id as `v.id`, v.name as `v.name`, v.short_description as `v.short_description`, v.description as `v.description`";
    private static final String UPDATE_QUERY = String.format("update %s set state = ?, preliminary_interview_note = ?, technical_interview_note = ?, resume_text = ? where id = ?;", TABLE);
    private static final String INSERT_QUERY = String.format("insert into %s (id_user, id_vacancy, date, state, preliminary_interview_note, technical_interview_note, resume_text) values (?, ?, ?, ?, ?, ?, ?);", TABLE) ;
    private static final String APPLICATIONS_BY_USER_ID = String.format("select %s from %s where id_user = ? limit ?, ?;", FULL_ATTRIBUTES, FULL_TABLE);
    private static final String APPLICATIONS_BY_VACANCY_ID = String.format("select %s from %s where id_vacancy = ? limit ?, ?;", FULL_ATTRIBUTES, FULL_TABLE) ;
    private static final String APPLICATIONS_BY_USER_ID_AND_VACANCY_ID = String.format("select %s from %s where id_user = ? and id_vacancy = ?;", FULL_ATTRIBUTES, FULL_TABLE) ;
    private static final String FIND_BY_ID_QUERY = String.format("select %s from %s where ja.id = ?;", FULL_ATTRIBUTES, FULL_TABLE);
    private static final String USER_JOB_APPLICATIONS_QUANTITY_CONDITION = "id_user = %d";
    private static final String VACANCY_JOB_APPLICATIONS_QUANTITY_CONDITION = "id_vacancy = %d";
    private final Mapper<JobApplication> mapper;

    public JobApplicationDao(Connection connection, Mapper<JobApplication> mapper) {
        super(connection);
        this.mapper = mapper;
    }

    @Override
    public Optional<JobApplication> findById(long id) throws DaoException {
        return executeSingleResultQueryPrepared(FIND_BY_ID_QUERY, mapper, id);
    }

    public List<JobApplication> findByUserId(long id, int start, int count) throws DaoException {
        return executeQueryPrepared(APPLICATIONS_BY_USER_ID, mapper, id, start, count);
    }

    public List<JobApplication> findByVacancyId(long id, int start, int count) throws DaoException {
        return executeQueryPrepared(APPLICATIONS_BY_VACANCY_ID, mapper, id, start, count);
    }

    public Optional<JobApplication> findByUserAndVacancyId(long userId, long vacancyId) throws DaoException {
        return executeSingleResultQueryPrepared(APPLICATIONS_BY_USER_ID_AND_VACANCY_ID, mapper, userId, vacancyId);
    }

    @Override
    public List<JobApplication> findAll(int start, int count) throws DaoException {
        return findAll(FULL_TABLE, FULL_ATTRIBUTES, mapper, start, count);
    }

    @Override
    public void removeById(long id) throws DaoException {
        removeById(TABLE, id);
    }

    @Override
    public int getRowCount() throws DaoException {
        return getRowCount(TABLE);
    }

    public int findUserJobApplicationsQuantity(long idUser) throws DaoException {
        String condition = String.format(USER_JOB_APPLICATIONS_QUANTITY_CONDITION, idUser);
        return getRowCount(TABLE, condition);
    }

    public int findVacancyJobApplicationsQuantity(long idVacancy) throws DaoException {
        String condition = String.format(VACANCY_JOB_APPLICATIONS_QUANTITY_CONDITION, idVacancy);
        return getRowCount(TABLE, condition);
    }

    @Override
    public void save(JobApplication jobApplication) throws DaoException {
        long id = jobApplication.getId();
        long idUser = jobApplication.getIdUser();
        long idVacancy = jobApplication.getIdVacancy();
        Date date = jobApplication.getDate();
        JobApplicationState state = jobApplication.getState();
        String stateName = state.name();
        String preliminaryInterviewNote = jobApplication.getPreliminaryInterviewNote();
        String technicalInterviewNote = jobApplication.getTechnicalInterviewNote();
        String resumeText = jobApplication.getResumeText();

        Optional<JobApplication> optional = findById(id);

        if (optional.isPresent()) {
            executeNoResultQueryPrepared(UPDATE_QUERY, stateName,
                    preliminaryInterviewNote, technicalInterviewNote, resumeText, id);
        } else {
            executeNoResultQueryPrepared(INSERT_QUERY, idUser, idVacancy, date,
                    stateName, preliminaryInterviewNote, technicalInterviewNote, resumeText);
        }
    }
}
