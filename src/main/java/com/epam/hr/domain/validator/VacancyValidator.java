package com.epam.hr.domain.validator;

import com.epam.hr.domain.model.Vacancy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hr.domain.validator.ValidationFieldNames.*;

public class VacancyValidator extends AbstractValidator {
    private static final String NAME_REGEX = "(?s).{3,30}";
    private static final String SHORT_DESCRIPTION_REGEX = "(?s).{3,140}";
    private static final String DESCRIPTION_REGEX = "(?s).{3,2048}";

    public Map<String, Boolean> validate(Vacancy vacancy) {
        String name = vacancy.getName();
        String shortDescription = vacancy.getShortDescription();
        String description = vacancy.getDescription();

        Map<String, Boolean> result = new HashMap<>();
        result.put(NAME, validateName(name));
        result.put(SHORT_DESCRIPTION, validateShortDescription(shortDescription));
        result.put(DESCRIPTION, validateDescription(description));
        return result;
    }

    public List<String> getValidationFails(Vacancy vacancy) {
        return filterFails(validate(vacancy));
    }

    public boolean validateName(String name) {
        return nullOrEmptyAndRegexCheck(name, NAME_REGEX);
    }

    public boolean validateShortDescription(String shortDescription) {
        return nullOrEmptyAndRegexCheck(shortDescription, SHORT_DESCRIPTION_REGEX);
    }

    public boolean validateDescription(String description) {
        return nullOrEmptyAndRegexCheck(description, DESCRIPTION_REGEX);
    }
}
