package com.epam.hr.domain.model;

import java.util.Date;
import java.util.Objects;

public class JobApplication {
    private final long id;
    private final long idUser;
    private final long idVacancy;
    private final long idResume;
    private final Date date;
    private final JobApplicationState state;
    private final String preliminaryInterviewNote;
    private final String technicalInterviewNote;

    public JobApplication(long id, long idUser, long idVacancy, long idResume,
                          Date date, JobApplicationState state,
                          String preliminaryInterviewNote, String technicalInterviewNote) {
        this.id = id;
        this.idUser = idUser;
        this.idVacancy = idVacancy;
        this.idResume = idResume;
        this.date = date;
        this.state = state;
        this.preliminaryInterviewNote = preliminaryInterviewNote;
        this.technicalInterviewNote = technicalInterviewNote;
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

    public long getIdResume() {
        return idResume;
    }

    public String getPreliminaryInterviewNote() {
        return preliminaryInterviewNote;
    }

    public String getTechnicalInterviewNote() {
        return technicalInterviewNote;
    }

    public JobApplication changeState(JobApplicationState state) {
        return new JobApplication(id, idUser, idVacancy, idResume, date, state, preliminaryInterviewNote, technicalInterviewNote);
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idVacancy=" + idVacancy +
                ", idResume=" + idResume +
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
                && idResume == that.idResume
                && Objects.equals(date, that.date)
                && state == that.state
                && Objects.equals(preliminaryInterviewNote, that.preliminaryInterviewNote)
                && Objects.equals(technicalInterviewNote, that.technicalInterviewNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, idVacancy, idResume, date, state, preliminaryInterviewNote, technicalInterviewNote);
    }
}
