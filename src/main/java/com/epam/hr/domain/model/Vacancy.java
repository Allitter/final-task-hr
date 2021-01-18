package com.epam.hr.domain.model;

import java.util.Objects;

public class Vacancy {
    private final long id;
    private final String name;
    private final String shortDescription;
    private final String description;

    public Vacancy(long id, String name, String shortDescription, String description) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
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
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id && Objects.equals(name, vacancy.name)
                && Objects.equals(shortDescription, vacancy.shortDescription)
                && Objects.equals(description, vacancy.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortDescription, description);
    }
}
