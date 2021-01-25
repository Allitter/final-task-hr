package com.epam.hr.domain.model;

import com.epam.hr.domain.util.DateUtils;

import java.util.Date;

public final class VerificationToken extends Entity {
    private final long idUser;
    private final String code;
    private final Date expirationDate;

    private VerificationToken(VerificationToken.Builder builder) {
        super(builder);
        this.idUser = builder.idUser;
        this.code = builder.code;
        this.expirationDate = builder.expirationDate;
    }

    public long getIdUser() {
        return idUser;
    }

    public String getCode() {
        return code;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public static class Builder extends Entity.Builder<VerificationToken> {
        private static final int EXPIRATION_TIME_IN_MINUTES = 60 * 24;

        private final long idUser;
        private String code;
        private Date expirationDate;

        public Builder(long idUser, String code) {
            this(DEFAULT_ID, idUser, code);
        }

        public Builder(long id, long idUser, String code) {
            super(id);
            this.idUser = idUser;
            this.code = code;
            this.expirationDate = DateUtils.calculateOffsetDateInMinutes(EXPIRATION_TIME_IN_MINUTES);
        }

        public Builder(VerificationToken verificationToken) {
            super(verificationToken);
            this.idUser = verificationToken.idUser;
            this.code = verificationToken.code;
            this.expirationDate = verificationToken.expirationDate;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Builder setExpirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        @Override
        public VerificationToken build(boolean isValid) {
            this.isValid = isValid;
            return new VerificationToken(this);
        }
    }
}
