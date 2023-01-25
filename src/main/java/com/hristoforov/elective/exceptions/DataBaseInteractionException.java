package com.hristoforov.elective.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataBaseInteractionException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(DataBaseInteractionException.class);

    public DataBaseInteractionException(Throwable cause) {
        super(cause);
        LOGGER.error(cause.getMessage());
    }

}
