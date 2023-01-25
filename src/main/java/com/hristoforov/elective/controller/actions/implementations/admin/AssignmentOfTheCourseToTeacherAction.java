package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.TABLE_OF_TEACHERS_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.ASSIGMENT_OF_THE_COURSE_TO_TEACHER_PAGE;
import static com.hristoforov.elective.constants.CommonConstants.ZERO;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class AssignmentOfTheCourseToTeacherAction implements Action {
    private  final CourseDao courseDao;
    private  final UserDao userDao;

    public AssignmentOfTheCourseToTeacherAction(AppContext appContext) {
        courseDao = appContext.getCourseDao();
        userDao = appContext.getUserDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(COURSES_WITHOUT_TEACHERS,
                courseDao.coursesWithoutTeachers(userDao.findAllTeachers()));
        request.getRequestDispatcher(ASSIGMENT_OF_THE_COURSE_TO_TEACHER_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (request.getParameter(EDIT_COURSE_FOR_TEACHER) != null
                && !request.getParameter(EDIT_COURSE_FOR_TEACHER).equals("None")) {
            userDao.createUserCourse(userDao.findById(Long.valueOf((String) session.getAttribute(ID_TEACHER_TO_EDIT))),
                    courseDao.findById(Long.valueOf(request.getParameter(EDIT_COURSE_FOR_TEACHER))), ZERO);
        }
        response.sendRedirect(TABLE_OF_TEACHERS_SERVLET);
    }
}
