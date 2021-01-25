package com.epam.hr.domain.model;

public final class Vacancy extends Entity {
    private final String name;
    private final String shortDescription;
    private final String description;
    private final boolean closed;

    private Vacancy(Vacancy.Builder builder) {
        super(builder);
        this.name = builder.name;
        this.shortDescription = builder.shortDescription;
        this.description = builder.description;
        this.closed = builder.closed;
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

    public boolean isClosed() {
        return closed;
    }

    public Vacancy changeClosed(boolean closed) {
        return new Vacancy.Builder(this).setClosed(closed).build(isValid);
    }

    public static class Builder extends Entity.Builder<Vacancy> {
        private String name;
        private String shortDescription;
        private String description;
        private boolean closed;

        public Builder() {
            this(DEFAULT_ID);
        }

        public Builder(long id) {
            super(id);
            name = DEFAULT_STRING_VALUE;
            shortDescription = DEFAULT_STRING_VALUE;
            description = DEFAULT_STRING_VALUE;
            closed = false;
        }

        public Builder(Vacancy vacancy) {
            super(vacancy);
            this.name = vacancy.name;
            this.shortDescription = vacancy.shortDescription;
            this.description = vacancy.description;
            this.closed = vacancy.closed;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setClosed(boolean closed) {
            this.closed = closed;
            return this;
        }

        @Override
        public Vacancy build(boolean isValid) {
            this.isValid = isValid;
            return new Vacancy(this);
        }
    }
}
