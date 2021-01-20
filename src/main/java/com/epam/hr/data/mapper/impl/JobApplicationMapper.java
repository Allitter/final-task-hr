package com.epam.hr.data.mapper.impl;

import com.epam.hr.data.mapper.Mapper;
import com.epam.hr.domain.model.JobApplication;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.Vacancy;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobApplicationMapper implements Mapper<JobApplication> {
    private static final String EMPTY_ATTRIBUTE_PREFIX = "";
    private static final String ATTRIBUTE_PREFIX = "ja.";
    private static final String ID = "id";
    private static final String STATE = "state";
    private static final String DATE = "date";
    private static final String PRELIMINARY_INTERVIEW_NOTE = "preliminary_interview_note";
    private static final String TECHNICAL_INTERVIEW_NOTE = "technical_interview_note";
    private static final String RESUME_TEXT = "resume_text";
    private final Mapper<Vacancy> vacancyMapper;
    private final Mapper<User> userMapper;

    public JobApplicationMapper(Mapper<Vacancy> vacancyMapper, Mapper<User> userMapper) {
        this.vacancyMapper = vacancyMapper;
        this.userMapper = userMapper;
    }

    @Override
    public JobApplication map(ResultSet resultSet) throws SQLException {
        return map(resultSet, EMPTY_ATTRIBUTE_PREFIX);
    }

    @Override
    public JobApplication mapForAnotherEntity(ResultSet resultSet) throws SQLException {
        return map(resultSet, ATTRIBUTE_PREFIX);
    }

    public JobApplication map(ResultSet resultSet, String attributePrefix) throws SQLException {
        long id = resultSet.getLong(attributePrefix + ID);
        Date date = resultSet.getDate(attributePrefix + DATE);
        String preliminaryInterviewNote = resultSet.getString(attributePrefix + PRELIMINARY_INTERVIEW_NOTE);
        String technicalInterviewNote = resultSet.getString(attributePrefix + TECHNICAL_INTERVIEW_NOTE);
        String resumeText = resultSet.getString(attributePrefix + RESUME_TEXT);

        String stateName = resultSet.getString(attributePrefix + STATE);
        JobApplication.State state = JobApplication.State.valueOf(stateName);

        User user = userMapper.mapForAnotherEntity(resultSet);
        Vacancy vacancy = vacancyMapper.mapForAnotherEntity(resultSet);

        return new JobApplication.Builder(id)
                .setUser(user)
                .setVacancy(vacancy)
                .setDate(date)
                .setState(state)
                .setPreliminaryInterviewNote(preliminaryInterviewNote)
                .setTechnicalInterviewNote(technicalInterviewNote)
                .setResumeText(resumeText)
                .build(true);
    }
}
