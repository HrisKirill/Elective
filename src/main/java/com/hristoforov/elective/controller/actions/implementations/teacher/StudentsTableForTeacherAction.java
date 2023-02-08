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
import static com.hristoforov.elective.constants.CRAPaths.USERS_TABLES_FOR_TEACHER_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.USERS_TABLE_FOR_TEACHER_PAGE;
import static com.hristoforov.elective.constants.CommonConstants.RECORDS_PER_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class StudentsTableForTeacherAction implements Action {
    private final UserDao userDao;
    int page;

    public StudentsTableForTeacherAction(AppContext appContext) {
        userDao = appContext.getUserDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(CURRENT_PAGE_STUDENTS_TABLE_FOR_TEACHER) != null) {
            page = (int) session.getAttribute(CURRENT_PAGE_STUDENTS_TABLE_FOR_TEACHER);
        } else {
            page = 1;
        }

        request.setAttribute(NUMBER_OF_PAGES_STUDENTS_TABLE_FOR_TEACHER,
                (userDao.findAllStudentsByCourseId((Long) session.getAttribute(COURSE_ID)).size() + RECORDS_PER_PAGE - 1) / RECORDS_PER_PAGE);

        request.setAttribute(USER_MAP,
                userDao.findAllStudentsByCourseIdWithOffset((Long) session.getAttribute(COURSE_ID),
                        (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE));


        request.setAttribute(CURRENT_PAGE_STUDENTS_TABLE_FOR_TEACHER, page);
        request.getRequestDispatcher(USERS_TABLE_FOR_TEACHER_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (request.getParameter(PREVIOUS) != null) {
            page = Integer.parseInt(request.getParameter(PREVIOUS));
            session.setAttribute(CURRENT_PAGE_STUDENTS_TABLE_FOR_TEACHER,page);
            response.sendRedirect(USERS_TABLES_FOR_TEACHER_SERVLET);
        } else if (request.getParameter(NEXT) != null) {
            page = Integer.parseInt(request.getParameter(NEXT));
            session.setAttribute(CURRENT_PAGE_STUDENTS_TABLE_FOR_TEACHER,page);
            response.sendRedirect(USERS_TABLES_FOR_TEACHER_SERVLET);
        }

        if(request.getParameter(EDIT_MARK)!= null){
            User user = userDao.findById(Long.valueOf(request.getParameter(EDIT_MARK)));
            Course course = (Course) session.getAttribute(COURSE);
            session.setAttribute(USER_TO_EDIT, user);
            session.setAttribute(USERS_COURSE_TO_EDIT, course);
            response.sendRedirect(EDIT_MARK_SERVLET);
        }

    }
}
