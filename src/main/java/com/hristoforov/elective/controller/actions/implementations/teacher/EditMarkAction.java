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

import static com.hristoforov.elective.constants.CRAPaths.USERS_TABLES_FOR_TEACHER_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.EDIT_MARK_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class EditMarkAction implements Action {
    private final UserDao userDao;

    public EditMarkAction(AppContext appContext) {
        userDao = appContext.getUserDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(EDIT_MARK_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        userDao.updateUserCourse(
                (User) session.getAttribute(USER_TO_EDIT),
                (Course) session.getAttribute(COURSE),
                Integer.parseInt(request.getParameter(MARKS))
        );
        session.setAttribute(USER_MAP,
                userDao.findAllStudentsByCourseId(((Course) session.getAttribute(USERS_COURSE_TO_EDIT)).getId()));
        response.sendRedirect(USERS_TABLES_FOR_TEACHER_SERVLET);
    }
}
