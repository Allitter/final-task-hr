package com.epam.hr.domain.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class VerificationToken {
    private static final int EXPIRATION_TIME_IN_MINUTES = 60 * 24;

    private final long id;
    private final long idUser;
    private final String code;
    private final Date expirationDate;

    public VerificationToken(long idUser, String code) {
        this.id = -1;

        this.idUser = idUser;
        this.code = code;
        this.expirationDate = calculateExpirationDate();
    }

    public VerificationToken(long id, long idUser, String code, Date expirationDate) {
        this.id = id;
        this.idUser = idUser;
        this.code = code;
        this.expirationDate = expirationDate;
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

    private Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME_IN_MINUTES);
        return new Date(calendar.getTime().getTime());
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", code='" + code + '\'' +
                ", expirationDate=" + expirationDate +
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
        VerificationToken token = (VerificationToken) o;
        return id == token.id
                && idUser == token.idUser
                && Objects.equals(code, token.code)
                && Objects.equals(expirationDate, token.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, code, expirationDate);
    }
}
