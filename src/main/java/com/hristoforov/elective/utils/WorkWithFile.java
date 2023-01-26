package com.hristoforov.elective.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * WorkWithFile to read file from resources
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public final class WorkWithFile {
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
            throw new RuntimeException(e);
        }
    }
}
