package com.hristoforov.elective.dao.implementations;

import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.topic.Topic;
import com.hristoforov.elective.exceptions.DataBaseInteractionException;
import com.hristoforov.elective.utils.WorkWithFile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.hristoforov.elective.constants.CRA_QueriesFiles.*;
import static com.hristoforov.elective.constants.DBAttributes.TOPIC_ID;
import static com.hristoforov.elective.constants.DBAttributes.TOPIC_TITLE;

/**
 * Topic DAO class for db. Match tables 'topic' and 'courses_topics' in database.
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public class TopicDaoImpl implements TopicDao {

    private final DataSource dataSource;

    public TopicDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Insert topic into db
     *
     * @param entity - concrete topic
     * @throws DataBaseInteractionException
     */
    @Override
    public void create(Topic entity) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(WorkWithFile.readFromFile(CREATE_TOPIC))) {
            try {
                conn.setAutoCommit(false);
                ps.setString(1, entity.getTitle());
                ps.execute();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
    }

    /**
     * Insert into table 'courses_topics' this pair
     *
     * @param topic  - topic
     * @param course - course
     * @throws DataBaseInteractionException
     */
    @Override
    public void createTopicCourse(Topic topic, Course course) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(WorkWithFile.readFromFile(CREATE_TOPIC_COURSE))) {
            try {
                conn.setAutoCommit(false);
                ps.setLong(1, topic.getId());
                ps.setLong(2, course.getId());
                ps.execute();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
    }

    /**
     * Update topic in db
     *
     * @param entity -  concrete topic
     * @throws DataBaseInteractionException
     */
    @Override
    public void update(Topic entity) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile.readFromFile(UPDATE_TOPIC))) {
            try {
                conn.setAutoCommit(false);
                preparedStatement.setString(1, entity.getTitle());
                preparedStatement.setLong(2, entity.getId());

                preparedStatement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
    }

    /**
     * Remove topic by id from db
     *
     * @param id - id of topic
     * @throws DataBaseInteractionException
     */
    @Override
    public void remove(Long id) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile.readFromFile(REMOVE_TOPIC))) {
            try {
                conn.setAutoCommit(false);
                statement.setLong(1, id);
                statement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
    }

    /**
     * Obtains list of all topics from database
     *
     * @return list of topics
     * @throws DataBaseInteractionException
     */
    @Override
    public List<Topic> findAll() throws DataBaseInteractionException {
        List<Topic> listTopic = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_ALL_TOPICS))) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    listTopic.add(createTopicFromDb(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }

        return listTopic;
    }

    /**
     * Find topic by id
     *
     * @param id - id of topic
     * @return topic with this id
     * @throws DataBaseInteractionException
     */
    @Override
    public Topic findById(Long id) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_TOPIC_BY_ID))) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createTopicFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
    }

    /**
     * Find topic by title
     *
     * @param title - title of topic
     * @return topic with this title
     * @throws DataBaseInteractionException
     */
    @Override
    public Topic findByTitle(String title) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_TOPIC_BY_TITLE))) {

            statement.setString(1, title);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createTopicFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
    }

    /**
     * Find topic by course id
     *
     * @param courseId - course id
     * @return topic
     * @throws DataBaseInteractionException
     */
    @Override
    public List<Topic> findTopicsByCourse(Long courseId) throws DataBaseInteractionException {
        List<Topic> listCourse = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_TOPICS_BY_COURSE))) {

            statement.setLong(1, courseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    listCourse.add(createTopicFromDb(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
        return listCourse;
    }

    /**
     * Find all topics that not include course with this id
     *
     * @param courseId - course id
     * @return list of topics
     */
    @Override
    public List<Topic> topicsThatDoNotIncludeTheCourse(Long courseId) {
        List<Topic> topicForCourse = findTopicsByCourse(courseId);
        List<Topic> allTopics = findAll();
        allTopics.removeAll(topicForCourse);
        return allTopics;
    }

    private Topic createTopicFromDb(ResultSet resultSet) throws SQLException {
        return Topic.builder()
                .id(resultSet.getLong(TOPIC_ID))
                .title(resultSet.getString(TOPIC_TITLE))
                .build();
    }
}
