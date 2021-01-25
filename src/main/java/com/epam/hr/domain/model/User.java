package com.epam.hr.domain.model;

import com.epam.hr.domain.util.DateUtils;

import java.io.Serializable;

public final class User extends Entity implements Serializable {
    public static final User DEFAULT = new Builder().build();
    private final Role role;
    private final String login;
    private final String password;
    private final String name;
    private final String lastName;
    private final String patronymic;
    private final String email;
    private final String phone;
    private final String birthDate;
    private final String avatarPath;
    private final boolean isBanned;
    private final boolean enabled;

    private User(User.Builder builder) {
        super(builder);
        this.role = builder.role;
        this.login = builder.login;
        this.password = builder.password;
        this.name = builder.name;
        this.lastName = builder.lastName;
        this.patronymic = builder.patronymic;
        this.email = builder.email;
        this.phone = builder.phone;
        this.birthDate = builder.birthDate;
        this.avatarPath = builder.avatarPath;
        this.isBanned = builder.isBanned;
        this.enabled = builder.enabled;
    }

    public Role getRole() {
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

    public String getAvatarPath() {
        return avatarPath;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public User changeEnabled(boolean enabled) {
        return new User.Builder(this).setEnabled(enabled).build(isValid);
    }

    public static class Builder extends Entity.Builder<User> {
        private static final Role DEFAULT_ROLE = Role.DEFAULT;
        private static final boolean DEFAULT_IS_BANNED = false;
        private static final boolean DEFAULT_ENABLED = true;

        private Role role;
        private String login;
        private String password;
        private String name;
        private String lastName;
        private String patronymic;
        private String email;
        private String phone;
        private String birthDate;
        private String avatarPath;
        private boolean isBanned;
        private boolean enabled;

        public Builder() {
            this(DEFAULT_ID);
        }

        public Builder(long id) {
            super(id);
            role = DEFAULT_ROLE;
            login = DEFAULT_STRING_VALUE;
            password = DEFAULT_STRING_VALUE;
            name = DEFAULT_STRING_VALUE;
            lastName = DEFAULT_STRING_VALUE;
            patronymic = DEFAULT_STRING_VALUE;
            email = DEFAULT_STRING_VALUE;
            phone = DEFAULT_STRING_VALUE;
            birthDate = DateUtils.currentDate().toString();
            avatarPath = DEFAULT_STRING_VALUE;
            isBanned = DEFAULT_IS_BANNED;
            enabled = DEFAULT_ENABLED;
        }

        public Builder(User user) {
            super(user);
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
            avatarPath = user.getAvatarPath();
        }

        public Builder setRole(Role role) {
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

        public Builder setAvatarPath(String avatarPath) {
            this.avatarPath = avatarPath;
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

        @Override
        public User build(boolean isValid) {
            this.isValid = isValid;
            return new User(this);
        }
    }

    public enum Role {
        ADMINISTRATOR,
        EMPLOYEE,
        JOB_SEEKER,
        DEFAULT
    }
}
