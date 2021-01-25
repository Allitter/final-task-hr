package com.epam.hr.domain.model;

import java.io.Serializable;

/**
 * Super class for all programs' entities stored in database
 * all entities have id and isValid fields, id stored in database,
 * while isValid not, it represents the ability of the entity to be stored in database
 * this class also contains static nested {@link Builder} class that provides ability
 * for heirs to use Builder pattern. All entities are immutable
 */
public abstract class Entity implements Serializable {
    private final long id;
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

    /**
     * Builder for entity subclasses
     *
     * @param <T> entity subclass
     */
    public abstract static class Builder<T extends Entity> {
        protected static final int DEFAULT_ID = -1;
        protected static final String DEFAULT_STRING_VALUE = "";
        protected static final boolean DEFAULT_IS_VALID = false;

        /**
         * final as id should never change, even in builder
         */
        private final long id;
        protected boolean isValid;

        /**
         * To create new entity
         *
         * @param id entity id
         */
        protected Builder(long id) {
            this.id = id;
            this.isValid = DEFAULT_IS_VALID;
        }

        /**
         * To create new entity from existing one
         *
         * @param t entity subclass
         */
        protected Builder(T t) {
            this.id = t.getId();
            this.isValid = t.isValid();
        }

        /**
         * Default build method, create Entity with isValid field set to false
         *
         * @return Entity subclass
         */
        public T build() {
            return build(false);
        }

        /**
         * Default build method, create Entity with isValid field set to method parameter
         *
         * @param isValid represent if creating the valid entity
         * @return Entity subclass
         */
        public abstract T build(boolean isValid);
    }
}