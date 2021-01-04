package com.epam.hr.domain.logic.validation;

import com.epam.hr.domain.model.Vacancy;
import org.apache.taglibs.standard.lang.jstl.UnaryMinusOperator;

public class VacancyValidator implements Validator<Vacancy> {
    public static final int MAX_DESCRIPTION_LENGTH = 2048;
    public static final int MAX_SHORT_DESCRIPTION_LENGTH = 140;
    public static final int MAX_NAME_LENGTH = 140;

    @Override
    public boolean isValid(Vacancy vacancy) {
        String name = vacancy.getName();
        String shortDescription = vacancy.getShortDescription();
        String description = vacancy.getDescription();

        return isNameValid(name)
                && isShortDescriptionValid(shortDescription)
                && isDescriptionValid(description);
    }

    private boolean isDescriptionValid(String description) {
        return description != null && description.length() <= MAX_DESCRIPTION_LENGTH;
    }

    private boolean isShortDescriptionValid(String shortDescription) {
        return shortDescription != null && shortDescription.length() <= MAX_SHORT_DESCRIPTION_LENGTH;
    }

    private boolean isNameValid(String name) {
        return name != null && name.length() <= MAX_NAME_LENGTH;
    }
}
