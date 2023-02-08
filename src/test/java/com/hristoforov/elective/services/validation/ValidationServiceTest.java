package com.hristoforov.elective.services.validation;

import com.hristoforov.elective.exceptions.IncorrectDataFormatException;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static com.hristoforov.elective.Constants.getTestCourse;
import static com.hristoforov.elective.Constants.getTestUser;
import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceTest {
    private final static String WRONG_VALUE_FOR_TEST = "-123";
    private final static String WRONG_VALUE_FOR_TITLE = " -123 ";

    @Test
    void checkRightValidationTest() throws IncorrectDataFormatException {
        assertEquals(getTestUser().getLogin(), ValidationService.loginCheck(getTestUser().getLogin()));
        assertEquals(getTestUser().getPassword(), ValidationService.passwordCheck(getTestUser().getPassword()));
        assertEquals(getTestUser().getEmail(), ValidationService.emailCheck(getTestUser().getEmail()));
        assertEquals(getTestUser().getLastName(), ValidationService.lastNameCheck(getTestUser().getLastName()));
        assertEquals(getTestUser().getFirstName(), ValidationService.firstNameCheck(getTestUser().getFirstName()));
        assertEquals(getTestCourse().getTitle(), ValidationService.titleCheck(getTestCourse().getTitle()));
        assertEquals(getTestCourse().getDuration(), ValidationService.durationCheck(getTestCourse().getDuration()));
        assertEquals(Date.valueOf(LocalDate.now().plusDays(1)),
                ValidationService.checkDateAfterNow(String.valueOf(Date.valueOf(LocalDate.now().plusDays(1)))));
    }

    @Test
    void checkWrongValidationTest() {
        assertThrows(IncorrectDataFormatException.class, () -> ValidationService.loginCheck(WRONG_VALUE_FOR_TEST));
        assertThrows(IncorrectDataFormatException.class, () -> ValidationService.passwordCheck(WRONG_VALUE_FOR_TEST));
        assertThrows(IncorrectDataFormatException.class, () -> ValidationService.emailCheck(WRONG_VALUE_FOR_TEST));
        assertThrows(IncorrectDataFormatException.class, () -> ValidationService.lastNameCheck(WRONG_VALUE_FOR_TEST));
        assertThrows(IncorrectDataFormatException.class, () -> ValidationService.firstNameCheck(WRONG_VALUE_FOR_TEST));
        assertThrows(IncorrectDataFormatException.class, () -> ValidationService.titleCheck(WRONG_VALUE_FOR_TITLE));
        assertThrows(IncorrectDataFormatException.class, () -> ValidationService.durationCheck(Integer.parseInt(WRONG_VALUE_FOR_TEST)));
        assertThrows(IncorrectDataFormatException.class,
                () -> ValidationService.passwordEqualityCheck("", WRONG_VALUE_FOR_TEST));
        assertThrows(IncorrectDataFormatException.class,
                () -> ValidationService.checkDateAfterNow(String.valueOf(Date.valueOf(LocalDate.now().minusDays(1)))));
        assertThrows(IncorrectDataFormatException.class,
                () -> ValidationService.checkBothDates(String.valueOf(Date.valueOf(LocalDate.now().plusDays(1))),
                        String.valueOf(Date.valueOf(LocalDate.now().minusDays(1)))));


    }

}