package com.epam.hr.domain.model;

import java.util.Objects;

public class Resume {
    private final long id;
    private final long idUser;
    private final String name;
    private final String text;

    public Resume(long id, long idUser, String name, String text) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.text = text;
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

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", text='" + text + '\'' +
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
        Resume resume = (Resume) o;
        return id == resume.id
                && idUser == resume.idUser
                && Objects.equals(text, resume.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, text);
    }
}
