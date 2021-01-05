package com.epam.hr.domain.model;

import java.util.Date;
import java.util.Objects;

public class JobApplication {
    private final long id;
    private final long idUser;
    private final long idVacancy;
    private final Date date;
    private final JobApplicationState state;
    private final String preliminaryInterviewNote;
    private final String technicalInterviewNote;
    private final String resumeText;

    public JobApplication(long id, long idUser, long idVacancy,
                          Date date, JobApplicationState state,
                          String preliminaryInterviewNote,
                          String technicalInterviewNote, String resumeText) {
        this.id = id;
        this.idUser = idUser;
        this.idVacancy = idVacancy;
        this.date = date;
        this.state = state;
        this.preliminaryInterviewNote = preliminaryInterviewNote;
        this.technicalInterviewNote = technicalInterviewNote;
        this.resumeText = resumeText;
    }

    public long getId() {
        return id;
    }

    public long getIdUser() {
        return idUser;
    }

    public long getIdVacancy() {
        return idVacancy;
    }

    public Date getDate() {
        return date;
    }

    public JobApplicationState getState() {
        return state;
    }

    public String getPreliminaryInterviewNote() {
        return preliminaryInterviewNote;
    }

    public String getResumeText() {
        return resumeText;
    }

    public String getTechnicalInterviewNote() {
        return technicalInterviewNote;
    }

    public JobApplication changeState(JobApplicationState state) {
        return new JobApplication(id, idUser, idVacancy, date, state, preliminaryInterviewNote, technicalInterviewNote, resumeText);
    }

    public JobApplication changePreliminaryNote(String preliminaryNote) {
        return new JobApplication(id, idUser, idVacancy, date, state, preliminaryNote, technicalInterviewNote, resumeText);
    }

    public JobApplication changeTechnicalNote(String technicalNote) {
        return new JobApplication(id, idUser, idVacancy, date, state, preliminaryInterviewNote, technicalNote, resumeText);
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idVacancy=" + idVacancy +
                ", date=" + date +
                ", state=" + state +
                ", preliminaryInterviewNote='" + preliminaryInterviewNote + '\'' +
                ", technicalInterviewNote='" + technicalInterviewNote + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobApplication that = (JobApplication) o;
        return id == that.id
                && idUser == that.idUser
                && idVacancy == that.idVacancy
                && Objects.equals(date, that.date)
                && state == that.state
                && Objects.equals(preliminaryInterviewNote, that.preliminaryInterviewNote)
                && Objects.equals(technicalInterviewNote, that.technicalInterviewNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, idVacancy, date, state, preliminaryInterviewNote, technicalInterviewNote);
    }
}
