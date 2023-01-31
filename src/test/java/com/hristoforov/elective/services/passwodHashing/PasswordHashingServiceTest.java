package com.hristoforov.elective.services.passwodHashing;

import org.junit.jupiter.api.Test;

import static com.hristoforov.elective.Constants.HASH_PASSWORD;
import static com.hristoforov.elective.Constants.PASSWORD_VALUE;
import static com.hristoforov.elective.constants.CommonConstants.SHA_256_ALGORITHM;
import static org.junit.jupiter.api.Assertions.*;

class PasswordHashingServiceTest {

    @Test
    void encodeTest() {
        assertEquals(HASH_PASSWORD,
                PasswordHashingService.encode(PASSWORD_VALUE, SHA_256_ALGORITHM));
    }

    @Test
    void encodeTestBadAlgorithm() {
        assertThrows(RuntimeException.class, () -> PasswordHashingService.encode(PASSWORD_VALUE, ""));
    }

    @Test
    void verifyTestTrue() {
        assertTrue(PasswordHashingService.verify(HASH_PASSWORD, PASSWORD_VALUE));
    }

    @Test
    void verifyTestFalse() {
        assertFalse(PasswordHashingService.verify("",
                PASSWORD_VALUE));
    }
}