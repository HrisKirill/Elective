package com.hristoforov.elective;

import com.hristoforov.elective.utils.WorkWithFile;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

public abstract class DBCPDataSource {
    private static final BasicDataSource dataSourceForConnection = new BasicDataSource();

    static {
        initDBConnection();
        deleteCreateAndFillDb("startDb/dropDB.sql");
        deleteCreateAndFillDb("startDb/createSchema.sql");
        deleteCreateAndFillDb("startDb/usingDb.sql");
        deleteCreateAndFillDb("startDb/createUser.sql");
        deleteCreateAndFillDb("startDb/createCourse.sql");
        deleteCreateAndFillDb("startDb/createUsersCourses.sql");
        deleteCreateAndFillDb("startDb/createTopic.sql");
        deleteCreateAndFillDb("startDb/createCoursesTopic.sql");
    }

    private static void initDBConnection() {
        try (InputStream inputStream = Objects.requireNonNull(Thread.currentThread().
                getContextClassLoader().getResourceAsStream("database.properties"))) {
            Properties properties = new Properties();
            properties.load(inputStream);
            Class.forName(properties.getProperty("driver"));
            dataSourceForConnection.setUrl(properties.getProperty("url"));
            dataSourceForConnection.setUsername(properties.getProperty("user"));
            dataSourceForConnection.setPassword(properties.getProperty("password"));
            dataSourceForConnection.setMinIdle(5);
            dataSourceForConnection.setMaxIdle(1000);
            dataSourceForConnection.setMaxOpenPreparedStatements(100);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection createConnection() {
        try {
            return dataSourceForConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static void deleteCreateAndFillDb(String fileName) {
        String createTable = WorkWithFile.readFromFile(fileName);
        try (Connection conn = createConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
