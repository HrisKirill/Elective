package com.hristoforov.elective.dao.implementations;

import com.hristoforov.elective.DBCPDataSource;
import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.entities.Course;
import com.hristoforov.elective.entities.Topic;
import com.hristoforov.elective.utils.WorkWithEntityFromDb;
import com.hristoforov.elective.utils.WorkWithFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicDaoImpl extends DBCPDataSource implements TopicDao {
    @Override
    public void create(Topic entity) {
        try (Connection conn = createConnection();
             PreparedStatement ps =
                     conn.prepareStatement(WorkWithFile.readFromFile("topicQueries/fillTableTopic.sql"),
                             Statement.RETURN_GENERATED_KEYS)) {
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Topic entity) {
        try (Connection conn = createConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile.readFromFile("topicQueries/updateTopic.sql"))) {
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Topic entity) {
        try (Connection conn = createConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile.readFromFile("topicQueries/removeTopic.sql"))) {
            try {
                conn.setAutoCommit(false);
                statement.setLong(1, entity.getId());
                statement.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Topic> findAll() {
        List<Topic> listTopic = new ArrayList<>();

        try (Connection conn = createConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile.readFromFile("topicQueries/findAllInTopic.sql"))) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    listTopic.add(WorkWithEntityFromDb.createTopicFromDb(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return listTopic;
    }

    @Override
    public Topic findById(Long id) {
        try (Connection conn = createConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile.readFromFile("topicQueries/findTopicById.sql"))) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return WorkWithEntityFromDb.createTopicFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Topic findByTitle(String title) {
        try (Connection conn = createConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile.readFromFile("topicQueries/findTopicByTitle.sql"))) {

            statement.setString(1, title);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return WorkWithEntityFromDb.createTopicFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
