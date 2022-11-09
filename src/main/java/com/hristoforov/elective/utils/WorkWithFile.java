package com.hristoforov.elective.utils;

import com.hristoforov.elective.DBCPDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WorkWithFile {
    private static final Logger LOGGER = LogManager.getLogger(WorkWithFile.class);

    public static String readFromFile(String fileName) {
        try {
            Path path = new File(DBCPDataSource.class.getClassLoader()
                    .getResource(fileName).getFile()).toPath();

            LOGGER.info(new String(Files.readAllBytes(path)));
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
