package com.hristoforov.elective.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * IncorrectDataFormatException if input values are entered incorrectly
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public class IncorrectDataFormatException extends Exception {
    private static final Logger LOGGER = LogManager.getLogger(IncorrectDataFormatException.class);

    public IncorrectDataFormatException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
