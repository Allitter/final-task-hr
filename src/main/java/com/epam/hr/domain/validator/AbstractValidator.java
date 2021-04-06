package com.epam.hr.domain.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The type Abstract validator.
 */
public abstract class AbstractValidator {
    private final Map<String, Pattern> patterns;

    /**
     * Instantiates a new Abstract validator.
     */
    protected AbstractValidator() {
        this.patterns = new HashMap<>();
    }

    /**
     * Null or empty check boolean.
     *
     * @param target the target
     * @return true if is not null or empty
     */
    protected boolean nullOrEmptyCheck(String target) {
        return target != null && !target.equals("");
    }

    /**
     * Regex check boolean.
     *
     * @param target the target
     * @param regex  the regex
     * @return true if is correct
     */
    protected boolean regexCheck(String target, String regex) {
        Pattern pattern = getPattern(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }

    /**
     * Filters fails to list.
     *
     * @param validations the validations
     * @return the list of filtered fails
     */
    public List<String> filterFails(Map<String, Boolean> validations) {
        return validations.entrySet().stream()
                .filter(validation -> !validation.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Pattern getPattern(String regex) {
        Pattern pattern;
        if (patterns.containsKey(regex)) {
            pattern = patterns.get(regex);
        } else {
            pattern = Pattern.compile(regex);
            patterns.put(regex, pattern);
        }

        return pattern;
    }
}
