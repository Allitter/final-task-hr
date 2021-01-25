package com.epam.hr.domain.validator;

import com.epam.hr.domain.model.User;
import com.epam.hr.domain.util.DateUtils;
import com.epam.hr.exception.UtilsException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.hr.domain.validator.ValidationFieldNames.*;

/**
 * The type User validator.
 */
public class UserValidator extends AbstractValidator {
    private static final String LOGIN_REGEX = "\\w{3,15}";
    private static final String PASSWORD_REGEX = "[_*$()%0-9A-Za-z]{8,32}";
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final String NAME_REGEX = "(^[А-ЯЁ][а-яё]{1,14}$)|(^[A-Z][a-z]{1,14}$)";
    private static final String EMAIL_REGEX = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final int EMAIL_MAX_LENGTH = 320;
    private static final String PHONE_REGEX = "[+][0-9]{7,14}";
    private static final String BIRTH_DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private static final String MIN_BIRTH_DATE = "1970-12-31";
    private static final String MAX_BIRTH_DATE = "2008-01-01";

    /**
     * Validate login and password.
     *
     * @param login    the login
     * @param password the password
     * @return true if valid
     */
    public boolean validateLoginAndPassword(String login, String password) {
        return validateLogin(login) && validatePassword(password);
    }

    /**
     * Gets validation fails for update.
     *
     * @param user the user
     * @return the validation fails for update
     */
    public List<String> getValidationFailsForUpdate(User user) {
        return filterFails(validateForUpdate(user));
    }

    /**
     * Gets validation fails for register.
     *
     * @param user the user
     * @return the validation fails for register
     */
    public List<String> getValidationFailsForRegister(User user) {
        return filterFails(validateForRegister(user));
    }

    /**
     * Validate for register.
     *
     * @param user the user
     * @return validation results
     */
    public Map<String, Boolean> validateForRegister(User user) {
        Map<String, Boolean> result = validateForUpdate(user);
        String password = user.getPassword();
        result.put(PASSWORD, validatePassword(password));

        return result;
    }

    /**
     * Validate for update.
     *
     * @param user the user
     * @return validation results
     */
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
        result.put(NAME, validateName(name));
        result.put(LAST_NAME, validateLastName(lastName));
        result.put(PATRONYMIC, validatePatronymic(patronymic));
        result.put(EMAIL, validateEmail(email));
        result.put(PHONE, validatePhone(phone));
        result.put(BIRTH_DATE, validateBirthDate(birthDate));
        return result;
    }

    /**
     * Validate login.
     *
     * @param login the login
     * @return true if valid
     */
    public boolean validateLogin(String login) {
        return nullOrEmptyCheck(login) && regexCheck(login, LOGIN_REGEX);
    }

    /**
     * Validate password.
     *
     * @param password the password
     * @return true if valid
     */
    public boolean validatePassword(String password) {
        return nullOrEmptyCheck(password)
                && regexCheck(password, PASSWORD_REGEX)
                && password.length() >= PASSWORD_MIN_LENGTH;
    }

    /**
     * Validate name.
     *
     * @param name the name
     * @return true if valid
     */
    public boolean validateName(String name) {
        return nullOrEmptyCheck(name)
                && regexCheck(name, NAME_REGEX);
    }

    /**
     * Validate last name.
     *
     * @param lastName the last name
     * @return true if valid
     */
    public boolean validateLastName(String lastName) {
        return nullOrEmptyCheck(lastName)
                && regexCheck(lastName, NAME_REGEX);
    }

    /**
     * Validate patronymic.
     *
     * @param patronymic the patronymic
     * @return true if valid
     */
    public boolean validatePatronymic(String patronymic) {
        return !nullOrEmptyCheck(patronymic)
                || regexCheck(patronymic, NAME_REGEX);
    }

    /**
     * Validate email.
     *
     * @param email the email
     * @return true if valid
     */
    public boolean validateEmail(String email) {
        return nullOrEmptyCheck(email)
                && regexCheck(email, EMAIL_REGEX)
                && email.length() <= EMAIL_MAX_LENGTH;
    }

    /**
     * Validate phone.
     *
     * @param phone the phone
     * @return true if valid
     */
    public boolean validatePhone(String phone) {
        return nullOrEmptyCheck(phone) && regexCheck(phone, PHONE_REGEX);
    }

    /**
     * Validate birth date.
     *
     * @param date the date
     * @return true if valid
     */
    public boolean validateBirthDate(String date) {
        if (!nullOrEmptyCheck(date) || !regexCheck(date, BIRTH_DATE_REGEX)) {
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
