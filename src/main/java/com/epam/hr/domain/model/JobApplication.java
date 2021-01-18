package com.epam.hr.domain.model;

import java.util.Date;
import java.util.Objects;

public class JobApplication {
    private final long id;
    private final User user;
    private final Vacancy vacancy;
    private final Date date;
    private final JobApplicationState state;
    private final String preliminaryInterviewNote;
    private final String technicalInterviewNote;
    private final String resumeText;

    public JobApplication(JobApplication.Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.vacancy = builder.vacancy;
        this.date = builder.date;
        this.state = builder.state;
        this.preliminaryInterviewNote = builder.preliminaryInterviewNote;
        this.technicalInterviewNote = builder.technicalInterviewNote;
        this.resumeText = builder.resumeText;
    }

    public long getId() {
        return id;
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

    public long getIdUser() {
        return user.getId();
    }

    public UserRole geUserRole() {
        return user.getRole();
    }

    public String getUserLogin() {
        return user.getLogin();
    }

    public String getUserName() {
        return user.getName();
    }

    public String getUserLastName() {
        return user.getLastName();
    }

    public String getUserPatronymic() {
        return user.getPatronymic();
    }

    public Date getUserBirthDate() {
        return user.getBirthDate();
    }

    public String getUserEmail() {
        return user.getEmail();
    }

    public String getUserPhone() {
        return user.getPhone();
    }

    public boolean isUserEnabled() {
        return user.isEnabled();
    }

    public boolean isUserBanned() {
        return user.isBanned();
    }

    public String getFormattedUserBirthDate() {
        return user.getFormattedBirthDate();
    }

    public long getIdVacancy() {
        return vacancy.getId();
    }

    public String getVacancyName() {
        return vacancy.getName();
    }

    public String getVacancyShortDescription() {
        return vacancy.getShortDescription();
    }

    public String getVacancyDescription() {
        return vacancy.getDescription();
    }

    @Override
    public String toString() {
        return "JobApplication{" +
                "id=" + id +
                ", user=" + user +
                ", vacancy=" + vacancy +
                ", date=" + date +
                ", state=" + state +
                ", preliminaryInterviewNote='" + preliminaryInterviewNote + '\'' +
                ", technicalInterviewNote='" + technicalInterviewNote + '\'' +
                ", resumeText='" + resumeText + '\'' +
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
        return id == that.id && Objects.equals(user, that.user)
                && Objects.equals(vacancy, that.vacancy)
                && Objects.equals(date, that.date)
                && state == that.state
                && Objects.equals(preliminaryInterviewNote, that.preliminaryInterviewNote)
                && Objects.equals(technicalInterviewNote, that.technicalInterviewNote)
                && Objects.equals(resumeText, that.resumeText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, vacancy, date, state,
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
        private User user;
        private Vacancy vacancy;
        private Date date;
        private JobApplicationState state;
        private String preliminaryInterviewNote;
        private String technicalInterviewNote;
        private String resumeText;

        public Builder() {
            id = DEFAULT_ID;
            date = DEFAULT_DATE;
            state = DEFAULT_STATE;
            preliminaryInterviewNote = DEFAULT_PRELIMINARY_INTERVIEW_NOTE;
            technicalInterviewNote = DEFAULT_TECHNICAL_INTERVIEW_NOTE;
            resumeText = DEFAULT_RESUME_TEXT;
        }

        public Builder(JobApplication jobApplication) {
            this.id = jobApplication.getId();
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

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setVacancy(Vacancy vacancy) {
            this.vacancy = vacancy;
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
            return new JobApplication(this);
        }
    }
}
