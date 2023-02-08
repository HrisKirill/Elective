package com.hristoforov.elective.constants;

/**
 * Contains paths to sql query files
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public final class CRA_QueriesFiles {
    public static final String CREATE_USER = "queries/userQueries/createUser.sql";
    public static final String UPDATE_USER = "queries/userQueries/updateUser.sql";
    public static final String REMOVE_USER = "queries/userQueries/removeUser.sql";
    public static final String FIND_ALL_USERS = "queries/userQueries/findAllInUser.sql";
    public static final String FIND_USER_BY_ID = "queries/userQueries/findUserById.sql";
    public static final String FIND_USER_BY_LOGIN = "queries/userQueries/findUserByLogin.sql";
    public static final String FIND_USER_BY_EMAIL = "queries/userQueries/findUserByEmail.sql";
    public static final String FIND_ALL_USERS_BY_COURSE_ID = "queries/userQueries/findAllUsersByCourseId.sql";
    public static final String FIND_ALL_USERS_BY_COURSE_ID_WITH_OFFSET = "queries/userQueries/withOffset/findAllUsersByCourseIdWithOffset.sql";
    public static final String FIND_ALL_TEACHERS_WITH_OFFSET = "queries/userQueries/withOffset/findAllTeachersWithOffset.sql";
    public static final String FIND_ALL_STUDENTS_WITH_OFFSET = "queries/userQueries/withOffset/findAllStudentsWithOffset.sql";

    public static final String CREATE_COURSE = "queries/courseQueries/createCourse.sql";
    public static final String UPDATE_COURSE = "queries/courseQueries/updateCourse.sql";
    public static final String REMOVE_COURSE = "queries/courseQueries/removeCourse.sql";
    public static final String INCREMENT_COUNT_OF_STUDENT = "queries/courseQueries/incrementCountOfStudent.sql";
    public static final String FIND_ALL_COURSES = "queries/courseQueries/findAllCourses.sql";
    public static final String FIND_ALL_COURSES_WITH_OFFSET = "queries/courseQueries/withOffset/findAllCoursesWithOffSet.sql";
    public static final String FIND_COURSE_BY_ID = "queries/courseQueries/findCourseById.sql";
    public static final String FIND_COURSE_BY_TITLE = "queries/courseQueries/findCourseByTitle.sql";
    public static final String COURSES_IN_WHICH_THE_STUDENT_DOES_NOT_PARTICIPATE_WITH_OFFSET =
            "queries/courseQueries/withOffset/coursesInWhichTheStudentDoesNotParticipate.sql";

    public static final String SORT_COURSES_BY_TITLE = "queries/sorting/withOffset/sortCoursesByTitle.sql";
    public static final String SORT_COURSES_BY_DURATION = "queries/sorting/withOffset/sortCoursesByDuration.sql";
    public static final String SORT_BY_NUMBER_OF_STUDENTS_ENROLLED_IN_THE_COURSE =
            "queries/sorting/withOffset/sortByNumberOfStudentsEnrolledInTheCourse.sql";

    public static final String SELECT_NOT_STARTING_COURSES_WITH_OFFSET = "queries/selection/withOffset/notStartingCourses.sql";
    public static final String SELECT_STILL_ONGOING_COURSES_WITH_OFFSET = "queries/selection/withOffset/stillOngoingCourses.sql";
    public static final String SELECT_COMPLETED_COURSES_WITH_OFFSET = "queries/selection/withOffset/completedCourses.sql";

    public static final String SELECT_NOT_STARTING_COURSES_WITHOUT_OFFSET = "queries/selection/withoutOffset/notStartingCourses.sql";
    public static final String SELECT_STILL_ONGOING_COURSES_WITHOUT_OFFSET = "queries/selection/withoutOffset/stillOngoingCourses.sql";
    public static final String SELECT_COMPLETED_COURSES_WITHOUT_OFFSET = "queries/selection/withoutOffset/completedCourses.sql";

    public static final String FIND_ALL_COURSES_BY_USER_ID_WITH_OFFSET = "queries/courseQueries/withOffset/findAllCoursesByUsersIdWithOffset.sql";

    public static final String FIND_ALL_COURSES_BY_USER_ID = "queries/courseQueries/findAllCoursesByUserId.sql";
    public static final String FIND_ALL_COURSES_BY_TOPIC_ID = "queries/courseQueries/findAllCoursesByTopicId.sql";
    public static final String FIND_ALL_COURSES_BY_TOPIC_AND_USER_IDS = "queries/courseQueries/findAllCoursesByTopicAndUserId.sql";
    public static final String FIND_ALL_COURSES_BY_TOPIC_ID_WITH_OFFSET = "queries/courseQueries/withOffset/findAllCoursesByTopicIdWithOffset.sql";
    public static final String FIND_ALL_COURSES_BY_TOPIC_AND_USER_IDS_WITH_OFFSET = "queries/courseQueries/withOffset/findAllCoursesByTopicAndUserIdsWithOffset.sql";

    public static final String CREATE_TOPIC = "queries/topicQueries/createTopic.sql";
    public static final String UPDATE_TOPIC = "queries/topicQueries/updateTopic.sql";
    public static final String REMOVE_TOPIC = "queries/topicQueries/removeTopic.sql";
    public static final String FIND_ALL_TOPICS = "queries/topicQueries/findAllInTopic.sql";
    public static final String FIND_TOPIC_BY_ID = "queries/topicQueries/findTopicById.sql";
    public static final String FIND_TOPIC_BY_TITLE = "queries/topicQueries/findTopicByTitle.sql";

    public static final String CREATE_TOPIC_COURSE = "queries/manyToManyTopicCourse/createTopicCourse.sql";
    public static final String FIND_TOPICS_BY_COURSE = "queries/manyToManyTopicCourse/findTopicsByCourse.sql";

    public static final String CREATE_USER_COURSE = "queries/manyToManyUserCourse/createUserCourse.sql";
    public static final String UPDATE_USER_COURSE = "queries/manyToManyUserCourse/updateUserCourse.sql";


}
