package com.hristoforov.elective.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * AuthenticationException if user has problems with authentication
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public class AuthenticationException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationException.class);


    private final Type type;

    public AuthenticationException(Type type) {
        LOGGER.error("Error cause by " + type);
        this.type = type;
    }


    public Type getType() {
        return type;
    }

    public enum Type {
        PASS,
        LOGIN,
        BLOCKED
    }
}