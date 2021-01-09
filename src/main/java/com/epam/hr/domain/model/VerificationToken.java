package com.epam.hr.domain.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    private final long id;
    private final long idUser;
    private final String code;
    private final Date expirationDate;

    public VerificationToken(long idUser, String code) {
        this.id = -1;

        this.idUser = idUser;
        this.code = code;
        this.expirationDate = calculateExpirationDate(EXPIRATION);
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

    private Date calculateExpirationDate(int expirationTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expirationTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }
}
