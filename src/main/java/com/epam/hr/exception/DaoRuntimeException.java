package com.epam.hr.exception;

public class DaoRuntimeException extends RuntimeException {
    public DaoRuntimeException() {
    }

    public DaoRuntimeException(String message) {
        super(message);
    }

    public DaoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoRuntimeException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
