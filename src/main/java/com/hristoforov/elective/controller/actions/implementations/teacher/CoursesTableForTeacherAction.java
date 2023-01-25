package com.hristoforov.elective.controller.actions.implementations.teacher;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.utils.DoPostForUserAndTeacherTables;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.TABLE_OF_COURSES_FOR_TEACHER_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.TABLE_FOR_TEACHER_PAGE;
import static com.hristoforov.elective.constants.CommonConstants.RECORDS_PER_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class CoursesTableForTeacherAction implements Action {
    private final UserDao userDao;
    private final CourseDao courseDao;
    int page;

    public CoursesTableForTeacherAction(AppContext appContext) {
        userDao = appContext.getUserDao();
        courseDao = appContext.getCourseDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = userDao.findByLogin(((User) session.getAttribute(CURRENT_USER)).getLogin());

        if (session.getAttribute(CURRENT_PAGE) != null) {
            page = (int) session.getAttribute(CURRENT_PAGE);
        } else {
            page = 1;
        }

        if (session.getAttribute(NUMBER_OF_PAGES) == null) {
            request.setAttribute(NUMBER_OF_PAGES,
                    (courseDao.findAllCoursesByUserId(user.getId()).size() + RECORDS_PER_PAGE - 1) / RECORDS_PER_PAGE);
        }

        if (session.getAttribute(COURSE_MAP) == null) {
            request.setAttribute(COURSE_MAP, courseDao.findAllCoursesByUserIdWithOffset
                    (user.getId(), (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE));
        }

        request.setAttribute(CURRENT_PAGE, page);
        request.getRequestDispatcher(TABLE_FOR_TEACHER_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DoPostForUserAndTeacherTables.doPostForTables(userDao, courseDao, request, response, TABLE_OF_COURSES_FOR_TEACHER_SERVLET);
    }
}
