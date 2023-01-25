package com.hristoforov.elective.controller.context;

import com.hristoforov.elective.connection.DBCPDataSource;
import com.hristoforov.elective.dao.implementations.DAOFactory;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.dao.interfaces.UserDao;
import lombok.Getter;

import javax.sql.DataSource;

@Getter
public class AppContext {
    private static AppContext appContext;
    private final UserDao userDao;
    private final CourseDao courseDao;
    private final TopicDao topicDao;


    private AppContext() {
        DataSource dataSource = DBCPDataSource.getDataSource();
        DBCPDataSource.startDbScripts();
        DAOFactory daoFactory = new DAOFactory(dataSource);
        userDao = daoFactory.getUserDao();
        courseDao = daoFactory.getCourseDao();
        topicDao = daoFactory.getTopicDao();
    }

    public static void createAppContext() {
        appContext = new AppContext();
    }

    public static AppContext getAppContext() {
        return appContext;
    }
}
