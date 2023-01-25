package com.hristoforov.elective.dao.implementations;

import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.exceptions.DataBaseInteractionException;
import com.hristoforov.elective.utils.WorkWithFile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hristoforov.elective.constants.CRA_QueriesFiles.*;
import static com.hristoforov.elective.constants.DBAttributes.*;

/**
 * Course DAO class for db. Match tables 'course' in database.
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public class CourseDaoImpl implements CourseDao {
    private final DataSource dataSource;

    public CourseDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * Insert course into db
     *
     * @param entity - concrete entity
     * @throws DataBaseInteractionException
     */
    @Override
    public void create(Course entity) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(WorkWithFile.readFromFile(CREATE_COURSE))) {
            try {
                conn.setAutoCommit(false);
                ps.setString(1, entity.getTitle());
                ps.setInt(2, entity.getDuration());
                ps.setDate(3, entity.getStartDate());
                ps.setDate(4, entity.getEndDate());
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
     * Update course in db
     *
     * @param entity -  concrete entity
     * @throws DataBaseInteractionException
     */
    @Override
    public void update(Course entity) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile.readFromFile(UPDATE_COURSE))) {
            try {
                conn.setAutoCommit(false);
                preparedStatement.setString(1, entity.getTitle());
                preparedStatement.setInt(2, entity.getDuration());
                preparedStatement.setDate(3, entity.getStartDate());
                preparedStatement.setDate(4, entity.getEndDate());
                preparedStatement.setLong(5, entity.getId());

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
     * Remove course by id from db
     *
     * @param id - id of entity
     * @throws DataBaseInteractionException
     */
    @Override
    public void remove(Long id) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(REMOVE_COURSE))) {
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
     * Obtains list of all courses from database
     *
     * @return list of courses
     * @throws DataBaseInteractionException
     */
    @Override
    public List<Course> findAll() throws DataBaseInteractionException {
        List<Course> listCourse = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_ALL_COURSES))) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    listCourse.add(createCourseFromDb(resultSet));
                }

            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }

        return listCourse;
    }

    /**
     * Find all courses with offset
     *
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @return list of courses
     * @throws DataBaseInteractionException
     */
    @Override
    public List<Course> findAllWithOffset(int offset, int recordsPerPage) throws DataBaseInteractionException {
        List<Course> allCoursesWithOffset = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_ALL_COURSES_WITH_OFFSET))) {

            preparedStatement.setLong(1, offset);
            preparedStatement.setLong(2, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    allCoursesWithOffset.add(createCourseFromDb(resultSet));
                }

            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }

        return allCoursesWithOffset;
    }


    /**
     * Find entity by id
     *
     * @param id - id of entity
     * @return course with this id
     * @throws DataBaseInteractionException
     */
    @Override
    public Course findById(Long id) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_COURSE_BY_ID))) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createCourseFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
    }

    /**
     * Find course by title
     *
     * @param title - course title to find
     * @return course with this title
     * @throws DataBaseInteractionException
     */
    @Override
    public Course findByTitle(String title) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_COURSE_BY_TITLE))) {

            statement.setString(1, title);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createCourseFromDb(resultSet);
                }

                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
    }

    /**
     * Finds all courses and marks for them
     *
     * @param userId - user id
     * @return map of course and user's mark
     * @throws DataBaseInteractionException
     */
    @Override
    public Map<Course, Integer> findAllCoursesByUserId(Long userId) throws DataBaseInteractionException {
        Map<Course, Integer> courseIntegerMap = new LinkedHashMap<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_ALL_COURSES_BY_USER_ID))) {

            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    courseIntegerMap.put(createCourseFromDb(resultSet),
                            resultSet.getInt(MARK));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
        return courseIntegerMap;
    }

    /**
     * Find all courses by topic and User ids
     *
     * @param topicId - topic id
     * @param userId  - user id
     * @return map of course and user's mark
     * @throws DataBaseInteractionException
     */
    @Override
    public Map<Course, Integer> findAllCoursesByTopicAndUserId(Long topicId, Long userId) throws DataBaseInteractionException {
        Map<Course, Integer> coursesByTopicAndUserIds = new LinkedHashMap<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_ALL_COURSES_BY_TOPIC_AND_USER_IDS))) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, topicId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    coursesByTopicAndUserIds.put(createCourseFromDb(resultSet),
                            resultSet.getInt(MARK));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
        return coursesByTopicAndUserIds;
    }

    /**
     * Find all courses by topic and user ids with offset
     *
     * @param topicId        - topic id
     * @param userId         - user id
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @return map of course and user's mark
     * @throws DataBaseInteractionException
     */
    @Override
    public Map<Course, Integer> findAllCoursesByTopicAndUserIdsWithOffset(Long topicId, Long userId,
                                                                          int offset, int recordsPerPage) throws DataBaseInteractionException {
        Map<Course, Integer> coursesByTopicAndUserIdsWithOffset = new LinkedHashMap<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_ALL_COURSES_BY_TOPIC_AND_USER_IDS_WITH_OFFSET))) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, topicId);
            preparedStatement.setLong(3, offset);
            preparedStatement.setLong(4, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    coursesByTopicAndUserIdsWithOffset.put(createCourseFromDb(resultSet),
                            resultSet.getInt(MARK));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
        return coursesByTopicAndUserIdsWithOffset;
    }

    /**
     * Find courses in which student does not participate
     *
     * @param userId - user id
     * @return list of courses
     */
    @Override
    public List<Course> coursesInWhichTheStudentDoesNotParticipateWithoutOffset(Long userId) {
        List<Course> allUserCourses = new ArrayList<>(findAllCoursesByUserId(userId).keySet());
        return findAll().stream().filter(one -> !allUserCourses.stream()
                        .anyMatch(two -> two.getTitle().equals(one.getTitle())))
                .collect(Collectors.toList());
    }

    /**
     * Increment count of student
     *
     * @param courseId - course id
     * @throws DataBaseInteractionException
     */
    @Override
    public void incrementCountOfStudent(Long courseId) throws DataBaseInteractionException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile.readFromFile(INCREMENT_COUNT_OF_STUDENT))) {
            try {
                conn.setAutoCommit(false);
                preparedStatement.setLong(1, courseId);
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
     * Find courses in which student does not participate with offset
     *
     * @param userId         - user id
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @return list of courses
     * @throws DataBaseInteractionException
     */
    @Override
    public List<Course> coursesInWhichTheStudentDoesNotParticipateWithOffset
    (Long userId, int offset, int recordsPerPage) throws DataBaseInteractionException {
        List<Course> courseList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(COURSES_IN_WHICH_THE_STUDENT_DOES_NOT_PARTICIPATE_WITH_OFFSET))) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, offset);
            preparedStatement.setLong(3, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    courseList.add(createCourseFromDb(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }

        return courseList;
    }

    /**
     * Find all courses without teachers
     *
     * @param teacherList list of teachers
     * @return list of courses without teachers
     */
    @Override
    public List<Course> coursesWithoutTeachers(List<User> teacherList) {
        List<Course> courses = new ArrayList<>();
        List<Course> coursesFromFindAll = findAll();

        for (User teacher :
                teacherList) {
            courses.addAll(findAllCoursesByUserId(teacher.getId()).keySet());
        }
        coursesFromFindAll.removeAll(courses);
        return coursesFromFindAll;
    }

    /**
     * Sort courses in some way with offset
     *
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @param fileName       - file name
     * @return sorted list of courses
     * @throws DataBaseInteractionException
     */
    @Override
    public List<Course> sortCoursesBySomeValueWithOffset(int offset, int recordsPerPage, String fileName)
            throws DataBaseInteractionException {
        List<Course> sortedCoursesBySomeValueWithOffset = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(WorkWithFile.readFromFile(fileName))) {

            preparedStatement.setLong(1, offset);
            preparedStatement.setLong(2, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    sortedCoursesBySomeValueWithOffset.add(createCourseFromDb(resultSet));
                }

            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }

        return sortedCoursesBySomeValueWithOffset;
    }

    /**
     * Select courses in some way with offset
     *
     * @param userId         - user id
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @param fileName       - file name
     * @return selected list of courses
     * @throws DataBaseInteractionException
     */
    @Override
    public Map<Course, Integer> selectBySomeValueWithOffset
    (Long userId, int offset, int recordsPerPage, String fileName) throws DataBaseInteractionException {
        Map<Course, Integer> notStartingCourses = new LinkedHashMap<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(fileName))) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    notStartingCourses.put(createCourseFromDb(resultSet),
                            resultSet.getInt(MARK));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
        return notStartingCourses;
    }

    /**
     * Find all course by user id with offset
     *
     * @param userId         - user id
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @return map of course and user's mark
     * @throws DataBaseInteractionException
     */
    @Override
    public Map<Course, Integer> findAllCoursesByUserIdWithOffset
    (Long userId, int offset, int recordsPerPage) throws DataBaseInteractionException {
        Map<Course, Integer> allCourses = new LinkedHashMap<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(FIND_ALL_COURSES_BY_USER_ID_WITH_OFFSET))) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, recordsPerPage);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    allCourses.put(createCourseFromDb(resultSet),
                            resultSet.getInt(MARK));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
        return allCourses;
    }

    /**
     * Select course in some way
     *
     * @param userId   -  user id
     * @param fileName - file name
     * @return map of course and user's mark
     * @throws DataBaseInteractionException
     */
    @Override
    public Map<Course, Integer> selectCoursesBySomeValueWithoutOffset(Long userId, String fileName)
            throws DataBaseInteractionException {
        Map<Course, Integer> courses = new LinkedHashMap<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     conn.prepareStatement(WorkWithFile
                             .readFromFile(fileName))) {

            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    courses.put(createCourseFromDb(resultSet),
                            resultSet.getInt(MARK));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseInteractionException(e);
        }
        return courses;
    }

    private Course createCourseFromDb(ResultSet resultSet) throws SQLException {
        return Course.builder()
                .id(resultSet.getLong(COURSE_ID))
                .title(resultSet.getString(COURSE_TITLE))
                .duration(resultSet.getInt(COURSE_DURATION))
                .startDate(resultSet.getDate(COURSE_START_DATE))
                .endDate(resultSet.getDate(COURSE_END_DATE))
                .build();
    }
}
