package com.epam.hr.domain.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hr.domain.validator.ValidationFieldNames.NAME;
import static com.epam.hr.domain.validator.ValidationFieldNames.TEXT;

/**
 * The type Resume validator.
 */
public class ResumeValidator extends AbstractValidator {
    private static final String NAME_REGEX = "[A-Za-zА-ЯЁа-яё_0-9 ]{3,15}";
    private static final String TEXT_REGEX = "(?s).{3,2048}";

    /**
     * Validate map.
     *
     * @param name resume name
     * @param text resume text
     * @return validation results
     */
    public Map<String, Boolean> validate(String name, String text) {
        Map<String, Boolean> result = new HashMap<>();
        result.put(NAME, validateName(name));
        result.put(TEXT, validateText(text));
        return result;
    }

    /**
     * Gets validation fails.
     *
     * @param name resume name
     * @param text resume text
     * @return the validation fails
     */
    public List<String> getValidationFails(String name, String text) {
        return filterFails(validate(name, text));
    }

    /**
     * Validate name
     *
     * @param name the name
     * @return true if correct
     */
    public boolean validateName(String name) {
        return nullOrEmptyCheck(name) && regexCheck(name, NAME_REGEX);
    }

    /**
     * Validate text boolean.
     *
     * @param text the text
     * @return true if correct
     */
    public boolean validateText(String text) {
        return nullOrEmptyCheck(text) && regexCheck(text, TEXT_REGEX);
    }
}
