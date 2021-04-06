package com.epam.hr.domain.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.epam.hr.test.util.TestUtils.fillString;
import static org.junit.jupiter.api.Assertions.*;

class ResumeValidatorTest {
    private static final int MAX_VALID_TEXT_LENGTH = 2048;
    private static final int MAX_VALID_NAME_LENGTH = 15;
    private static final int MIN_VALID_STRING_LENGTH = 3;
    private static final char LINE_CHARACTER = 'a';
    private static final String EMPTY_LINE = "";

    public static Object[][] validNames() {
        String lineWithMaxValidLength = fillString(MAX_VALID_NAME_LENGTH, LINE_CHARACTER);
        String lineWithMinValidLength = fillString(MIN_VALID_STRING_LENGTH, LINE_CHARACTER);

        return new Object[][]{
                {"simple name"},
                {lineWithMinValidLength},
                {lineWithMaxValidLength}
        };
    }

    public static Object[][] invalidNames() {
        String lineWithLengthGreaterThanMaxValid = fillString(MAX_VALID_NAME_LENGTH + 1, LINE_CHARACTER);
        String lineWithLengthSmallerThanMinValid = fillString(MIN_VALID_STRING_LENGTH - 1, LINE_CHARACTER);


        return new Object[][]{
                {EMPTY_LINE},
                {lineWithLengthGreaterThanMaxValid},
                {lineWithLengthSmallerThanMinValid}
        };
    }

    public static Object[][] validTexts() {
        String lineWithMaxValidLength = fillString(MAX_VALID_TEXT_LENGTH, LINE_CHARACTER);
        String lineWithMinValidLength = fillString(MIN_VALID_STRING_LENGTH, LINE_CHARACTER);

        return new Object[][]{
                {"some note"},
                {lineWithMinValidLength},
                {lineWithMaxValidLength}
        };
    }

    public static Object[][] invalidTexts() {
        String lineWithLengthGreaterThanMaxValid = fillString(MAX_VALID_TEXT_LENGTH + 1, LINE_CHARACTER);
        String lineWithLengthSmallerThanMinValid = fillString(MIN_VALID_STRING_LENGTH - 1, LINE_CHARACTER);

        return new Object[][]{
                {EMPTY_LINE},
                {lineWithLengthSmallerThanMinValid},
                {lineWithLengthGreaterThanMaxValid}
        };
    }

    @ParameterizedTest
    @MethodSource("validNames")
    void validateNameShouldReturnTrueWhenNameIsValid(String line) {
        ResumeValidator validator = new ResumeValidator();

        assertTrue(validator.validateName(line));
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void validateNameShouldReturnFalseWhenNameIsInvalid(String line) {
        ResumeValidator validator = new ResumeValidator();

        assertFalse(validator.validateName(line));
    }

    @ParameterizedTest
    @MethodSource("validTexts")
    void validateTextShouldReturnTrueWhenTextIsValid(String line) {
        ResumeValidator validator = new ResumeValidator();

        assertTrue(validator.validateText(line));
    }

    @ParameterizedTest
    @MethodSource("invalidTexts")
    void validateTextShouldReturnFalseWhenTextIsInvalid(String line) {
        ResumeValidator validator = new ResumeValidator();

        assertFalse(validator.validateText(line));
    }
}