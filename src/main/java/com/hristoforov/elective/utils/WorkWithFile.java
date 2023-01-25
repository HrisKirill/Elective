package com.hristoforov.elective.utils;

import com.hristoforov.elective.connection.DBCPDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final  class WorkWithFile {
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
