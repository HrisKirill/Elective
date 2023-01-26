package com.hristoforov.elective.dao.implementations;

import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.enums.Role;
import com.hristoforov.elective.entities.enums.Status;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.exceptions.DataBaseInteractionException;
import com.hristoforov.elective.utils.WorkWithFile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hristoforov.elective.constants.CRA_QueriesFiles.*;
import static com.hristoforov.elective.constants.DBAttributes.*;

/**
 * User DAO class for db. Match tables 'user' and 'users_courses' in database.
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public class UserDaoImpl implements UserDao {

    private final DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Insert user into db
     *
     * @param entity - concrete entity
     * @throws DataBaseInteractionException
     */
    @Override
    public void create(User entity) throws DataBaseInteractionException {

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(WorkWithFile.readFromFile(CREATE_USER))) {
            try {
                conn.setAutoCommit(false);
                ps.setString(1, entity.getLastName());
                ps.setString(2, entity.getFirstName());
                ps.setString(3, entity.getLogin());
                ps.setString(4, entity.getPassword());
                ps.setString(5, entity.getEmail());
                ps.setString(6, entity.getRole().name());
                ps.setString(7, entity.getStatus().name());
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
     * Insert into table 'users_courses' this pair
     *
     * @param user   - user
     * @param course - course
     * @param mark   - user's mark by this course
     * @throws DataBaseInteractionException
     */
    @Override
    public void createUserCourse(User user, Course course, int mark) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(CREATE_USER_COURSE))) {
            try {
                conn.setAutoCommit(false);
                ps.setLong(1, course.getId());
                ps.setLong(2, user.getId());
                ps.setInt(3, mark);
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
     * Update user and course pair
     *
     * @param user   - user
     * @param course - course
     * @param mark   - user's mark by this course
     * @throws DataBaseInteractionException
     */
    @Override
    public void updateUserCourse(User user, Course course, int mark) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(UPDATE_USER_COURSE))) {
            try {
                conn.setAutoCommit(false);
                ps.setInt(1, mark);
                ps.setLong(2, course.getId());
                ps.setLong(3, user.getId());
                ps.executeUpdate();
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
     * Update user in db
     *
     * @param entity -  concrete entity
     * @throws DataBaseInteractionException
     */
    @Override
    public void update(User entity) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile.readFromFile(UPDATE_USER))) {
            try {
                conn.setAutoCommit(false);
                preparedStatement.setString(1, entity.getLastName());
                preparedStatement.setString(2, entity.getFirstName());
                preparedStatement.setString(3, entity.getLogin());
                preparedStatement.setString(4, entity.getPassword());
                preparedStatement.setString(5, entity.getEmail());
                preparedStatement.setString(6, entity.getRole().name());
                preparedStatement.setString(7, entity.getStatus().name());
                preparedStatement.setLong(8, entity.getId());

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
     * Remove user from db
     *
     * @param id - id of entity
     * @throws DataBaseInteractionException
     */
    @Override
    public void remove(Long id) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(REMOVE_USER))) {
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
     * Find all users in db
     *
     * @return list of students
     * @throws DataBaseInteractionException
     */
    @Override
    public List<User> findAll() throws DataBaseInteractionException {
        List<User> listUser = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_ALL_USERS))) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    listUser.add(createUserFromDb(resultSet));
                }

            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }

        return listUser;
    }

    /**
     * Find user by id
     *
     * @param id - id of entity
     * @return user with this id
     * @throws DataBaseInteractionException
     */
    @Override
    public User findById(Long id) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_USER_BY_ID))) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createUserFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
    }

    /**
     * Find user by login
     *
     * @param login - user login
     * @return user with this login
     * @throws DataBaseInteractionException
     */
    @Override
    public User findByLogin(String login) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_USER_BY_LOGIN))) {

            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createUserFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
    }

    /**
     * Find user by email
     *
     * @param email - user email
     * @return user with this email
     * @throws DataBaseInteractionException
     */
    @Override
    public User findByEmail(String email) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(WorkWithFile
                     .readFromFile(FIND_USER_BY_EMAIL))) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createUserFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
    }

    /**
     * Find all students by course id
     *
     * @param courseId - course id
     * @return map of user and  mark
     * @throws DataBaseInteractionException
     */
    @Override
    public Map<User, Integer> findAllStudentsByCourseId(Long courseId) throws DataBaseInteractionException {
        Map<User, Integer> map = new HashMap<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(WorkWithFile
                     .readFromFile(FIND_ALL_USERS_BY_COURSE_ID))) {
            statement.setLong(1, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    if (resultSet.getString(USER_ROLE).equalsIgnoreCase(Role.STUDENT.name())) {
                        map.put(createUserFromDb(resultSet),
                                resultSet.getInt(MARK));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }

        return map;
    }

    /**
     * Find all teachers
     *
     * @return list of teachers
     */
    @Override
    public List<User> findAllTeachers() {
        return findAll().stream()
                .filter(user -> user.getRole().name().equalsIgnoreCase(Role.TEACHER.name()))
                .collect(Collectors.toList());
    }

    /**
     * Find all students
     *
     * @return list of students
     */
    @Override
    public List<User> findAllStudents() {
        return findAll().stream()
                .filter(user -> user.getRole().name().equalsIgnoreCase(Role.STUDENT.name()))
                .collect(Collectors.toList());
    }

    /**
     * Find teachers or students or all with offset
     *
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @param filePath       - path to file
     * @return list of users
     * @throws DataBaseInteractionException
     */
    @Override
    public List<User> findTeachersOrStudentsOrAllWithOffset(int offset, int recordsPerPage, String filePath) throws DataBaseInteractionException {
        List<User> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(WorkWithFile.readFromFile(filePath))) {
            preparedStatement.setLong(1, offset);
            preparedStatement.setLong(2, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(createUserFromDb(resultSet));
                }

            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }

        return list;

    }

    private User createUserFromDb(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .id(resultSet.getLong(USER_ID))
                .lastName(resultSet.getString(USER_LAST_NAME))
                .firstName(resultSet.getString(USER_FIRST_NAME))
                .login(resultSet.getString(USER_LOGIN))
                .password(resultSet.getString(USER_PASSWORD))
                .email(resultSet.getString(USER_EMAIL))
                .role(Role.checkRole(resultSet.getString(USER_ROLE)))
                .status(Status.checkStatus(resultSet.getString(USER_STATUS)))
                .build();
    }
}
