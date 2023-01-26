package com.hristoforov.elective.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DataBaseInteractionException as a wrapper for SQLException
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public class DataBaseInteractionException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(DataBaseInteractionException.class);

    public DataBaseInteractionException(Throwable cause) {
        super(cause);
        LOGGER.error(cause.getMessage());
    }

}
