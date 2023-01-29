package com.hristoforov.elective.controller.actions.implementations.teacher;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.services.emailSending.EmailSender;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.USERS_TABLES_FOR_TEACHER_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.EDIT_MARK_PAGE;
import static com.hristoforov.elective.constants.EmailConstants.EMAIL_SUBJECT;
import static com.hristoforov.elective.constants.EmailConstants.MESSAGE_RATE_STUDENT;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class EditMarkAction implements Action {
    private final UserDao userDao;
    private final EmailSender emailSender;

    public EditMarkAction(AppContext appContext) {
        userDao = appContext.getUserDao();
        emailSender = appContext.getEmailSender();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(EDIT_MARK_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_TO_EDIT);
        Course course = (Course) session.getAttribute(COURSE);
        int mark = Integer.parseInt(request.getParameter(MARKS));
        userDao.updateUserCourse(user, course, mark);

        String content = String.format(MESSAGE_RATE_STUDENT, user.getFirstName(), mark, course.getTitle());
        new Thread(() -> emailSender.sendEmail(user.getEmail(), EMAIL_SUBJECT, content));
        session.setAttribute(USER_MAP,
                userDao.findAllStudentsByCourseId(((Course) session.getAttribute(USERS_COURSE_TO_EDIT)).getId()));
        response.sendRedirect(USERS_TABLES_FOR_TEACHER_SERVLET);
    }
}
