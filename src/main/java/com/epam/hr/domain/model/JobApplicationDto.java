package com.epam.hr.domain.model;

import java.util.Objects;

public class JobApplicationDto extends JobApplication {
    private final String vacancyName;
    private final String vacancyShortDescription;

    public JobApplicationDto(JobApplication jobApplication, String vacancyName,
                             String vacancyShortDescription) {
        super(jobApplication.getId(),
                jobApplication.getIdUser(),
                jobApplication.getIdVacancy(),
                jobApplication.getIdResume(),
                jobApplication.getDate(),
                jobApplication.getState(),
                jobApplication.getPreliminaryInterviewNote(),
                jobApplication.getTechnicalInterviewNote());
        this.vacancyName = vacancyName;
        this.vacancyShortDescription = vacancyShortDescription;
    }

    public String getVacancyName() {
        return vacancyName;
    }

    public String getVacancyShortDescription() {
        return vacancyShortDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        JobApplicationDto that = (JobApplicationDto) o;
        return Objects.equals(vacancyName, that.vacancyName)
                && Objects.equals(vacancyShortDescription, that.vacancyShortDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vacancyName, vacancyShortDescription);
    }
}
