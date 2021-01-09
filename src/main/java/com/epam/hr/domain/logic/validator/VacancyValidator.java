package com.epam.hr.domain.logic.validator;

import com.epam.hr.domain.model.Vacancy;

import java.util.LinkedList;
import java.util.List;

public class VacancyValidator extends AbstractValidator {
    private static final String NAME = "name";
    private static final String NAME_REGEX = "(.|\\n|\\r){3,30}";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String SHORT_DESCRIPTION_REGEX = "(.|\\n|\\r){3,140}";
    private static final String DESCRIPTION = "description";
    private static final String DESCRIPTION_REGEX = "(.|\\n|\\r){3,2048}";

    public List<String> validate(Vacancy vacancy) {
        String name = vacancy.getName();
        String shortDescription = vacancy.getShortDescription();
        String description = vacancy.getDescription();

        List<String> result = new LinkedList<>();
        result.addAll(validateName(name));
        result.addAll(validateShortDescription(shortDescription));
        result.addAll(validateDescription(description));
        return result;
    }

    public List<String> validateName(String name) {
        return nullOrEmptyAndRegexCheck(name, NAME, NAME_REGEX);
    }

    public List<String> validateShortDescription(String shortDescription) {
        return nullOrEmptyAndRegexCheck(shortDescription, SHORT_DESCRIPTION, SHORT_DESCRIPTION_REGEX);
    }

    public List<String> validateDescription(String description) {
        return nullOrEmptyAndRegexCheck(description, DESCRIPTION, DESCRIPTION_REGEX);
    }
}
