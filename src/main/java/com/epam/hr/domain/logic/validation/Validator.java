package com.epam.hr.domain.logic.validation;

public interface Validator<T> {
    boolean isValid(T item);
}
