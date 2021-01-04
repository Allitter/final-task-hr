package com.epam.hr.data.mapper.impl;

import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.JobApplicationState;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobApplicationMapper implements Mapper<JobApplication> {
    private static final String ID = "id";
    private static final String ID_USER = "id_user";
    private static final String ID_VACANCY = "id_vacancy";
    private static final String ID_RESUME = "id_resume";
    private static final String STATE = "state";
    private static final String DATE = "date";
    private static final String PRELIMINARY_INTERVIEW_NOTE = "preliminary_interview_note";
    private static final String TECHNICAL_INTERVIEW_NOTE = "technical_interview_note";

    @Override
    public JobApplication map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        long idUser = resultSet.getLong(ID_USER);
        long idVacancy = resultSet.getLong(ID_VACANCY);
        long idResume = resultSet.getLong(ID_RESUME);
        Date date = resultSet.getDate(DATE);
        String preliminaryInterviewNote = resultSet.getString(PRELIMINARY_INTERVIEW_NOTE);
        String technicalInterviewNote = resultSet.getString(TECHNICAL_INTERVIEW_NOTE);

        String stateName = resultSet.getString(STATE);
        JobApplicationState state = JobApplicationState.valueOf(stateName);

        return new JobApplication(id, idUser, idVacancy, idResume, date, state, preliminaryInterviewNote, technicalInterviewNote);
    }
}
