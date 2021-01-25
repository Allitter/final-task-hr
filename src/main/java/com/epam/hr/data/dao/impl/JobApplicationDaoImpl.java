package com.epam.hr.data.dao.impl;

import com.epam.hr.data.dao.JobApplicationDao;
import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.exception.DaoException;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JobApplicationDaoImpl extends AbstractDao<JobApplication> implements JobApplicationDao {
    private static final String TABLE = "job_application";
    private static final String FULL_TABLE = "job_application ja join user u on ja.id_user = u.id join vacancy v on ja.id_vacancy = v.id";
    private static final String FULL_ATTRIBUTES = "ja.*, u.id as `u.id`, u.login as `u.login`, u.role as `u.role`, u.name as `u.name`, u.last_name as `u.last_name`, u.avatar as `u.avatar`," +
            " u.patronymic as `u.patronymic`, u.birth_date as `u.birth_date`, u.banned as `u.banned`, u.email as `u.email`, u.phone as `u.phone`, u.enabled as `u.enabled`," +
            " v.id as `v.id`, v.name as `v.name`, v.short_description as `v.short_description`, v.description as `v.description`, v.closed as `v.closed`";
    private static final String UPDATE_QUERY = String.format("update %s set state = ?, preliminary_interview_note = ?, technical_interview_note = ?, resume_text = ? where id = ?;", TABLE);
    private static final String INSERT_QUERY = String.format("insert into %s (id_user, id_vacancy, date, state, preliminary_interview_note, technical_interview_note, resume_text) values (?, ?, ?, ?, ?, ?, ?);", TABLE);
    private static final String APPLICATIONS_BY_USER_ID = String.format("select %s from %s where id_user = ? and ja.removed = 0 limit ?, ?;", FULL_ATTRIBUTES, FULL_TABLE);
    private static final String APPLICATIONS_BY_VACANCY_ID = String.format("select %s from %s where id_vacancy = ? and ja.removed = 0 limit ?, ?;", FULL_ATTRIBUTES, FULL_TABLE);
    private static final String APPLICATIONS_BY_USER_ID_AND_VACANCY_ID = String.format("select %s from %s where id_user = ? and id_vacancy = ? and ja.removed = 0;", FULL_ATTRIBUTES, FULL_TABLE);
    private static final String FIND_BY_ID_QUERY = String.format("select %s from %s where ja.id = ? and ja.removed = 0;", FULL_ATTRIBUTES, FULL_TABLE);
    private static final String USER_JOB_APPLICATIONS_QUANTITY_CONDITION = "id_user = ?";
    private static final String VACANCY_JOB_APPLICATIONS_QUANTITY_CONDITION = "id_vacancy = ?";
    private static final String BLOCK_NON_APPLIED_JOB_APPLICATIONS_BY_VACANCY_ID_QUERY = "update job_application set state = 'BLOCKED' where id_vacancy = ?;";
    private static final String TABLE_PREFIX = "ja.";

    private final Mapper<JobApplication> mapper;

    public JobApplicationDaoImpl(Connection connection, Mapper<JobApplication> mapper) {
        this(connection, mapper, true);
    }

    public JobApplicationDaoImpl(Connection connection, Mapper<JobApplication> mapper, boolean canCloseConnection) {
        super(canCloseConnection, connection);
        this.mapper = mapper;
    }

    @Override
    public Optional<JobApplication> findById(long idJobApplication) throws DaoException {
        return executeSingleResultQueryPrepared(FIND_BY_ID_QUERY, mapper, idJobApplication);
    }

    @Override
    public List<JobApplication> findByUserId(long idUser, int start, int count) throws DaoException {
        return executeQueryPrepared(APPLICATIONS_BY_USER_ID, mapper, idUser, start, count);
    }

    @Override
    public List<JobApplication> findByVacancyId(long idVacancy, int start, int count) throws DaoException {
        return executeQueryPrepared(APPLICATIONS_BY_VACANCY_ID, mapper, idVacancy, start, count);
    }

    @Override
    public Optional<JobApplication> findByUserAndVacancyId(long idUser, long idVacancy) throws DaoException {
        return executeSingleResultQueryPrepared(APPLICATIONS_BY_USER_ID_AND_VACANCY_ID, mapper, idUser, idVacancy);
    }

    @Override
    public void blockNonAppliedJobApplicationsByVacancyId(long idVacancy) throws DaoException {
        executeNoResultQueryPrepared(BLOCK_NON_APPLIED_JOB_APPLICATIONS_BY_VACANCY_ID_QUERY, idVacancy);
    }

    @Override
    public List<JobApplication> findAll(int start, int count) throws DaoException {
        return findAll(FULL_TABLE, FULL_ATTRIBUTES, TABLE_PREFIX, mapper, start, count);
    }

    @Override
    public void removeById(long idJobApplication) throws DaoException {
        removeById(TABLE, idJobApplication);
    }

    @Override
    public int getRowCount() throws DaoException {
        return getRowCount(TABLE);
    }

    @Override
    public int getUserJobApplicationsCount(long idUser) throws DaoException {
        return getRowCount(TABLE, USER_JOB_APPLICATIONS_QUANTITY_CONDITION, idUser);
    }

    @Override
    public int getVacancyJobApplicationsCount(long idVacancy) throws DaoException {
        return getRowCount(TABLE, VACANCY_JOB_APPLICATIONS_QUANTITY_CONDITION, idVacancy);
    }

    @Override
    public void save(JobApplication jobApplication) throws DaoException {
        if (!jobApplication.isValid()) {
            throw new DaoException("attempt to save invalid object");
        }

        long id = jobApplication.getId();
        long idUser = jobApplication.getIdUser();
        long idVacancy = jobApplication.getIdVacancy();
        Date date = jobApplication.getDate();
        JobApplication.State state = jobApplication.getState();
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
