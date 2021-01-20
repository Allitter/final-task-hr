package com.epam.hr.domain.model;

public final class Resume extends Entity {
    private final long idUser;
    private final String name;
    private final String text;

    // todo remove
    public Resume(long id, long idUser, String name, String text) {
        super(id, true);
        this.idUser = idUser;
        this.name = name;
        this.text = text;
    }

    private Resume(Resume.Builder builder) {
        super(builder);
        this.idUser = builder.idUser;
        this.name = builder.name;
        this.text = builder.text;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public long getIdUser() {
        return idUser;
    }

    public String getText() {
        return text;
    }

    public Resume changeName(String name) {
        return new Resume.Builder(this).setName(name).build();
    }

    public Resume changeText(String text) {
        return new Resume.Builder(this).setText(text).build();
    }

    public static class Builder extends Entity.Builder<Resume> {
        private final long idUser;
        private String name;
        private String text;

        public Builder(long idUser) {
            this(DEFAULT_ID, idUser);
        }

        public Builder(long id, long idUser) {
            super(id);
            this.idUser = idUser;
            this.name = DEFAULT_STRING_VALUE;
            this.text = DEFAULT_STRING_VALUE;
        }

        public Builder(Resume resume) {
            super(resume);
            idUser = resume.getIdUser();
            name = resume.getName();
            text = resume.getText();
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        @Override
        public Resume build(boolean isValid) {
            this.isValid = isValid;
            return new Resume(this);
        }
    }
}

