package com.hristoforov.elective.controller.actions.implementations.teacher;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.EDIT_MARK_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.USERS_TABLE_FOR_TEACHER_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class StudentsTableForTeacherAction implements Action {
    private final UserDao userDao;

    public StudentsTableForTeacherAction(AppContext appContext) {
        userDao = appContext.getUserDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(USERS_TABLE_FOR_TEACHER_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = userDao.findById(Long.valueOf(request.getParameter(EDIT_MARK)));
        Course course = (Course) session.getAttribute(COURSE);
        session.setAttribute(USER_TO_EDIT, user);
        session.setAttribute(USERS_COURSE_TO_EDIT, course);
        response.sendRedirect(EDIT_MARK_SERVLET);
    }
}
