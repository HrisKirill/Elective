package com.hristoforov.elective.services;

import javax.servlet.http.HttpSession;

import static com.hristoforov.elective.constants.HttpAttributes.*;
import static com.hristoforov.elective.constants.HttpAttributes.INCORRECT_USER_LAST_NAME;

public class SessionService {

    public static void removeErrorFromUserSignUpPage(HttpSession session) {
        session.removeAttribute(LOGIN_SPELLING_ERROR);
        session.removeAttribute(PASSWORD_SPELLING_ERROR);
        session.removeAttribute(PASSWORD_AGAIN_SPELLING_ERROR);
        session.removeAttribute(PASSWORDS_ARE_NOT_EQUALS_ERROR);
        session.removeAttribute(EMAIL_SPELLING_ERROR);
        session.removeAttribute(LAST_NAME_SPELLING_ERROR);
        session.removeAttribute(FIRST_NAME_SPELLING_ERROR);
        session.removeAttribute(EMAIL_EXISTS_ERROR);
        session.removeAttribute(LOGIN_EXISTS_ERROR);
        session.removeAttribute(CAPTCHA_ERROR);

        session.removeAttribute(INCORRECT_USER_LOGIN);
        session.removeAttribute(INCORRECT_USER_PASSWORD);
        session.removeAttribute(INCORRECT_USER_AGAIN_PASSWORD);
        session.removeAttribute(INCORRECT_USER_EMAIL);
        session.removeAttribute(INCORRECT_USER_FIRST_NAME);
        session.removeAttribute(INCORRECT_USER_LAST_NAME);
    }
}
