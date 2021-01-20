package com.epam.hr.domain.validator;

import com.epam.hr.domain.model.User;
import com.epam.hr.domain.util.DateUtils;
import com.epam.hr.exception.UtilsException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hr.domain.validator.ValidationFieldNames.*;

public class UserValidator extends AbstractValidator {
    private static final String LOGIN_REGEX = "\\w{3,15}";
    private static final String PASSWORD_REGEX = "[_*$()%0-9A-Za-z]{8,32}";
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final String NAME_REGEX = "(^[А-ЯЁ][а-яё]{1,14}$)|(^[A-Z][a-z]{1,14}$)";
    private static final String EMAIL_REGEX = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final int EMAIL_MAX_LENGTH = 320;
    private static final String PHONE_REGEX = "[+][0-9]{7,14}";
    private static final String BIRTH_DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private static final String MIN_BIRTH_DATE = "1950-01-01";
    private static final String MAX_BIRTH_DATE = "2007-12-31";

    public List<String> getValidationFailsForUpdate(User user) {
        return filterFails(validateForUpdate(user));
    }

    public List<String> getValidationFailsForRegister(User user) {
        return filterFails(validateForRegister(user));
    }

    public Map<String, Boolean> validateForRegister(User user) {
        Map<String, Boolean> result = validateForUpdate(user);
        String password = user.getPassword();
        result.put(PASSWORD, validatePassword(password));

        return result;
    }

    public Map<String, Boolean> validateForUpdate(User user) {
        String login = user.getLogin();
        String name = user.getName();
        String lastName = user.getLastName();
        String patronymic = user.getPatronymic();
        String email = user.getEmail();
        String phone = user.getPhone();
        String birthDate = user.getBirthDate();

        Map<String, Boolean> result = new HashMap<>();
        result.put(LOGIN, validateLogin(login));
        result.put(NAME ,validateName(name));
        result.put(LAST_NAME, validateLastName(lastName));
        result.put(PATRONYMIC, validatePatronymic(patronymic));
        result.put(EMAIL, validateEmail(email));
        result.put(PHONE, validatePhone(phone));
        result.put(BIRTH_DATE, validateBirthDate(birthDate));
        return result;
    }

    public boolean validateLogin(String login) {
        return nullOrEmptyAndRegexCheck(login, LOGIN_REGEX);
    }

    public boolean validatePassword(String password) {
        boolean isValid = nullOrEmptyAndRegexCheck(password, PASSWORD_REGEX);
        return isValid && password.length() >= PASSWORD_MIN_LENGTH;
    }

    public boolean validateName(String name) {
        return nullOrEmptyAndRegexCheck(name, NAME_REGEX);
    }

    public boolean validateLastName(String lastName) {
        return nullOrEmptyAndRegexCheck(lastName, NAME_REGEX);
    }

    public boolean validatePatronymic(String patronymic) {
        return nullOrEmptyAndRegexCheck(patronymic, NAME_REGEX);
    }

    public boolean validateEmail(String email) {
        boolean isValid = nullOrEmptyAndRegexCheck(email, EMAIL_REGEX);
        return isValid && email.length() <= EMAIL_MAX_LENGTH;
    }

    public boolean validatePhone(String phone) {
        return nullOrEmptyAndRegexCheck(phone, PHONE_REGEX);
    }

    public boolean validateBirthDate(String date) {
        boolean isValid = nullOrEmptyAndRegexCheck(date, BIRTH_DATE_REGEX);
        if (!isValid) {
            return false;
        }

        try {
            Date birthDate = DateUtils.tryParse(date);
            Date minBirthDate = DateUtils.tryParse(MIN_BIRTH_DATE);
            Date maxBirthDate = DateUtils.tryParse(MAX_BIRTH_DATE);

            return birthDate.after(minBirthDate) && birthDate.before(maxBirthDate);
        } catch (UtilsException e) {
            return false;
        }
    }
}
