package com.hristoforov.elective.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * WorkWithFile to read file from resources
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public final class WorkWithFile {
    private static final Logger LOGGER = LogManager.getLogger(WorkWithFile.class);


    /**
     * Read data from file
     *
     * @param fileName file name
     * @return data in file
     */
    public static String readFromFile(String fileName) {
        try {
            Path path = new File(WorkWithFile.class.getClassLoader()
                    .getResource(fileName).getFile()).toPath();

            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Read properties from source file
     *
     * @param propertyFile file name
     * @return properties
     */
    public static Properties readPropertiesFromSource(String propertyFile) {
        Properties properties = new Properties();
        try (InputStream resource = WorkWithFile.class.getClassLoader().getResourceAsStream(propertyFile)) {
            properties.load(resource);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return properties;
    }

}
