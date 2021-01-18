package com.epam.hr.exception;

public class UtilsException extends ServiceException {
    public UtilsException() {
    }

    public UtilsException(String message) {
        super(message);
    }

    public UtilsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilsException(Throwable cause) {
        super(cause);
    }
}
