package com.hristoforov.elective.utils;

import com.hristoforov.elective.constants.CaptchaParams;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class VerifyRecaptchaTest {

    private final String[] wrongConnection = {"https://123/",
            "6LfkMc8eAAAAACus0Q6jTtpXWFZd3Vfcb9X7SQwC",
            "POST",
            "en-US,en;q=0.5"};

    @ParameterizedTest
    @ValueSource(strings = {"kirill", "7", "qwerty", "true"})
    void testVerifyRecaptcha(String reCaptcha) {
        assertDoesNotThrow(() -> new VerifyRecaptcha(CaptchaParams.URL,
                CaptchaParams.SECRET_KEY,
                CaptchaParams.METHOD,
                CaptchaParams.ACCEPT_LANGUAGE).verify(reCaptcha));
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", ""})
    void testVerifyNull(String reCaptcha) {
        assertDoesNotThrow(() -> new VerifyRecaptcha(CaptchaParams.URL,
                CaptchaParams.SECRET_KEY,
                CaptchaParams.METHOD,
                CaptchaParams.ACCEPT_LANGUAGE).verify(reCaptcha));
    }

    @ParameterizedTest
    @ValueSource(strings = {"kirill", "7", "qwerty", "true"})
    void testNoConnectionToRecaptcha(String reCaptcha) {
        assertThrows(RuntimeException.class, () -> new VerifyRecaptcha(wrongConnection[0],
                wrongConnection[1],
                wrongConnection[2],
                wrongConnection[3]).verify(reCaptcha));
    }
}