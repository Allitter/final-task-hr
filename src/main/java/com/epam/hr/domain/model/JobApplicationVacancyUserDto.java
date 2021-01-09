package com.epam.hr.domain.model;

import java.util.Date;
import java.util.Objects;

public class JobApplicationVacancyUserDto extends JobApplicationVacancyDto {
    private final User user;

    public JobApplicationVacancyUserDto(JobApplication jobApplication, Vacancy vacancy, User user) {
        super(jobApplication, vacancy);
        this.user = user;
    }

    public UserRole getUserRole() {
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

    @Override
    public String toString() {
        return "JobApplicationVacancyUserDto{" +
                "user=" + user +
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
        if (!super.equals(o)) {
            return false;
        }
        JobApplicationVacancyUserDto that = (JobApplicationVacancyUserDto) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user);
    }
}
