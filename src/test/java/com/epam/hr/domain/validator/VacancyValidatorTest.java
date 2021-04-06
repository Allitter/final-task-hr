package com.epam.hr.domain.validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.epam.hr.test.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class VacancyValidatorTest {
    private static final char LINE_CHARACTER = 'a';
    private static final int MIN_VALID_STRING_LENGTH = 3;
    private static final int MAX_VALID_NAME_LENGTH = 120;
    private static final int MAX_VALID_SHORT_DESCRIPTION_LENGTH = 140;
    private static final int MAX_VALID_DESCRIPTION_LENGTH = 4096;

    public static Object[][] validNames() {
        String minValidName = fillString(MIN_VALID_STRING_LENGTH, LINE_CHARACTER);
        String maxValidName = fillString(MAX_VALID_NAME_LENGTH, LINE_CHARACTER);

        return new Object[][]{
                {minValidName},
                {maxValidName},
        };
    }

    public static Object[][] invalidNames() {
        String tooShortName = fillString(MIN_VALID_STRING_LENGTH - 1, LINE_CHARACTER);
        String tooLongName = fillString(MAX_VALID_NAME_LENGTH + 1, LINE_CHARACTER);

        return new Object[][]{
                {tooLongName},
                {tooShortName},
        };
    }

    public static Object[][] validShortDescriptions() {
        String minValidDescription = fillString(MIN_VALID_STRING_LENGTH, LINE_CHARACTER);
        String maxValidDescription = fillString(MAX_VALID_SHORT_DESCRIPTION_LENGTH, LINE_CHARACTER);

        return new Object[][]{
                {minValidDescription},
                {maxValidDescription},
        };
    }

    public static Object[][] invalidShortDescriptions() {
        String tooShortDescription = fillString(MIN_VALID_STRING_LENGTH - 1, LINE_CHARACTER);
        String tooLongDescription = fillString(MAX_VALID_SHORT_DESCRIPTION_LENGTH + 1, LINE_CHARACTER);

        return new Object[][]{
                {tooShortDescription},
                {tooLongDescription},
        };
    }

    public static Object[][] validDescriptions() {
        String minValidDescription = fillString(MIN_VALID_STRING_LENGTH, LINE_CHARACTER);
        String maxValidDescription = fillString(MAX_VALID_DESCRIPTION_LENGTH, LINE_CHARACTER);

        return new Object[][]{
                {minValidDescription},
                {maxValidDescription},
        };
    }

    public static Object[][] invalidDescriptions() {
        String tooShortDescription = fillString(MIN_VALID_STRING_LENGTH - 1, LINE_CHARACTER);
        String tooLongDescription = fillString(MAX_VALID_DESCRIPTION_LENGTH + 1, LINE_CHARACTER);

        return new Object[][]{
                {tooShortDescription},
                {tooLongDescription},
        };
    }


    @ParameterizedTest
    @MethodSource("validNames")
    void validateNameShouldReturnTrueWhenInputIsCorrect(String line) {
        VacancyValidator validator = new VacancyValidator();

        assertTrue(validator.validateName(line));
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void validateNameShouldReturnFalseWhenInputIsNotCorrect(String line) {
        VacancyValidator validator = new VacancyValidator();

        assertFalse(validator.validateName(line));
    }

    @ParameterizedTest
    @MethodSource("validShortDescriptions")
    void validateShortDescriptionShouldReturnTrueWhenInputIsCorrect(String line) {
        VacancyValidator validator = new VacancyValidator();

        assertTrue(validator.validateShortDescription(line));
    }

    @ParameterizedTest
    @MethodSource("invalidShortDescriptions")
    void validateShortDescriptionShouldReturnFalseWhenInputIsNotCorrect(String line) {
        VacancyValidator validator = new VacancyValidator();

        assertFalse(validator.validateShortDescription(line));
    }

    @ParameterizedTest
    @MethodSource("validDescriptions")
    void validateDescriptionShouldReturnTrueWhenInputIsCorrect(String line) {
        VacancyValidator validator = new VacancyValidator();

        assertTrue(validator.validateDescription(line));
    }

    @ParameterizedTest
    @MethodSource("invalidDescriptions")
    void validateDescriptionShouldReturnFalseWhenInputIsNotCorrect(String line) {
        VacancyValidator validator = new VacancyValidator();

        assertFalse(validator.validateDescription(line));
    }
}