package com.hristoforov.elective.utils;

import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.entities.course.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.hristoforov.elective.constants.CRA_QueriesFiles.*;

public class SortAndSelect {


    public static List<Course> sortAdminTableBySomeValue(CourseDao courseDao, String sortingWay, int offset, int recordsPerPage) {
        List<Course> courses = new ArrayList<>();
        switch (sortingWay) {
            case "sortByTitle":
                courses = courseDao.sortCoursesBySomeValueWithOffset(offset, recordsPerPage, SORT_COURSES_BY_TITLE);
                break;
            case "sortByDuration":
                courses = courseDao.sortCoursesBySomeValueWithOffset(offset, recordsPerPage, SORT_COURSES_BY_DURATION);
                break;
            case "sortByNumberOfStudents":
                courses = courseDao.sortCoursesBySomeValueWithOffset(offset, recordsPerPage, SORT_BY_NUMBER_OF_STUDENTS_ENROLLED_IN_THE_COURSE);
                break;
        }

        return courses;
    }

    public static Map<Course, Integer> selectTableBySomeValue(CourseDao courseDao, Long userId, String selectWay, int offset, int recordsPerPage) {
        Map<Course, Integer> resultMap;
        switch (selectWay) {
            case "all":
                resultMap = courseDao.findAllCoursesByUserIdWithOffset(userId, offset, recordsPerPage);
                break;
            case "doNotStart":
                resultMap = courseDao.selectBySomeValueWithOffset(userId, offset, recordsPerPage, SELECT_NOT_STARTING_COURSES_WITH_OFFSET);
                break;
            case "stillOngoing":
                resultMap = courseDao.selectBySomeValueWithOffset(userId, offset, recordsPerPage, SELECT_STILL_ONGOING_COURSES_WITH_OFFSET);
                break;
            case "completedCourses":
                resultMap = courseDao.selectBySomeValueWithOffset(userId, offset, recordsPerPage, SELECT_COMPLETED_COURSES_WITH_OFFSET);
                break;
            default:
                resultMap = courseDao.findAllCoursesByTopicAndUserIdsWithOffset(Long.valueOf(selectWay), userId, offset, recordsPerPage);
                break;
        }

        return resultMap;
    }

    public static Map<Course, Integer> getSelectingTableWithoutOffset(CourseDao courseDao,Long userId, String selectWay) {
        Map<Course, Integer> resultMap;
        switch (selectWay) {
            case "all":
                resultMap = courseDao.findAllCoursesByUserId(userId);
                break;
            case "doNotStart":
                resultMap = courseDao.selectCoursesBySomeValueWithoutOffset(userId, SELECT_NOT_STARTING_COURSES_WITHOUT_OFFSET);
                break;
            case "stillOngoing":
                resultMap = courseDao.selectCoursesBySomeValueWithoutOffset(userId, SELECT_STILL_ONGOING_COURSES_WITHOUT_OFFSET);
                break;
            case "completedCourses":
                resultMap = courseDao.selectCoursesBySomeValueWithoutOffset(userId, SELECT_COMPLETED_COURSES_WITHOUT_OFFSET);
                break;
            default:
                resultMap = courseDao.findAllCoursesByTopicAndUserId(Long.valueOf(selectWay), userId);
                break;
        }

        return resultMap;
    }
}
