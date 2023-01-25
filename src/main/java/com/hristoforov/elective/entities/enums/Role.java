package com.hristoforov.elective.entities.enums;


/**
 * Role enum.
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public enum Role {
    TEACHER("teacher"),
    STUDENT("student"),
    SYSADMIN("sysadmin");

    Role(String name) {
    }

    /**
     * Obtains the role by the name.
     *
     * @param name role value
     * @return the role assigned to this value
     */
    public static Role checkRole(String name) {
        for (Role r :
                Role.values()) {
            if (r.name().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
    }
}
