package com.epam.hr.exception;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ValidationException extends ServiceException {
    private final List<String> validationFails;

    public ValidationException(List<String> validationFails) {
        this.validationFails = Collections.unmodifiableList(validationFails);
    }

    public ValidationException(String... fails) {
        this.validationFails = Arrays.asList(fails);
    }

    public List<String> getValidationFails() {
        return validationFails;
    }
}
