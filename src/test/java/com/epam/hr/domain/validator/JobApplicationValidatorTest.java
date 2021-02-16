package com.epam.hr.domain.validator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static com.epam.hr.test.util.TestUtils.fillString;
import static org.junit.jupiter.api.Assertions.*;


class JobApplicationValidatorTest {
    private static final int MAX_VALID_LENGTH = 1024;
    private static final int MIN_VALID_LENGTH = 3;
    private static final char LINE_CHARACTER = 'a';
    private static final String EMPTY_LINE = "";

    public static Object[][] validNotes() {
        String lineWithMaxValidLength = fillString(MAX_VALID_LENGTH, LINE_CHARACTER);
        String lineWithMinValidLength = fillString(MIN_VALID_LENGTH, LINE_CHARACTER);

        return new Object[][]{
                {"some note"},
                {lineWithMinValidLength},
                {lineWithMaxValidLength}
        };
    }

    public static Object[][] invalidNotes() {
        String lineWithLengthGreaterThanMaxValid = fillString(MAX_VALID_LENGTH + 1, LINE_CHARACTER);
        String lineWithLengthSmallerThanMinValid = fillString(MIN_VALID_LENGTH - 1, LINE_CHARACTER);

        return new Object[][]{
                {EMPTY_LINE},
                {lineWithLengthSmallerThanMinValid},
                {lineWithLengthGreaterThanMaxValid}
        };
    }


    @ParameterizedTest
    @MethodSource("validNotes")
    void validatePreliminaryInterviewNoteShouldReturnTrueWhenNoteIsCorrect(String line) {
        JobApplicationValidator validator = new JobApplicationValidator();

        assertTrue(validator.validatePreliminaryInterviewNote(line));
    }

    @ParameterizedTest
    @MethodSource("invalidNotes")
    void validatePreliminaryInterviewNoteShouldReturnFalseWhenNoteIsIncorrect(String line) {
        JobApplicationValidator validator = new JobApplicationValidator();

        assertFalse(validator.validatePreliminaryInterviewNote(line));
    }

    @ParameterizedTest
    @MethodSource("validNotes")
    void validateTechnicalInterviewNoteShouldReturnTrueWhenNoteIsCorrect(String line) {
        JobApplicationValidator validator = new JobApplicationValidator();

        assertTrue(validator.validateTechnicalInterviewNote(line));
    }

    @ParameterizedTest
    @MethodSource("invalidNotes")
    void validateTechnicalInterviewNoteShouldReturnFalseWhenNoteIsIncorrect(String line) {
        JobApplicationValidator validator = new JobApplicationValidator();

        assertFalse(validator.validateTechnicalInterviewNote(line));
    }
}