package com.epam.hr.domain.model;

import java.util.Objects;

public class JobApplicationVacancyDto extends JobApplicationDto {
    private final String userName;
    private final String userLastName;
    private final String userPatronymic;

    public JobApplicationVacancyDto(JobApplicationDto applicationDto,
                                    String userName, String userLastName,
                                    String userPatronymic) {
        super(applicationDto,
                applicationDto.getVacancyName(),
                applicationDto.getVacancyShortDescription());

        this.userName = userName;
        this.userLastName = userLastName;
        this.userPatronymic = userPatronymic;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserPatronymic() {
        return userPatronymic;
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
        JobApplicationVacancyDto that = (JobApplicationVacancyDto) o;
        return Objects.equals(userName, that.userName)
                && Objects.equals(userLastName, that.userLastName)
                && Objects.equals(userPatronymic, that.userPatronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userName, userLastName, userPatronymic);
    }
}
