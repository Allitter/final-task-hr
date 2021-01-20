package com.epam.hr.domain.validator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractValidator {

    protected boolean nullOrEmptyAndRegexCheck(String target, String regex) {
        return target != null
                && !target.equals("")
                && target.matches(regex);
    }

    public List<String> filterFails(Map<String, Boolean> validations) {
        return validations.entrySet().stream()
                .filter(validation -> !validation.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
