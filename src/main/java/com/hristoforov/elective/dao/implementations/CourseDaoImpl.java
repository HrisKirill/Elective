package com.hristoforov.elective.dao.implementations;

import com.hristoforov.elective.DBCPDataSource;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.entities.Course;
import com.hristoforov.elective.utils.WorkWithEntityFromDb;
import com.hristoforov.elective.utils.WorkWithFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl extends DBCPDataSource implements CourseDao {

    @Override
    public void create(Course entity) {
        try (Connection conn = createConnection();
             PreparedStatement ps =
                     conn.prepareStatement(WorkWithFile.readFromFile("courseQueries/fillTableCourse.sql"),
                             Statement.RETURN_GENERATED_KEYS)) {
            try {
                conn.setAutoCommit(false);
                ps.setString(1, entity.getTitle());
                ps.setInt(2, entity.getDuration());
                ps.setDate(3, entity.getStartDate());
                ps.setDate(4, entity.getEndDate());
                ps.setLong(5, entity.getUser().getId());
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
    public void update(Course entity) {
        try (Connection conn = createConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile.readFromFile("courseQueries/updateCourse.sql"))) {
            try {
                conn.setAutoCommit(false);
                preparedStatement.setString(1, entity.getTitle());
                preparedStatement.setInt(2, entity.getDuration());
                preparedStatement.setDate(3, entity.getStartDate());
                preparedStatement.setDate(4, entity.getEndDate());
                preparedStatement.setLong(5, entity.getUser().getId());
                preparedStatement.setLong(6, entity.getId());

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
    public void remove(Course entity) {
        try (Connection conn = createConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile.readFromFile("courseQueries/removeCourse.sql"))) {
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
    public List<Course> findAll() {
        List<Course> listCourse = new ArrayList<>();

        try (Connection conn = createConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile.readFromFile("courseQueries/findAllInCourse.sql"))) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    listCourse.add(WorkWithEntityFromDb.createCourseFromDb(resultSet));
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return listCourse;
    }

    @Override
    public Course findById(Long id) {
        try (Connection conn = createConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile.readFromFile("courseQueries/findCourseById.sql"))) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return WorkWithEntityFromDb.createCourseFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Course findByTitle(String title) {
        try (Connection conn = createConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile.readFromFile("courseQueries/findCourseByTitle.sql"))) {

            statement.setString(1, title);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return WorkWithEntityFromDb.createCourseFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
