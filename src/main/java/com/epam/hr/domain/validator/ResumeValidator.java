package com.epam.hr.domain.validator;

import java.util.LinkedList;
import java.util.List;

public class ResumeValidator extends AbstractValidator {
    private static final String NAME = "name";
    private static final String NAME_REGEX = "[A-Za-zА-ЯЁа-яё_0-9]{3,15}";
    private static final String TEXT = "text";
    private static final String TEXT_REGEX = "(?s).{3,2048}";

    public List<String> validate(String name, String text) {
        List<String> result = new LinkedList<>();
        result.addAll(validateName(name));
        result.addAll(validateText(text));
        return result;
    }

    public List<String> validateName(String name) {
        return nullOrEmptyAndRegexCheck(name, NAME, NAME_REGEX);
    }

    public List<String> validateText(String text) {
        return nullOrEmptyAndRegexCheck(text, TEXT, TEXT_REGEX);
    }
}
