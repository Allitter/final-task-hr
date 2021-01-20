package com.epam.hr.domain.model;

import java.util.Date;

public final class JobApplication extends Entity {
    private final User user;
    private final Vacancy vacancy;
    private final Date date;
    private final State state;
    private final String preliminaryInterviewNote;
    private final String technicalInterviewNote;
    private final String resumeText;

    public JobApplication(JobApplication.Builder builder) {
        super(builder);
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

    public State getState() {
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

    public User.Role geUserRole() {
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

    public String getUserBirthDate() {
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
        return user.getBirthDate();
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

    public static class Builder extends Entity.Builder<JobApplication> {
        private static final State DEFAULT_STATE = State.RECENTLY_CREATED;
        private static final String DEFAULT_PRELIMINARY_INTERVIEW_NOTE = "";
        private static final String DEFAULT_TECHNICAL_INTERVIEW_NOTE = "";
        private static final String DEFAULT_RESUME_TEXT = "";

        private User user;
        private Vacancy vacancy;
        private Date date;
        private State state;
        private String preliminaryInterviewNote;
        private String technicalInterviewNote;
        private String resumeText;

        public Builder() {
            this(DEFAULT_ID);
        }

        public Builder(long id) {
            super(id);
            this.date = new Date();
            this.state = DEFAULT_STATE;
            this.preliminaryInterviewNote = DEFAULT_PRELIMINARY_INTERVIEW_NOTE;
            this.technicalInterviewNote = DEFAULT_TECHNICAL_INTERVIEW_NOTE;
            this.resumeText = DEFAULT_RESUME_TEXT;
        }

        public Builder(JobApplication jobApplication) {
            super(jobApplication);
            this.date = jobApplication.getDate();
            this.state = jobApplication.getState();
            this.preliminaryInterviewNote = jobApplication.getPreliminaryInterviewNote();
            this.technicalInterviewNote = jobApplication.getTechnicalInterviewNote();
            this.resumeText = jobApplication.getResumeText();
            this.vacancy = jobApplication.vacancy;
            this.user = jobApplication.user;
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

        public Builder setState(State state) {
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

        @Override
        public JobApplication build(boolean isValid) {
            this.isValid = isValid;
            return new JobApplication(this);
        }
    }

    public enum State {
        RECENTLY_CREATED,
        PRELIMINARY_INTERVIEW,
        TECHNICAL_INTERVIEW,
        APPLIED,
        BLOCKED
    }
}
