package com.hristoforov.elective.services.validation;

import com.hristoforov.elective.exceptions.IncorrectDataFormatException;

import java.sql.Date;
import java.time.LocalDate;

import static com.hristoforov.elective.constants.ValidationConstants.*;

/**
 * ValidationService with various data validation check
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public class ValidationService {

    public static String loginCheck(String login) throws IncorrectDataFormatException {
        if (login == null || !login.matches(LOGIN_REGEX)) {
            throw new IncorrectDataFormatException("Login format is incorrect");
        }
        return login;
    }

    public static String passwordCheck(String password) throws IncorrectDataFormatException {
        if (password == null || !password.matches(PASSWORD_REGEX)) {
            throw new IncorrectDataFormatException("Password format is incorrect");
        }
        return password;
    }

    public static void passwordEqualityCheck(String password, String passwordAgain) throws IncorrectDataFormatException {
        if (password == null || !password.equals(passwordAgain)) {
            throw new IncorrectDataFormatException("Passwords do not match");
        }
    }

    public static String emailCheck(String email) throws IncorrectDataFormatException {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new IncorrectDataFormatException("Email format is incorrect");
        }
        return email;
    }

    public static String lastNameCheck(String lastName) throws IncorrectDataFormatException {
        if (lastName == null || !lastName.matches(NAME_REGEX)) {
            throw new IncorrectDataFormatException("Last name format is incorrect");
        }
        return lastName;
    }

    public static String firstNameCheck(String firstName) throws IncorrectDataFormatException {
        if (firstName == null || !firstName.matches(NAME_REGEX)) {
            throw new IncorrectDataFormatException("First name format is incorrect");
        }
        return firstName;
    }


    public static String titleCheck(String title) throws IncorrectDataFormatException {
        if (title == null || !title.matches(TITLE_REGEX)) {
            throw new IncorrectDataFormatException("Title format is incorrect");
        }
        return title;
    }

    public static int durationCheck(int duration) throws IncorrectDataFormatException {
        if (duration < 0) {
            throw new IncorrectDataFormatException("Duration format is incorrect");
        }
        return duration;
    }

    public static Date checkDateAfterNow(String date) throws IncorrectDataFormatException {
        if (Date.valueOf(LocalDate.now()).compareTo(Date.valueOf(date)) > 0) {
            throw new IncorrectDataFormatException("The entered date cannot be earlier than the current");
        }
        return Date.valueOf(date);
    }

    public static void checkBothDates(String startDate, String endDate) throws IncorrectDataFormatException {
        if (Date.valueOf(startDate).compareTo(Date.valueOf(endDate)) > 0) {
            throw new IncorrectDataFormatException("End date cannot be earlier than start date");
        }
    }

}
