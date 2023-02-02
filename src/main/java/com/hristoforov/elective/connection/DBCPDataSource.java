package com.hristoforov.elective.connection;

import com.hristoforov.elective.controller.filters.AuthorizationFilter;
import com.hristoforov.elective.utils.WorkWithFile;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

import static com.hristoforov.elective.constants.ConnectionConstants.*;

/**
 * Abstract class DBCPDataSource to configure and obtain BasicDataSource.
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public abstract class DBCPDataSource {
    private static final Logger LOGGER = LogManager.getLogger(DBCPDataSource.class);
    private static DataSource dataSource;

    private static BasicDataSource getBDSConfig() {
        BasicDataSource dataSourceForConnection = new BasicDataSource();
        Properties properties = WorkWithFile.readPropertiesFromSource(CONNECTION_FILE);
        dataSourceForConnection.setDriverClassName(properties.getProperty(DRIVER));
        dataSourceForConnection.setUrl(properties.getProperty(URL));
        dataSourceForConnection.setUsername(properties.getProperty(USER));
        dataSourceForConnection.setPassword(properties.getProperty(PASSWORD));
        dataSourceForConnection.setMinIdle(5);
        dataSourceForConnection.setMaxIdle(1000);
        dataSourceForConnection.setMaxOpenPreparedStatements(100);

        return dataSourceForConnection;
    }

    /**
     * Execute initial db queries
     */
    public static void startDbScripts() {
        deleteCreateAndFillDb("queries/startDb/dropDB.sql");
        deleteCreateAndFillDb("queries/startDb/createSchema.sql");
        deleteCreateAndFillDb("queries/startDb/usingDb.sql");
        deleteCreateAndFillDb("queries/dropTables/dropUserTable.sql");
        deleteCreateAndFillDb("queries/startDb/createTableUser.sql");
        deleteCreateAndFillDb("queries/startDb/createTableCourse.sql");
        deleteCreateAndFillDb("queries/startDb/createTableUsersCourses.sql");
        deleteCreateAndFillDb("queries/startDb/createTableTopic.sql");
        deleteCreateAndFillDb("queries/startDb/createTableCoursesTopic.sql");
        deleteCreateAndFillDb("queries/fillingTables/fillUserTable.sql");
        deleteCreateAndFillDb("queries/fillingTables/fillCourseTable.sql");
        deleteCreateAndFillDb("queries/fillingTables/fillTopicTable.sql");
        deleteCreateAndFillDb("queries/fillingTables/fillUsersCoursesTable.sql");
        deleteCreateAndFillDb("queries/fillingTables/fillTableTopicCourse.sql");
    }


    private static void deleteCreateAndFillDb(String fileName) {
        String deleteCreateAndFillDb = WorkWithFile.readFromFile(fileName);
        try (Connection conn = getDataSource().getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate(deleteCreateAndFillDb);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtains configured DataSource
     *
     * @return singleton instance of BasicDataSource
     */
    public static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = getBDSConfig();
        }
        return dataSource;
    }
}
