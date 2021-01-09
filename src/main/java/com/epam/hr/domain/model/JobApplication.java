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

    private JobApplication(long id, long idUser, long idVacancy,
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

    public JobApplication(JobApplication jobApplication) {
        this.id = jobApplication.getId();
        this.idUser = jobApplication.getIdUser();
        this.idVacancy = jobApplication.getIdVacancy();
        this.date = jobApplication.getDate();
        this.state = jobApplication.getState();
        this.preliminaryInterviewNote = jobApplication.getPreliminaryInterviewNote();
        this.technicalInterviewNote = jobApplication.getTechnicalInterviewNote();
        this.resumeText = jobApplication.getResumeText();
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
                && Objects.equals(technicalInterviewNote, that.technicalInterviewNote)
                && Objects.equals(resumeText, that.resumeText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, idVacancy, date, state,
                preliminaryInterviewNote, technicalInterviewNote, resumeText);
    }

    public static class Builder {
        private static final int DEFAULT_ID = -1;
        private static final Date DEFAULT_DATE = new Date();
        private static final JobApplicationState DEFAULT_STATE = JobApplicationState.RECENTLY_CREATED;
        private static final String DEFAULT_PRELIMINARY_INTERVIEW_NOTE = "";
        private static final String DEFAULT_TECHNICAL_INTERVIEW_NOTE = "";
        private static final String DEFAULT_RESUME_TEXT = "";

        private long id;
        private long idUser;
        private long idVacancy;
        private Date date;
        private JobApplicationState state;
        private String preliminaryInterviewNote;
        private String technicalInterviewNote;
        private String resumeText;

        public Builder() {
            id = DEFAULT_ID;
            idUser = DEFAULT_ID;
            idVacancy = DEFAULT_ID;
            date = DEFAULT_DATE;
            state = DEFAULT_STATE;
            preliminaryInterviewNote = DEFAULT_PRELIMINARY_INTERVIEW_NOTE;
            technicalInterviewNote = DEFAULT_TECHNICAL_INTERVIEW_NOTE;
            resumeText = DEFAULT_RESUME_TEXT;
        }

        public Builder(JobApplication jobApplication) {
            this.id = jobApplication.getId();
            this.idUser = jobApplication.getIdUser();
            this.idVacancy = jobApplication.getIdVacancy();
            this.date = jobApplication.getDate();
            this.state = jobApplication.getState();
            this.preliminaryInterviewNote = jobApplication.getPreliminaryInterviewNote();
            this.technicalInterviewNote = jobApplication.getTechnicalInterviewNote();
            this.resumeText = jobApplication.getResumeText();
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setIdUser(long idUser) {
            this.idUser = idUser;
            return this;
        }

        public Builder setIdVacancy(long idVacancy) {
            this.idVacancy = idVacancy;
            return this;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder setState(JobApplicationState state) {
            this.state = state;
            return this;
        }

        public Builder setPreliminaryInterviewNote(String preliminaryInterviewNote) {
            this.preliminaryInterviewNote = preliminaryInterviewNote;
            return this;
        }

        public Builder setTechnicalInterviewNote(String technicalInterviewNote) {
            this.technicalInterviewNote = technicalInterviewNote;
            return this;
        }

        public Builder setResumeText(String resumeText) {
            this.resumeText = resumeText;
            return this;
        }

        public JobApplication build() {
            return new JobApplication(id, idUser, idVacancy, date, state,
                    preliminaryInterviewNote, technicalInterviewNote, resumeText);
        }
    }
}
