package com.epam.hr.domain.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable {
    public static final User DEFAULT = new User();
    private static final String FORMATTED_DATE_PATTERN = "yyyy-MM-dd";
    private final long id;
    private final UserRole role;
    private final String login;
    private final String password;
    private final String name;
    private final String lastName;
    private final String patronymic;
    private final Date birthDate;
    private final boolean isBanned;

    public User(long id, UserRole role, String login, String password, String name,
                String lastName, String patronymic, Date birthDate, boolean isBanned) {
        this.id = id;
        this.role = role;
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.isBanned = isBanned;
    }

    private User() {
        id = -1;
        role = UserRole.DEFAULT;
        login = "";
        password = "";
        name = "";
        lastName = "";
        patronymic = "";
        birthDate = new Date();
        isBanned = false;
    }

    public long getId() {
        return id;
    }

    public UserRole getRole() {
        return role;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getFormattedBirthDate() {
        if (birthDate == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATTED_DATE_PATTERN);
        return dateFormat.format(birthDate);
    }

    public boolean isBanned() {
        return isBanned;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                ", isBanned=" + isBanned +
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
        User user = (User) o;
        return id == user.id && role == user.role
                && Objects.equals(login, user.login)
                && Objects.equals(password, user.password)
                && Objects.equals(name, user.name)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(patronymic, user.patronymic)
                && Objects.equals(birthDate, user.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, login, password, name, lastName, patronymic, birthDate);
    }
}
