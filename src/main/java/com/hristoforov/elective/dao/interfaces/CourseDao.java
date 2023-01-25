package com.hristoforov.elective.dao.interfaces;

import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.exceptions.DataBaseInteractionException;

import java.util.List;
import java.util.Map;

/**
 * CourseDao interface with all operations
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public interface CourseDao extends Dao<Course> {

    /**
     * Find course by title
     *
     * @param title - course title to find
     * @return Course
     * @throws DataBaseInteractionException
     */
    Course findByTitle(String title) throws DataBaseInteractionException;

    /**
     * Finds all courses and marks for them
     *
     * @param userId - user id
     * @return map of course and user's mark
     * @throws DataBaseInteractionException
     */
    Map<Course, Integer> findAllCoursesByUserId(Long userId) throws DataBaseInteractionException;

    /**
     * Find all courses with offset
     *
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @return list of courses
     * @throws DataBaseInteractionException
     */
    List<Course> findAllWithOffset(int offset, int recordsPerPage) throws DataBaseInteractionException;

    /**
     * Find all Courses by topic and user ids
     *
     * @param topicId - topic id
     * @param userId  - user id
     * @return map of course and user's mark
     * @throws DataBaseInteractionException
     */
    Map<Course, Integer> findAllCoursesByTopicAndUserId(Long topicId, Long userId) throws DataBaseInteractionException;

    /**
     * Find all Courses by topic and user ids with offset
     *
     * @param topicId        - topic id
     * @param userId         - user id
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @return map of course and user's mark
     * @throws DataBaseInteractionException
     */
    Map<Course, Integer> findAllCoursesByTopicAndUserIdsWithOffset(Long topicId, Long userId,
                                                                   int offset, int recordsPerPage) throws DataBaseInteractionException;

    /**
     * Find courses in which student does not participate
     *
     * @param userId - user id
     * @return list of courses
     */
    List<Course> coursesInWhichTheStudentDoesNotParticipateWithoutOffset(Long userId);

    /**
     * Increment count of student
     *
     * @param courseId - course id
     * @throws DataBaseInteractionException
     */
    void incrementCountOfStudent(Long courseId) throws DataBaseInteractionException;

    /**
     * Find courses in which student does not participate with offset
     *
     * @param userId         - user id
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @return list of courses
     * @throws DataBaseInteractionException
     */
    List<Course> coursesInWhichTheStudentDoesNotParticipateWithOffset
    (Long userId, int offset, int recordsPerPage) throws DataBaseInteractionException;

    /**
     * Find all courses without teachers
     *
     * @param teacherList list of teachers
     * @return list of courses without teachers
     */
    List<Course> coursesWithoutTeachers(List<User> teacherList);

    /**
     * Sort courses in some way with offset
     *
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @param fileName       - file name
     * @return sorted list of courses
     * @throws DataBaseInteractionException
     */
    List<Course> sortCoursesBySomeValueWithOffset(int offset, int recordsPerPage, String fileName) throws DataBaseInteractionException;

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
    Map<Course, Integer> selectBySomeValueWithOffset
    (Long userId, int offset, int recordsPerPage, String fileName) throws DataBaseInteractionException;

    /**
     * Find all course by user id with offset
     *
     * @param userId         - user id
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @return map of course and user's mark
     * @throws DataBaseInteractionException
     */
    Map<Course, Integer> findAllCoursesByUserIdWithOffset
    (Long userId, int offset, int recordsPerPage) throws DataBaseInteractionException;

    /**
     * Select course in some way
     *
     * @param userId   -  user id
     * @param fileName - file name
     * @return map of course and user's mark
     * @throws DataBaseInteractionException
     */
    Map<Course, Integer> selectCoursesBySomeValueWithoutOffset(Long userId, String fileName)
            throws DataBaseInteractionException;
}
