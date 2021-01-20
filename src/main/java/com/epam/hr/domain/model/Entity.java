package com.epam.hr.domain.model;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected final long id;
    protected final boolean isValid;

    protected Entity(long id, boolean isValid) {
        this.id = id;
        this.isValid = isValid;
    }

    protected Entity(Entity.Builder<? extends Entity> builder) {
        this.id = builder.id;
        this.isValid = builder.isValid;
    }

    public long getId() {
        return id;
    }

    public boolean isValid() {
        return isValid;
    }

    public abstract static class Builder<T extends Entity> {
        protected static final int DEFAULT_ID = -1;
        protected static final String DEFAULT_STRING_VALUE = "";
        protected static final boolean DEFAULT_IS_VALID = false;

        private final long id;
        protected boolean isValid;

        protected Builder(long id) {
            this.id = id;
            this.isValid = DEFAULT_IS_VALID;
        }

        protected Builder(T t) {
            this.id = t.getId();
            this.isValid = t.isValid();
        }

        public T build() {
            return build(false);
        }

        public abstract T build(boolean isValid);
    }
}
