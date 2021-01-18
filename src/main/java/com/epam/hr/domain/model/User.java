package com.epam.hr.domain.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable {
    public static final User DEFAULT = new Builder().build();
    private static final String FORMATTED_DATE_PATTERN = "yyyy-MM-dd";
    private final long id;
    private final UserRole role;
    private final String login;
    private final String password;
    private final String name;
    private final String lastName;
    private final String patronymic;
    private final String email;
    private final String phone;
    private final Date birthDate;
    private final boolean isBanned;
    private final boolean enabled;

    private User(User.Builder builder) {
        this.id = builder.id;
        this.role = builder.role;
        this.login = builder.login;
        this.password = builder.password;
        this.name = builder.name;
        this.lastName = builder.lastName;
        this.patronymic = builder.patronymic;
        this.email = builder.email;
        this.phone = builder.phone;
        this.birthDate = builder.birthDate;
        this.isBanned = builder.isBanned;
        this.enabled = builder.enabled;
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

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public String getFormattedBirthDate() {
        if (birthDate == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATTED_DATE_PATTERN);
        return dateFormat.format(birthDate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                ", isBanned=" + isBanned +
                ", enabled=" + enabled +
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
        return id == user.id
                && isBanned == user.isBanned
                && enabled == user.enabled
                && role == user.role
                && Objects.equals(login, user.login)
                && Objects.equals(name, user.name)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(patronymic, user.patronymic)
                && Objects.equals(email, user.email)
                && Objects.equals(phone, user.phone)
                && Objects.equals(birthDate, user.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, login, name, lastName,
                patronymic, email, phone, birthDate, isBanned, enabled);
    }

    public static class Builder {
        private static final int DEFAULT_ID = -1;
        private static final UserRole DEFAULT_ROLE = UserRole.DEFAULT;
        private static final String DEFAULT_LOGIN = "";
        private static final String DEFAULT_PASSWORD = "";
        private static final String DEFAULT_NAME = "";
        private static final String DEFAULT_LAST_NAME = "";
        private static final String DEFAULT_PATRONYMIC = "";
        private static final String DEFAULT_EMAIL = "";
        private static final String DEFAULT_PHONE = "";
        private static final Date DEFAULT_BIRTH_DATE = new Date();
        private static final boolean DEFAULT_IS_BANNED = false;
        private static final boolean DEFAULT_ENABLED = true;

        private long id;
        private UserRole role;
        private String login;
        private String password;
        private String name;
        private String lastName;
        private String patronymic;
        private String email;
        private String phone;
        private Date birthDate;
        private boolean isBanned;
        private boolean enabled;

        public Builder() {
            id = DEFAULT_ID;
            role = DEFAULT_ROLE;
            login = DEFAULT_LOGIN;
            password = DEFAULT_PASSWORD;
            name = DEFAULT_NAME;
            lastName = DEFAULT_LAST_NAME;
            patronymic = DEFAULT_PATRONYMIC;
            email = DEFAULT_EMAIL;
            phone = DEFAULT_PHONE;
            birthDate = DEFAULT_BIRTH_DATE;
            isBanned = DEFAULT_IS_BANNED;
            enabled = DEFAULT_ENABLED;
        }

        public Builder(User user) {
            id = user.getId();
            role = user.getRole();
            login = user.getLogin();
            password = user.getPassword();
            name = user.getName();
            lastName = user.getLastName();
            patronymic = user.getPatronymic();
            email = user.getEmail();
            phone = user.getPhone();
            birthDate = user.getBirthDate();
            isBanned = user.isBanned();
            enabled = user.isEnabled();
        }

        public Builder(UserDataHolder userDataHolder) {
            id = userDataHolder.getId();
            role = userDataHolder.getRole();
            login = userDataHolder.getLogin();
            password = userDataHolder.getPassword();
            name = userDataHolder.getName();
            lastName = userDataHolder.getLastName();
            patronymic = userDataHolder.getPatronymic();
            email = userDataHolder.getEmail();
            phone = userDataHolder.getPhone();
            birthDate = DEFAULT_BIRTH_DATE;
            isBanned = DEFAULT_IS_BANNED;
            enabled = DEFAULT_ENABLED;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setRole(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setPatronymic(String patronymic) {
            this.patronymic = patronymic;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setBirthDate(Date birtDate) {
            this.birthDate = birtDate;
            return this;
        }

        public Builder setBanned(boolean banned) {
            this.isBanned = banned;
            return this;
        }

        public Builder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
