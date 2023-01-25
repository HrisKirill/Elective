package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.TABLE_OF_TEACHERS_SERVLET;
import static com.hristoforov.elective.constants.CRAPaths.TEACHER_REGISTRATION_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.TEACHER_REGISTRATION_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class AdminTeacherSignUpAction implements Action {
    private  final UserDao userDao;
    private final CourseDao courseDao;

    public AdminTeacherSignUpAction(AppContext appContext) {
        userDao = appContext.getUserDao();
        courseDao = appContext.getCourseDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(COURSES_WITHOUT_TEACHERS,
                courseDao.coursesWithoutTeachers(userDao.findAllTeachers()));
        request.getRequestDispatcher(TEACHER_REGISTRATION_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CORRECT_USER);

        if (user != null) {
            userDao.create(user);
            if (!request.getParameter(SET_COURSE_FOR_TEACHER).equalsIgnoreCase("None")) {
                userDao.createUserCourse(userDao.findByLogin(user.getLogin()),
                        courseDao.findById(Long.valueOf(request.getParameter(SET_COURSE_FOR_TEACHER))), 0);
            }

            response.sendRedirect(TABLE_OF_TEACHERS_SERVLET);
        } else {
            response.sendRedirect(TEACHER_REGISTRATION_SERVLET);
        }
    }
}
