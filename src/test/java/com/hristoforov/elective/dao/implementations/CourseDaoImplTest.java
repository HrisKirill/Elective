package com.hristoforov.elective.dao.implementations;

import com.hristoforov.elective.dao.interfaces.CourseDao;
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
import static com.hristoforov.elective.constants.CRA_QueriesFiles.SELECT_NOT_STARTING_COURSES_WITHOUT_OFFSET;
import static com.hristoforov.elective.constants.CRA_QueriesFiles.SORT_COURSES_BY_TITLE;
import static com.hristoforov.elective.constants.CommonConstants.RECORDS_PER_PAGE;
import static com.hristoforov.elective.constants.CommonConstants.ZERO;
import static com.hristoforov.elective.constants.DBAttributes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class CourseDaoImplTest {



    private Course getTestCourse() {
        return Course.builder()
                .id(ID_VALUE)
                .title(TITLE_COURSE_VALUE)
                .duration(DURATION_VALUE)
                .startDate(START_DATE_VALUE)
                .endDate(END_DATE_VALUE)
                .build();
    }

    @Test
    void testCreate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> courseDao.create(getTestCourse()));
        }
    }

    @Test
    void testDataBaseInteractionExceptionCreate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> courseDao.create(getTestCourse()));
    }

    @Test
    void testSqlExceptionCreate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).execute();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> new CourseDaoImpl(dataSource).create(getTestCourse()));
    }

    @Test
    void testFindById() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Course resultCourse = courseDao.findById(ID_VALUE);
            assertNotNull(resultCourse);
            assertEquals(getTestCourse(), resultCourse);
        }
    }

    @Test
    void testSqlExceptionFindById() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> courseDao.findById(ID_VALUE));
    }

    @Test
    void testFindByIdIfAbsent() throws SQLException, DataBaseInteractionException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            Course resultUserById = courseDao.findById(ID_VALUE);
            assertNull(resultUserById);
        }
    }

    @Test
    void testUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> courseDao.update(getTestCourse()));
        }
    }

    @Test
    void testDataBaseInteractionExceptionUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> courseDao.update(getTestCourse()));
    }

    @Test
    void testSqlExceptionUpdate() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).executeUpdate();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> new CourseDaoImpl(dataSource).update(getTestCourse()));
    }


    @Test
    void testRemove() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> courseDao.remove(ID_VALUE));
        }
    }

    @Test
    void testDataBaseInteractionExceptionRemove() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> courseDao.remove(ID_VALUE));
    }

    @Test
    void testSqlExceptionRemove() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).executeUpdate();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> courseDao.remove(ID_VALUE));
    }

    @Test
    void testFindAll() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Course> users = courseDao.findAll();
            assertEquals(1, users.size());
            assertEquals(getTestCourse(), users.get(0));
        }
    }

    @Test
    void testSqlExceptionFindAll() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, courseDao::findAll);
    }

    @Test
    void testFindAllWithOffset() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Course> users = courseDao.findAllWithOffset(ZERO, RECORDS_PER_PAGE);
            assertEquals(1, users.size());
            assertEquals(getTestCourse(), users.get(0));
        }
    }

    @Test
    void testSqlExceptionFindAllWithOffset() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> courseDao.findAllWithOffset(ZERO, RECORDS_PER_PAGE));
    }

    @Test
    void testFindByTitle() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Course resultCourse = courseDao.findByTitle(TITLE_COURSE_VALUE);
            assertNotNull(resultCourse);
            assertEquals(getTestCourse(), resultCourse);
        }
    }

    @Test
    void testSqlExceptionFindByTitle() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> courseDao.findByTitle(TITLE_COURSE_VALUE));
    }

    @Test
    void testFindByTitleIfAbsent() throws SQLException, DataBaseInteractionException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            Course resultUserById = courseDao.findByTitle(TITLE_COURSE_VALUE);
            assertNull(resultUserById);
        }
    }

    @Test
    void testFindAllCoursesByUserId() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Map<Course, Integer> courses = courseDao.findAllCoursesByUserId(ID_VALUE);
            assertEquals(1, courses.size());
        }
    }

    @Test
    void testSqlExceptionFindAllCoursesByUserId() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> courseDao.findAllCoursesByUserId(ID_VALUE));
    }

    @Test
    void testFindAllCoursesByTopicAndUserId() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Map<Course, Integer> courses = courseDao.findAllCoursesByTopicAndUserId(ID_VALUE, ID_VALUE);
            assertEquals(1, courses.size());
        }
    }

    @Test
    void testSqlExceptionFindAllCoursesByTopicAndUserId() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> courseDao.findAllCoursesByTopicAndUserId(ID_VALUE, ID_VALUE));
    }

    @Test
    void testFindAllCoursesByTopicAndUserIdWithOffset() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Map<Course, Integer> courses = courseDao.findAllCoursesByTopicAndUserIdsWithOffset
                    (ID_VALUE, ID_VALUE, ZERO, RECORDS_PER_PAGE);
            assertEquals(1, courses.size());
        }
    }

    @Test
    void testSqlExceptionFindAllCoursesByTopicAndUserIdWithOffset() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> courseDao.findAllCoursesByTopicAndUserIdsWithOffset
                (ID_VALUE, ID_VALUE, ZERO, RECORDS_PER_PAGE));
    }

    @Test
    void testCoursesInWhichTheStudentDoesNotParticipateWithoutOffset() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Course> courses = courseDao.coursesInWhichTheStudentDoesNotParticipateWithoutOffset(ID_VALUE);
            assertEquals(0, courses.size());
        }
    }

    @Test
    void testCoursesInWhichTheStudentDoesNotParticipateWithOffset() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Course> courses = courseDao.coursesInWhichTheStudentDoesNotParticipateWithOffset(ID_VALUE, ZERO, RECORDS_PER_PAGE);
            assertEquals(1, courses.size());
        }
    }

    @Test
    void testSqlExceptionCoursesInWhichTheStudentDoesNotParticipateWithOffset() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class,
                () -> courseDao.coursesInWhichTheStudentDoesNotParticipateWithOffset(ID_VALUE, ZERO, RECORDS_PER_PAGE));
    }

    @Test
    void testIncrementCountOfStudent() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement ignored = prepareMocks(dataSource)) {
            assertDoesNotThrow(() -> courseDao.incrementCountOfStudent(ID_VALUE));
        }
    }

    @Test
    void testDataBaseInteractionExceptionIncrementCountOfStudent() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class, () -> courseDao.incrementCountOfStudent(ID_VALUE));
    }

    @Test
    void testSqlExceptionIncrementCountOfStudent() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        Connection connection = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(ps);
        doThrow(SQLException.class).when(ps).executeUpdate();
        Assertions.assertThrows(DataBaseInteractionException.class, () -> courseDao.incrementCountOfStudent(ID_VALUE));
    }

    @Test
    void testCoursesWithoutTeachers() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Course> courses = courseDao.coursesWithoutTeachers(List.of(new User.Builder().id(ID_VALUE).build()));
            assertEquals(1, courses.size());
        }
    }

    @Test
    void testSortCoursesBySomeValueWithOffset() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            List<Course> courses = courseDao.sortCoursesBySomeValueWithOffset(ZERO, RECORDS_PER_PAGE, SORT_COURSES_BY_TITLE);
            assertEquals(1, courses.size());
        }
    }

    @Test
    void testSqlExceptionSortCoursesBySomeValueWithOffset() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class,
                () -> courseDao.sortCoursesBySomeValueWithOffset(ZERO, RECORDS_PER_PAGE, SORT_COURSES_BY_TITLE));
    }


    @Test
    void testSelectBySomeValueWithOffset() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Map<Course, Integer> courses = courseDao.selectBySomeValueWithOffset(ID_VALUE, ZERO, RECORDS_PER_PAGE, SORT_COURSES_BY_TITLE);
            assertEquals(1, courses.size());
        }
    }

    @Test
    void testSqlExceptionSelectBySomeValueWithOffset() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class,
                () -> courseDao.selectBySomeValueWithOffset(ID_VALUE, ZERO, RECORDS_PER_PAGE, SORT_COURSES_BY_TITLE));
    }


    @Test
    void testFindAllCoursesByUserIdWithOffset() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Map<Course, Integer> courses = courseDao.findAllCoursesByUserIdWithOffset(ID_VALUE, ZERO, RECORDS_PER_PAGE);
            assertEquals(1, courses.size());
        }
    }

    @Test
    void testSqlExceptionFindAllCoursesByUserIdWithOffset() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class,
                () -> courseDao.findAllCoursesByUserIdWithOffset(ID_VALUE, ZERO, RECORDS_PER_PAGE));
    }


    @Test
    void testSelectCoursesBySomeValueWithoutOffset() throws DataBaseInteractionException, SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        try (PreparedStatement preparedStatement = prepareMocks(dataSource)) {
            ResultSet resultSet = mock(ResultSet.class);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            prepareResultSet(resultSet);
            Map<Course, Integer> courses = courseDao.selectCoursesBySomeValueWithoutOffset(ID_VALUE, SELECT_NOT_STARTING_COURSES_WITHOUT_OFFSET);
            assertEquals(1, courses.size());
        }
    }

    @Test
    void testSqlExceptionSelectCoursesBySomeValueWithoutOffset() throws SQLException {
        DataSource dataSource = mock(DataSource.class);
        CourseDao courseDao = new CourseDaoImpl(dataSource);
        when(dataSource.getConnection()).thenThrow(new SQLException());
        assertThrows(DataBaseInteractionException.class,
                () -> courseDao.selectCoursesBySomeValueWithoutOffset(ID_VALUE, SELECT_NOT_STARTING_COURSES_WITHOUT_OFFSET));
    }

    private static void prepareResultSet(ResultSet resultSet) throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(COURSE_ID)).thenReturn(ID_VALUE);
        when(resultSet.getString(COURSE_TITLE)).thenReturn(TITLE_COURSE_VALUE);
        when(resultSet.getInt(COURSE_DURATION)).thenReturn(DURATION_VALUE);
        when(resultSet.getDate(COURSE_START_DATE)).thenReturn(START_DATE_VALUE);
        when(resultSet.getDate(COURSE_END_DATE)).thenReturn(END_DATE_VALUE);
    }


    private PreparedStatement prepareMocks(DataSource dataSource) throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setInt(isA(int.class), isA(int.class));
        doNothing().when(preparedStatement).setLong(isA(int.class), isA(long.class));
        doNothing().when(preparedStatement).setString(isA(int.class), isA(String.class));
        doNothing().when(preparedStatement).setDate(isA(int.class), isA(Date.class));
        when(preparedStatement.execute()).thenReturn(true);
        return preparedStatement;
    }
}