package com.epam.hr.domain.model;

import java.util.Objects;

public class UserDataHolder {
    private final long id;
    private final UserRole role;
    private final String login;
    private final String password;
    private final String name;
    private final String lastName;
    private final String patronymic;
    private final String email;
    private final String phone;
    private final String birthDate;

    private UserDataHolder(UserDataHolder.Builder builder) {
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

    public String getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
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
        UserDataHolder userDataHolder = (UserDataHolder) o;
        return id == userDataHolder.id
                && role == userDataHolder.role
                && Objects.equals(login, userDataHolder.login)
                && Objects.equals(name, userDataHolder.name)
                && Objects.equals(lastName, userDataHolder.lastName)
                && Objects.equals(patronymic, userDataHolder.patronymic)
                && Objects.equals(email, userDataHolder.email)
                && Objects.equals(phone, userDataHolder.phone)
                && Objects.equals(birthDate, userDataHolder.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, login, name, lastName,
                patronymic, email, phone, birthDate);
    }


    public static class Builder {
        private long id;
        private UserRole role;
        private String login;
        private String password;
        private String name;
        private String lastName;
        private String patronymic;
        private String email;
        private String phone;
        private String birthDate;

        public Builder() {
            this(User.DEFAULT);
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
            birthDate = user.getBirthDate().toString();
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

        public Builder setBirthDate(String birtDate) {
            this.birthDate = birtDate;
            return this;
        }

        public UserDataHolder build() {
            return new UserDataHolder(this);
        }
    }

}
