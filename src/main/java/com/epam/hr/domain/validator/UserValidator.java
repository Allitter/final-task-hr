package com.epam.hr.domain.validator;

import com.epam.hr.domain.model.UserDataHolder;
import com.epam.hr.domain.util.DateUtils;
import com.epam.hr.exception.UtilsException;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class UserValidator extends AbstractValidator {
    private static final String LOGIN = "login";
    private static final String LOGIN_REGEX = "^\\w{3,15}$";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_REGEX = "[_*$()%0-9A-Za-z]{8,32}";
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final String NAME = "name";
    private static final String NAME_REGEX = "(^[А-ЯЁ][а-яё]{1,14}$)|(^[A-Z][a-z]{1,14}$)";
    private static final String LAST_NAME = "lastName";
    private static final String PATRONYMIC = "patronymic";
    private static final String EMAIL = "email";
    private static final String EMAIL_REGEX = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final int EMAIL_MAX_LENGTH = 320;
    private static final String PHONE = "phone";
    private static final String PHONE_REGEX = "[+][0-9]{7,14}";
    private static final String BIRTH_DATE = "birthDate";
    private static final String BIRTH_DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private static final String MIN_BIRTH_DATE = "1950-01-01";
    private static final String MAX_BIRTH_DATE = "2007-12-31";

    public List<String> validateForRegister(UserDataHolder userDataHolder) {
        List<String> result = validateForUpdate(userDataHolder);
        String password = userDataHolder.getPassword();
        result.addAll(validatePassword(password));

        return result;
    }

    public List<String> validateForUpdate(UserDataHolder userDataHolder) {
        String login = userDataHolder.getLogin();
        String name = userDataHolder.getName();
        String lastName = userDataHolder.getLastName();
        String patronymic = userDataHolder.getPatronymic();
        String email = userDataHolder.getEmail();
        String phone = userDataHolder.getPhone();
        String birthDate = userDataHolder.getBirthDate();

        List<String> result = new LinkedList<>();
        result.addAll(validateLogin(login));
        result.addAll(validateName(name));
        result.addAll(validateLastName(lastName));
        result.addAll(validatePatronymic(patronymic));
        result.addAll(validateEmail(email));
        result.addAll(validatePhone(phone));
        result.addAll(validateBirthDate(birthDate));
        return result;
    }

    public List<String> validateLogin(String login) {
        return nullOrEmptyAndRegexCheck(login, LOGIN, LOGIN_REGEX);
    }

    public List<String> validatePassword(String password) {
        List<String> fails = nullOrEmptyAndRegexCheck(password, PASSWORD, PASSWORD_REGEX);
        if (fails.isEmpty() && password.length() < PASSWORD_MIN_LENGTH) {
            fails.add(PASSWORD + TOO_WEAK);
        }

        return fails;
    }

    public List<String> validateName(String name) {
        return nullOrEmptyAndRegexCheck(name, NAME, NAME_REGEX);
    }

    public List<String> validateLastName(String lastName) {
        return nullOrEmptyAndRegexCheck(lastName, LAST_NAME, NAME_REGEX);
    }

    public List<String> validatePatronymic(String patronymic) {
        List<String> fails = nullOrEmptyAndRegexCheck(patronymic, PATRONYMIC, NAME_REGEX);

        if (!fails.isEmpty()) {
            return Collections.emptyList();
        } else {
            if (!patronymic.matches(NAME_REGEX)) {
                return Collections.singletonList(PATRONYMIC + REGEX_FAIL);
            }
        }

        return Collections.emptyList();
    }

    public List<String> validateEmail(String email) {
        List<String> fails = nullOrEmptyAndRegexCheck(email, EMAIL, EMAIL_REGEX);
        if (fails.isEmpty() && email.length() > EMAIL_MAX_LENGTH) {
            fails.add(EMAIL + OUT_OF_BOUNDS);
        }

        return fails;
    }

    public List<String> validatePhone(String phone) {
        return nullOrEmptyAndRegexCheck(phone, PHONE, PHONE_REGEX);
    }

    public List<String> validateBirthDate(String date) {
        List<String> fails = nullOrEmptyAndRegexCheck(date, BIRTH_DATE, BIRTH_DATE_REGEX);
        if (!fails.isEmpty()) {
            return fails;
        }

        try {
            Date birthDate = DateUtils.tryParse(date);
            Date minBirthDate = DateUtils.tryParse(MIN_BIRTH_DATE);
            Date maxBirthDate = DateUtils.tryParse(MAX_BIRTH_DATE);

            if ( !birthDate.after(minBirthDate) || !birthDate.before(maxBirthDate)) {
                fails.add(BIRTH_DATE + OUT_OF_BOUNDS);
            }

            return fails;
        } catch (UtilsException e) {
            fails.add(BIRTH_DATE + PARSE_FAIL);
            return fails;
        }
    }
}
