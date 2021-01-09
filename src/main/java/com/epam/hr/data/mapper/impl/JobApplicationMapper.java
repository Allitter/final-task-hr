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
    private static final String STATE = "state";
    private static final String DATE = "date";
    private static final String PRELIMINARY_INTERVIEW_NOTE = "preliminary_interview_note";
    private static final String TECHNICAL_INTERVIEW_NOTE = "technical_interview_note";
    private static final String RESUME_TEXT = "resume_text";

    @Override
    public JobApplication map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ID);
        long idUser = resultSet.getLong(ID_USER);
        long idVacancy = resultSet.getLong(ID_VACANCY);
        Date date = resultSet.getDate(DATE);
        String preliminaryInterviewNote = resultSet.getString(PRELIMINARY_INTERVIEW_NOTE);
        String technicalInterviewNote = resultSet.getString(TECHNICAL_INTERVIEW_NOTE);
        String resumeText = resultSet.getString(RESUME_TEXT);

        String stateName = resultSet.getString(STATE);
        JobApplicationState state = JobApplicationState.valueOf(stateName);

        return new JobApplication.Builder()
                .setId(id)
                .setIdUser(idUser)
                .setIdVacancy(idVacancy)
                .setDate(date)
                .setState(state)
                .setPreliminaryInterviewNote(preliminaryInterviewNote)
                .setTechnicalInterviewNote(technicalInterviewNote)
                .setResumeText(resumeText)
                .build();
    }
}
