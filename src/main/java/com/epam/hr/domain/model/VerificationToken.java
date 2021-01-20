package com.epam.hr.domain.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public final class VerificationToken extends Entity {
    private final long idUser;
    private final String code;
    private final Date expirationDate;

    public VerificationToken(long idUser, String code) {
        super(-1, true);
        this.idUser = idUser;
        this.code = code;
        this.expirationDate = Builder.calculateExpirationDate();
    }

    public VerificationToken(long id, long idUser, String code, Date expirationDate) {
        super(id, true);
        this.idUser = idUser;
        this.code = code;
        this.expirationDate = expirationDate;
    }

    public VerificationToken(VerificationToken.Builder builder) {
        super(builder);
        this.idUser = builder.idUser;
        this.code = builder.code;
        this.expirationDate = builder.expirationDate;
    }


    public long getId() {
        return id;
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
            this.expirationDate = calculateExpirationDate();
        }

        public Builder(VerificationToken verificationToken) {
            super(verificationToken);
            this.idUser = verificationToken.idUser;
            this.code = verificationToken.code;
            this.expirationDate = verificationToken.expirationDate;
        }

        // todo remove static
        private static Date calculateExpirationDate() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Timestamp(calendar.getTime().getTime()));
            calendar.add(Calendar.MINUTE, EXPIRATION_TIME_IN_MINUTES);
            return new Date(calendar.getTime().getTime());
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
            return null;
        }
    }
}
