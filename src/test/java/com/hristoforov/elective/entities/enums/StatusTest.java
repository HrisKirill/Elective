package com.hristoforov.elective.entities.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @ParameterizedTest
    @CsvSource({"BLOCKED, blocked", "UNBLOCKED, unblocked"})
    void testGetStatus(String statusString, String value) {
        Status status = Status.valueOf(statusString);
        assertEquals(status, Status.checkStatus(value));
    }

    @Test
    void testGetStatusIfAbsent() {
        assertNull(Status.checkStatus("open"));
    }
}