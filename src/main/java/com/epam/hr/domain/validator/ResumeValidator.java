package com.epam.hr.domain.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.epam.hr.domain.validator.ValidationFieldNames.NAME;
import static com.epam.hr.domain.validator.ValidationFieldNames.TEXT;

public class ResumeValidator extends AbstractValidator {
    private static final String NAME_REGEX = "[A-Za-zА-ЯЁа-яё_0-9]{3,15}";
    private static final String TEXT_REGEX = "(?s).{3,2048}";

    public Map<String, Boolean> validate(String name, String text) {
        Map<String, Boolean> result = new HashMap<>();
        result.put(NAME, validateName(name));
        result.put(TEXT, validateText(text));
        return result;
    }

    public List<String> getValidationFails(String name, String text) {
        return filterFails(validate(name, text));
    }

    public boolean validateName(String name) {
        return nullOrEmptyAndRegexCheck(name, NAME_REGEX);
    }

    public boolean validateText(String text) {
        return nullOrEmptyAndRegexCheck(text, TEXT_REGEX);
    }
}
