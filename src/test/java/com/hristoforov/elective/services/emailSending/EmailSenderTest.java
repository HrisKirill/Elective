package com.hristoforov.elective.services.emailSending;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailSenderTest {

    @Test
    void sendEmailThrowTest() {
        assertThrows(RuntimeException.class, () -> new EmailSender(new Properties())
                .sendEmail(null, null, null));
    }
}