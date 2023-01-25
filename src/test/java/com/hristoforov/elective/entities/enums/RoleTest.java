package com.hristoforov.elective.entities.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @ParameterizedTest
    @CsvSource({"TEACHER, teacher", "STUDENT, student", "SYSADMIN, sysadmin"})
    void testGetRole(String roleString, String value) {
        Role role = Role.valueOf(roleString);
        assertEquals(role, Role.checkRole(value));
    }

    @Test
    void testGetRoleIfAbsent() {
        assertNull(Role.checkRole("worker"));
    }
}