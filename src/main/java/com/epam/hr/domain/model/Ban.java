package com.epam.hr.domain.model;

public final class Ban extends Entity {
    private final String reason;
    private final long idTarget;
    private final long idAdministrant;
    private final String dateTime;

    private Ban(Ban.Builder builder) {
        super(builder);
        this.reason = builder.reason;
        this.idTarget = builder.idTarget;
        this.idAdministrant = builder.idAdministrant;
        this.dateTime = builder.dateTime;
    }

    public String getReason() {
        return reason;
    }

    public long getIdTarget() {
        return idTarget;
    }

    public long getIdAdministrant() {
        return idAdministrant;
    }

    public String getDateTime() {
        return dateTime;
    }

    public static class Builder extends Entity.Builder<Ban> {
        private String reason;
        private long idTarget;
        private long idAdministrant;
        private String dateTime;

        public Builder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder setIdTarget(long idTarget) {
            this.idTarget = idTarget;
            return this;
        }

        public Builder setIdAdministrant(long idAdministrant) {
            this.idAdministrant = idAdministrant;
            return this;
        }

        public Builder setDateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder() {
            this(DEFAULT_ID);
        }

        public Builder(long id) {
            super(id);
        }

        public Builder(Ban ban) {
            super(ban);
        }

        @Override
        public Ban build(boolean isValid) {
            this.isValid = isValid;
            return new Ban(this);
        }
    }
}
