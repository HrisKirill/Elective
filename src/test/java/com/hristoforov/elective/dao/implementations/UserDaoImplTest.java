package com.hristoforov.elective.dao.implementations;

import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.exceptions.DataBaseInteractionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Map;

import static com.hristoforov.elective.Constants.*;
import static com.hristoforov.elective.constants.CRA_QueriesFiles.FIND_ALL_STUDENTS_WITH_OFFSET;
import static com.hristoforov.elective.constants.CommonConstants.RECORDS_PER_PAGE;
import static com.hristoforov.elective.constants.CommonConstants.ZERO;
import static com.hristoforov.elective.constants.DBAttributes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


class UserDaoImplTest {

    private Course getTestCourse() {
        return Course.builder()
                .id(ID_VALUE)
                .title(TITLE_COURSE_VALUE)
                .duration(DURATION_VALUE)
                .startDate(START_DATE_VALUE)
                .endDate(END_DATE_VALUE)
                .build();
    }

    private User getTestUser() {
        return new User.Builder()
                .id(ID_VALUE)
                .lastName(LAST_NAME_VALUE)
                .firstName(FIRST_NAME_VALUE)
                .login(LOGIN_VALUE)
                .password(PASSWORD_VALUE)
                .email(EMAIL_VALUE)
                .role(ROLE_VALUE)
                .status(STATUS_VALUE)
                .build();
    }

    @Test
    void testCreate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDao.create(getTestUser()));
        }
    }

    @Test
    void testDataBaseInteractionExceptionCreate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> userDao.create(getTestUser()));
    }


    @Test
    void testSqlExceptionCreate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).execute();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> userDao.create(getTestUser()));
    }

    @Test
    void testCreateUserCourse() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDao.createUserCourse(getTestUser(), getTestCourse(), 0));
        }
    }

    @Test
    void testDataBaseInteractionExceptionCreateUserCourse() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> userDao.createUserCourse(getTestUser(), getTestCourse(), 0));
    }


    @Test
    void testSqlExceptionCreateUserCourse() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).execute();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> userDao.createUserCourse(getTestUser(), getTestCourse(), 0));
    }

    @Test
    void testUpdateUserCourse() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDao.updateUserCourse(getTestUser(), getTestCourse(), 0));
        }
    }

    @Test
    void testDataBaseInteractionExceptionUpdateUserCourse() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> userDao.updateUserCourse(getTestUser(), getTestCourse(), 0));
    }

    @Test
    void testSqlExceptionUpdateUserCourse() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).executeUpdate();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> userDao.updateUserCourse(getTestUser(), getTestCourse(), 0));
    }

    @Test
    void testFindById() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            User resultUser = userDao.findById(ID_VALUE);
            assertNotNull(resultUser);
            assertEquals(getTestUser(), resultUser);
        }
    }

    @Test
    void testSqlExceptionFindById() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> userDao.findById(ID_VALUE));
    }

    @Test
    void testFindByValuesIfAbsent() throws SQLException, DataBaseInteractionException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            User resultUserById = userDao.findById(ID_VALUE);
            User resultUSerByLogin = userDao.findByLogin(LOGIN_VALUE);
            User resultUserEmail = userDao.findByEmail(EMAIL_VALUE);
            assertNull(resultUserById);
            assertNull(resultUSerByLogin);
            assertNull(resultUserEmail);
        }
    }


    @Test
    void testFindByLogin() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            User resultUser = userDao.findByLogin(LOGIN_VALUE);
            assertNotNull(resultUser);
            assertEquals(getTestUser(), resultUser);
        }
    }

    @Test
    void testSqlExceptionFindByLogin() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> userDao.findByLogin(LOGIN_VALUE));
    }

    @Test
    void testGetByEmail() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            User resultUser = userDao.findByEmail(EMAIL_VALUE);
            assertNotNull(resultUser);
            assertEquals(getTestUser(), resultUser);
        }
    }

    @Test
    void testSqlExceptionFindByEmail() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> userDao.findByEmail(EMAIL_VALUE));
    }

    @Test
    void testUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDao.update(getTestUser()));
        }
    }

    @Test
    void testDataBaseInteractionExceptionUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> userDao.update(getTestUser()));
    }

    @Test
    void testSqlExceptionUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).executeUpdate();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> new UserDaoImpl(dataSource).update(getTestUser()));
    }

    @Test
    void testFindAll() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<User> users = userDao.findAll();
            assertEquals(1, users.size());
            assertEquals(getTestUser(), users.get(0));
        }
    }

    @Test
    void testSqlExceptionFindAll() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, userDao::findAll);
    }

    @Test
    void testFindAllStudents() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<User> users = userDao.findAllStudents();
            assertEquals(1, users.size());
            assertEquals(getTestUser(), users.get(0));
        }
    }


    @Test
    void testFindAllStudentsByCourseId() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Map<User, Integer> users = userDao.findAllStudentsByCourseId(ID_VALUE);
            assertEquals(1, users.size());
        }
    }

    @Test
    void testSqlExceptionFindAllStudentsByCourseId() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> userDao.findAllStudentsByCourseId(ID_VALUE));
    }

    @Test
    void testFindAllTeachers() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<User> users = userDao.findAllTeachers();
            assertEquals(0, users.size());
        }
    }

    @Test
    void testFindTeachersOrStudentsOrAllWithOffset() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<User> users = userDao.findTeachersOrStudentsOrAllWithOffset(ZERO, RECORDS_PER_PAGE, FIND_ALL_STUDENTS_WITH_OFFSET);
            assertEquals(1, users.size());
            assertEquals(getTestUser(), users.get(0));
        }
    }

    @Test
    void testSqlExceptionFindTeachersOrStudentsOrAllWithOffset() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () ->
                userDao.findTeachersOrStudentsOrAllWithOffset(ZERO, RECORDS_PER_PAGE, FIND_ALL_STUDENTS_WITH_OFFSET));
    }

    @Test
    void testRemove() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> userDao.remove(ID_VALUE));
        }
    }

    @Test
    void testDataBaseInteractionExceptionRemove() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        UserDao userDao = new UserDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> userDao.remove(ID_VALUE));
    }

    @Test
    void testSqlExceptionRemove() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).executeUpdate();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> new UserDaoImpl(dataSource).remove(ID_VALUE));
    }

    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(USER_ID)).thenReturn(ID_VALUE);
        when(resultSet.getString(USER_LAST_NAME)).thenReturn(LAST_NAME_VALUE);
        when(resultSet.getString(USER_FIRST_NAME)).thenReturn(FIRST_NAME_VALUE);
        when(resultSet.getString(USER_LOGIN)).thenReturn(LOGIN_VALUE);
        when(resultSet.getString(USER_PASSWORD)).thenReturn(PASSWORD_VALUE);
        when(resultSet.getString(USER_EMAIL)).thenReturn(EMAIL_VALUE);
        when(resultSet.getString(USER_ROLE)).thenReturn(ROLE_VALUE.name());
        when(resultSet.getString(USER_STATUS)).thenReturn(STATUS_VALUE.name());
    }


    private PreparedStatement prepareMocks(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doNothing().when(preparedStatement).setInt(isA(int.class), isA(int.class));
        doNothing().when(preparedStatement).setString(isA(int.class), isA(String.class));
        when(preparedStatement.execute()).thenReturn(true);
        return preparedStatement;
    }

}