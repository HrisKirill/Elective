package com.hristoforov.elective.dao.implementations;

import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.topic.Topic;
import com.hristoforov.elective.exceptions.DataBaseInteractionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.hristoforov.elective.Constants.*;
import static com.hristoforov.elective.constants.DBAttributes.TOPIC_ID;
import static com.hristoforov.elective.constants.DBAttributes.TOPIC_TITLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class TopicDaoImplTest {

    private Topic getTestTopic() {
        return Topic.builder()
                .id(ID_VALUE)
                .title(TITLE_TOPIC_VALUE)
                .build();
    }


    @Test
    void testCreate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> topicDao.create(getTestTopic()));
        }
    }

    @Test
    void testDataBaseInteractionExceptionCreate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> topicDao.create(getTestTopic()));
    }


    @Test
    void testSqlExceptionCreate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).execute();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> topicDao.create(getTestTopic()));
    }

    @Test
    void testCreateTopicCourse() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> topicDao.createTopicCourse(getTestTopic(), getTestCourse()));
        }
    }

    @Test
    void testDataBaseInteractionExceptionCreateTopicCourse() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> topicDao.createTopicCourse(getTestTopic(), getTestCourse()));
    }


    @Test
    void testSqlExceptionCreateTopicCourse() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).execute();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> topicDao.createTopicCourse(getTestTopic(), getTestCourse()));
    }


    @Test
    void testUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> topicDao.update(getTestTopic()));
        }
    }

    @Test
    void testDataBaseInteractionExceptionUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> topicDao.update(getTestTopic()));
    }

    @Test
    void testSqlExceptionUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).executeUpdate();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> topicDao.update(getTestTopic()));
    }

    @Test
    void testRemove() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> topicDao.remove(ID_VALUE));
        }
    }

    @Test
    void testDataBaseInteractionExceptionRemove() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> topicDao.remove(ID_VALUE));
    }

    @Test
    void testSqlExceptionRemove() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).executeUpdate();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> topicDao.remove(ID_VALUE));
    }


    @Test
    void testFindAll() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Topic> users = topicDao.findAll();
            assertEquals(1, users.size());
            assertEquals(getTestTopic(), users.get(0));
        }
    }

    @Test
    void testSqlExceptionFindAll() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, topicDao::findAll);
    }

    @Test
    void testFindById() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Topic resultTopic = topicDao.findById(ID_VALUE);
            assertNotNull(resultTopic);
            assertEquals(getTestTopic(), resultTopic);
        }
    }

    @Test
    void testSqlExceptionFindById() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> topicDao.findById(ID_VALUE));
    }

    @Test
    void testFindByIdIfAbsent() throws SQLException, DataBaseInteractionException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            Topic resultTopicById = topicDao.findById(ID_VALUE);
            assertNull(resultTopicById);
        }
    }

    @Test
    void testFindByTitle() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Topic resultTopic = topicDao.findByTitle(TITLE_TOPIC_VALUE);
            assertNotNull(resultTopic);
            assertEquals(getTestTopic(), resultTopic);
        }
    }

    @Test
    void testSqlExceptionFindByTitle() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> topicDao.findByTitle(TITLE_TOPIC_VALUE));
    }

    @Test
    void testFindByTitleIfAbsent() throws SQLException, DataBaseInteractionException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            Topic resultTopicById = topicDao.findByTitle(TITLE_TOPIC_VALUE);
            assertNull(resultTopicById);
        }
    }


    @Test
    void testFindTopicsByCourse() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Topic> topicsByCourse = topicDao.findTopicsByCourse(ID_VALUE);
            assertEquals(1, topicsByCourse.size());
        }
    }

    @Test
    void testSqlExceptionFindTopicsByCourse() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> topicDao.findTopicsByCourse(ID_VALUE));
    }

    @Test
    void testTopicsThatDoNotIncludeTheCourse() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        TopicDao topicDao = new TopicDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Topic> topicsByCourse = topicDao.topicsThatDoNotIncludeTheCourse(ID_VALUE);
            assertEquals(0, topicsByCourse.size());
        }
    }

    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(TOPIC_ID)).thenReturn(ID_VALUE);
        when(resultSet.getString(TOPIC_TITLE)).thenReturn(TITLE_TOPIC_VALUE);
    }


    private PreparedStatement prepareMocks(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doNothing().when(preparedStatement).setString(isA(int.class), isA(String.class));
        when(preparedStatement.execute()).thenReturn(true);
        return preparedStatement;
    }
}