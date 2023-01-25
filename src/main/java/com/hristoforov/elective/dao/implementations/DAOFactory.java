package com.hristoforov.elective.dao.implementations;

import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.dao.interfaces.UserDao;

import javax.sql.DataSource;

/**
 * DAO factory that creates DAOs
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public class DAOFactory {
    private UserDao userDao;
    private CourseDao courseDao;
    private TopicDao topicDao;
    private final DataSource dataSource;

    public DAOFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Obtains single instance of the UserDao.
     *
     * @return UserDao
     */
    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoImpl(dataSource);
        }
        return userDao;
    }

    /**
     * Obtains single instance of the CourseDao.
     *
     * @return CourseDao
     */
    public CourseDao getCourseDao() {
        if (courseDao == null) {
            courseDao = new CourseDaoImpl(dataSource);
        }
        return courseDao;
    }

    /**
     * Obtains single instance of the TopicDao.
     *
     * @return TopicDao
     */
    public TopicDao getTopicDao() {
        if (topicDao == null) {
            topicDao = new TopicDaoImpl(dataSource);
        }
        return topicDao;
    }

}
