package com.hristoforov.elective.controller.context;

import com.hristoforov.elective.connection.DBCPDataSource;
import com.hristoforov.elective.dao.implementations.DAOFactory;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.services.emailSending.EmailSender;
import com.hristoforov.elective.services.pdfConverter.ConvertToPdf;
import com.hristoforov.elective.utils.WorkWithFile;
import lombok.Getter;

import javax.sql.DataSource;

import static com.hristoforov.elective.constants.ConnectionConstants.CONNECTION_FILE;

/**
 * AppContext  class. Contains everything to run the application
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
@Getter
public class AppContext {
    private static AppContext appContext;
    private final UserDao userDao;
    private final CourseDao courseDao;
    private final TopicDao topicDao;
    private final EmailSender emailSender;
    private final ConvertToPdf convertToPdf;


    private AppContext() {
        DataSource dataSource = DBCPDataSource.getDataSource();
        DBCPDataSource.startDbScripts();
        DAOFactory daoFactory = new DAOFactory(dataSource);
        userDao = daoFactory.getUserDao();
        courseDao = daoFactory.getCourseDao();
        topicDao = daoFactory.getTopicDao();
        emailSender = new EmailSender(WorkWithFile.readPropertiesFromSource(CONNECTION_FILE));
        convertToPdf = new ConvertToPdf();
    }

    public static void createAppContext() {
        appContext = new AppContext();
    }

    public static AppContext getAppContext() {
        return appContext;
    }
}
