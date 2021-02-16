package com.epam.hr.domain.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.epam.hr.test.util.TestUtils.fillString;
import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    private static final int MAX_VALID_LOGIN_LENGTH = 15;
    private static final int MAX_VALID_PASSWORD_LENGTH = 32;
    private static final int MIN_VALID_STRING_LENGTH = 3;
    private static final int MIN_VALID_PASSWORD_LENGTH = 8;
    private static final char LINE_CHARACTER = 'a';
    private static final String EMPTY_LINE = "";

    public static Object[][] validNames() {
        return new Object[][]{
                {"Владислав"},
                {"John"},
        };
    }

    public static Object[][] invalidNames() {
        return new Object[][]{
                {EMPTY_LINE},
                {"Jвлад"},
                {"john"},
                {"JoHn"},
        };
    }

    public static Object[][] invalidPatronymics() {
        return new Object[][]{
                {"Jвлад"},
                {"john"},
                {"JoHn"},
        };
    }

    public static Object[][] validLogins() {
        String maxValidLogin = fillString(MAX_VALID_LOGIN_LENGTH, LINE_CHARACTER);
        String minValidLogin = fillString(MIN_VALID_STRING_LENGTH, LINE_CHARACTER);

        return new Object[][]{
                {"login"},
                {maxValidLogin},
                {minValidLogin},
        };
    }

    public static Object[][] invalidLogins() {
        String tooLongLogin = fillString(MAX_VALID_LOGIN_LENGTH + 1, LINE_CHARACTER);
        String tooShortLogin = fillString(MIN_VALID_STRING_LENGTH - 1, LINE_CHARACTER);

        return new Object[][]{
                {EMPTY_LINE},
                {tooLongLogin},
                {tooShortLogin},
        };
    }

    public static Object[][] validPasswords() {
        String maxValidPassword = fillString(MAX_VALID_PASSWORD_LENGTH, LINE_CHARACTER);
        String minValidPassword = fillString(MIN_VALID_PASSWORD_LENGTH, LINE_CHARACTER);

        return new Object[][]{
                {"1Aa()%_*$"},
                {maxValidPassword},
                {minValidPassword},
        };
    }

    public static Object[][] invalidPasswords() {
        String tooLongPassword = fillString(MAX_VALID_PASSWORD_LENGTH + 1, LINE_CHARACTER);
        String tooShortPassword = fillString(MIN_VALID_PASSWORD_LENGTH - 1, LINE_CHARACTER);

        return new Object[][]{
                {EMPTY_LINE},
                {tooLongPassword},
                {tooShortPassword},
        };
    }

    public static Object[][] validEmails() {
        return new Object[][]{
                {"example@gmail.com"},
        };
    }

    public static Object[][] invalidEmails() {
        String randomText = "aaaa";
        return new Object[][]{
                {EMPTY_LINE},
                {randomText},
        };
    }

    public static Object[][] validPhones() {
        String phoneWithMinLength = "+1234567";
        String phoneWithMaxLength = "+12345678912345";

        return new Object[][]{
                {phoneWithMinLength},
                {phoneWithMaxLength}
        };
    }

    public static Object[][] invalidPhones() {
        String tooShortPhone = "+123456";
        String tooLongPhone = "+123456789123456";
        String randomText = "aaaa";
        return new Object[][]{
                {tooShortPhone},
                {tooLongPhone},
                {randomText}
        };
    }

    public static Object[][] validBirthDates() {
        String minValidDate = "1971-01-01";
        String maxValidDate = "2007-12-31";

        return new Object[][]{
                {minValidDate},
                {maxValidDate}
        };
    }

    public static Object[][] invalidBirthDates() {
        String tooOld= "1970-12-31";
        String tooYong = "2008-01-01";

        return new Object[][]{
                {tooOld},
                {tooYong}
        };
    }

    @ParameterizedTest
    @MethodSource("validLogins")
    void validateLoginShouldReturnTrueWhenInputIsValid(String line) {
        UserValidator validator = new UserValidator();

        assertTrue(validator.validateLogin(line));
    }

    @ParameterizedTest
    @MethodSource("invalidLogins")
    void validateLoginShouldReturnFalseWhenInputIsNotValid(String line) {
        UserValidator validator = new UserValidator();

        assertFalse(validator.validateLogin(line));
    }

    @ParameterizedTest
    @MethodSource("validPasswords")
    void validatePasswordShouldReturnTrueWhenInputIsValid(String line) {
        UserValidator validator = new UserValidator();

        assertTrue(validator.validatePassword(line));
    }

    @ParameterizedTest
    @MethodSource("invalidPasswords")
    void validatePasswordShouldReturnFalseWhenInputIsNotValid(String line) {
        UserValidator validator = new UserValidator();

        assertFalse(validator.validatePassword(line));
    }

    @ParameterizedTest
    @MethodSource("validNames")
    void validateNameShouldReturnTrueWhenInputIsValid(String line) {
        UserValidator validator = new UserValidator();

        assertTrue(validator.validateName(line));
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void validateNameShouldReturnFalseWhenInputIsNotValid(String line) {
        UserValidator validator = new UserValidator();

        assertFalse(validator.validateName(line));
    }

    @ParameterizedTest
    @MethodSource("validNames")
    void validateLastNameShouldReturnTrueWhenInputIsValid(String line) {
        UserValidator validator = new UserValidator();

        assertTrue(validator.validateLastName(line));
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void validateLastNameShouldReturnFalseWhenInputIsNotValid(String line) {
        UserValidator validator = new UserValidator();

        assertFalse(validator.validateLastName(line));
    }

    @ParameterizedTest
    @MethodSource("validNames")
    void validatePatronymicShouldReturnTrueWhenInputIsValid(String line) {
        UserValidator validator = new UserValidator();

        assertTrue(validator.validatePatronymic(line));
    }

    @ParameterizedTest
    @MethodSource("invalidPatronymics")
    void validatePatronymicShouldReturnFalseWhenInputIsNotValid(String line) {
        UserValidator validator = new UserValidator();

        assertFalse(validator.validatePatronymic(line));
    }

    @ParameterizedTest
    @MethodSource("validEmails")
    void validateEmailShouldReturnTrueWhenInputIsValid(String line) {
        UserValidator validator = new UserValidator();

        assertTrue(validator.validateEmail(line));
    }

    @ParameterizedTest
    @MethodSource("invalidEmails")
    void validateEmailShouldReturnFalseWhenInputIsNotValid(String line) {
        UserValidator validator = new UserValidator();

        assertFalse(validator.validateEmail(line));
    }

    @ParameterizedTest
    @MethodSource("validPhones")
    void validatePhoneShouldReturnTrueWhenInputIsValid(String line) {
        UserValidator validator = new UserValidator();

        assertTrue(validator.validatePhone(line));
    }

    @ParameterizedTest
    @MethodSource("invalidPhones")
    void validatePhoneShouldReturnFalseWhenInputIsNotValid(String line) {
        UserValidator validator = new UserValidator();

        assertFalse(validator.validatePhone(line));
    }

    @ParameterizedTest
    @MethodSource("validBirthDates")
    void validateBirthDateShouldReturnTrueWhenInputIsValid(String line) {
        UserValidator validator = new UserValidator();

        assertTrue(validator.validateBirthDate(line));

    }

    @ParameterizedTest
    @MethodSource("invalidBirthDates")
    void validateBirthDateShouldReturnFalseWhenInputIsNotValid(String line) {
        UserValidator validator = new UserValidator();

        assertFalse(validator.validateBirthDate(line));
    }
}