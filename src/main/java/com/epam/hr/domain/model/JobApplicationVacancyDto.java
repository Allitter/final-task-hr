package com.epam.hr.domain.model;

import java.util.Date;
import java.util.Objects;

public class JobApplicationVacancyDto {
    private final JobApplication jobApplication;
    private final Vacancy vacancy;

    public JobApplicationVacancyDto(JobApplication jobApplication, Vacancy vacancy) {
        this.jobApplication = jobApplication;
        this.vacancy = vacancy;
    }

    public long getId() {
        return jobApplication.getId();
    }

    public long getIdUser() {
        return jobApplication.getIdUser();
    }

    public long getIdVacancy() {
        return jobApplication.getIdVacancy();
    }

    public Date getDate() {
        return jobApplication.getDate();
    }

    public JobApplicationState getState() {
        return jobApplication.getState();
    }

    public String getPreliminaryInterviewNote() {
        return jobApplication.getPreliminaryInterviewNote();
    }

    public String getResumeText() {
        return jobApplication.getResumeText();
    }

    public String getTechnicalInterviewNote() {
        return jobApplication.getTechnicalInterviewNote();
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
        return "JobApplicationVacancyDto{" +
                "jobApplication=" + jobApplication +
                ", vacancy=" + vacancy +
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
        JobApplicationVacancyDto dto = (JobApplicationVacancyDto) o;
        return Objects.equals(jobApplication, dto.jobApplication)
                && Objects.equals(vacancy, dto.vacancy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobApplication, vacancy);
    }
}
