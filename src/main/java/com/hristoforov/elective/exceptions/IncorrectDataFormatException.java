package com.hristoforov.elective.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IncorrectDataFormatException extends Exception{
    private static final Logger LOGGER = LogManager.getLogger(IncorrectDataFormatException.class);

    public IncorrectDataFormatException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
