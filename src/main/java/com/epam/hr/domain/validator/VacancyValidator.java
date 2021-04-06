package com.epam.hr.domain.validator;

import com.epam.hr.domain.model.Vacancy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hr.domain.validator.ValidationFieldNames.*;

/**
 * The type Vacancy validator.
 */
public class VacancyValidator extends AbstractValidator {
    private static final String NAME_REGEX = "(?s).{3,120}";
    private static final String SHORT_DESCRIPTION_REGEX = "(?s).{3,140}";
    private static final String DESCRIPTION_REGEX = "(?s).{3,4096}";

    /**
     * Validate.
     *
     * @param vacancy the vacancy
     * @return validation results
     */
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

    /**
     * Gets validation fails.
     *
     * @param vacancy the vacancy
     * @return the validation fails
     */
    public List<String> getValidationFails(Vacancy vacancy) {
        return filterFails(validate(vacancy));
    }

    /**
     * Validate name.
     *
     * @param name the name
     * @return true if valid
     */
    public boolean validateName(String name) {
        return nullOrEmptyCheck(name) && regexCheck(name, NAME_REGEX);
    }

    /**
     * Validate short description.
     *
     * @param shortDescription the short description
     * @return true if valid
     */
    public boolean validateShortDescription(String shortDescription) {
        return nullOrEmptyCheck(shortDescription)
                && regexCheck(shortDescription, SHORT_DESCRIPTION_REGEX);
    }

    /**
     * Validate description.
     *
     * @param description the description
     * @return true if valid
     */
    public boolean validateDescription(String description) {
        return nullOrEmptyCheck(description)
                && regexCheck(description, DESCRIPTION_REGEX);
    }
}
