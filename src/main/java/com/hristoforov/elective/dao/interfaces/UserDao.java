package com.hristoforov.elective.dao.interfaces;

import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.exceptions.DataBaseInteractionException;

import java.util.List;
import java.util.Map;

/**
 * UserDao interface with all operations
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public interface UserDao extends Dao<User> {

    /**
     * Insert into table 'users_courses'
     *
     * @param user   - user
     * @param course - course
     * @param mark   - user's mark by this course
     * @throws DataBaseInteractionException
     */
    void createUserCourse(User user, Course course, int mark) throws DataBaseInteractionException;

    /**
     * Update values in table 'users_courses'
     *
     * @param user   - user
     * @param course - course
     * @param mark   - user's mark by this course
     * @throws DataBaseInteractionException
     */
    void updateUserCourse(User user, Course course, int mark) throws DataBaseInteractionException;

    /**
     * Find user by login
     *
     * @param login - user login
     * @return user
     * @throws DataBaseInteractionException
     */
    User findByLogin(String login) throws DataBaseInteractionException;

    /**
     * Find by email
     *
     * @param email - user email
     * @return user
     * @throws DataBaseInteractionException
     */
    User findByEmail(String email) throws DataBaseInteractionException;

    /**
     * Find all students by course id
     *
     * @param courseId - course id
     * @return map of user and user's mark
     * @throws DataBaseInteractionException
     */
    Map<User, Integer> findAllStudentsByCourseId(Long courseId) throws DataBaseInteractionException;

    /**
     * Find all teachers
     *
     * @return list of teachers
     */
    List<User> findAllTeachers();

    /**
     * Find all students
     *
     * @return list of students
     */
    List<User> findAllStudents();

    /**
     * Find teachers ot students or all with offset
     *
     * @param offset         - offset
     * @param recordsPerPage - records on page
     * @param filePath       - path to file
     * @return list of users
     * @throws DataBaseInteractionException
     */
    List<User> findTeachersOrStudentsOrAllWithOffset(int offset, int recordsPerPage, String filePath) throws DataBaseInteractionException;
}
