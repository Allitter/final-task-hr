package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.AbstractDao;
import com.epam.hr.data.mapper.impl.JobApplicationMapper;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.JobApplicationState;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JobApplicationDao extends AbstractDao<JobApplication> {
    private static final String TABLE = "job_application";
    private static final String UPDATE_QUERY = String.format("update %s set state = ?, preliminary_interview_note = ?, technical_interview_note = ?, resume_text = ? where id = ?;", TABLE);
    private static final String INSERT_QUERY = String.format("insert into %s (id_user, id_vacancy, date, state, preliminary_interview_note, technical_interview_note, resume_text) values (?, ?, ?, ?, ?, ?, ?);", TABLE) ;
    private static final String APPLICATIONS_BY_USER_ID = String.format("select * from %s where id_user = ? limit ?, ?;", TABLE);
    private static final String APPLICATIONS_BY_VACANCY_ID = String.format("select * from %s where id_vacancy = ? limit ?, ?;", TABLE) ;
    private static final String APPLICATIONS_BY_USER_ID_AND_VACANCY_ID = String.format("select * from %s where id_user = ? and id_vacancy = ?;", TABLE) ;
    private static final String USER_JOB_APPLICATIONS_QUANTITY_CONDITION = "id_user = %d";
    private static final String VACANCY_JOB_APPLICATIONS_QUANTITY_CONDITION = "id_vacancy = %d";

    private final JobApplicationMapper mapper = new JobApplicationMapper();

    public JobApplicationDao(Connection connection) {
        super(connection);
    }

    @Override
    public Optional<JobApplication> getById(long id) throws DaoException {
        return findById(TABLE, mapper, id);
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

    public int findUserJobApplicationsQuantity(long idUser) throws DaoException {
        String condition = String.format(USER_JOB_APPLICATIONS_QUANTITY_CONDITION, idUser);
        return findQuantity(TABLE, condition);
    }

    public int findVacancyJobApplicationsQuantity(long idVacancy) throws DaoException {
        String condition = String.format(VACANCY_JOB_APPLICATIONS_QUANTITY_CONDITION, idVacancy);
        return findQuantity(TABLE, condition);
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


        Optional<JobApplication> optional = getById(id);

        if (optional.isPresent()) {
            executeNoResultQueryPrepared(UPDATE_QUERY, stateName,
                    preliminaryInterviewNote, technicalInterviewNote, resumeText, id);
        } else {
            executeNoResultQueryPrepared(INSERT_QUERY, idUser, idVacancy, date,
                    stateName, preliminaryInterviewNote, technicalInterviewNote, resumeText);
        }
    }
}
