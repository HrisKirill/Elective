package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.services.emailSending.EmailSender;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.EDIT_COURSE_SERVLET;
import static com.hristoforov.elective.constants.CRAPaths.TABLE_OF_COURSES_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.EDIT_COURSE_PAGE;
import static com.hristoforov.elective.constants.EmailConstants.EMAIL_SUBJECT;
import static com.hristoforov.elective.constants.EmailConstants.MESSAGE_EDIT_COURSE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class EditCourseAction implements Action {
    private final TopicDao topicDao;
    private final CourseDao courseDao;
    private final UserDao userDao;
    private final EmailSender emailSender;

    public EditCourseAction(AppContext appContext) {
        topicDao = appContext.getTopicDao();
        courseDao = appContext.getCourseDao();
        userDao = appContext.getUserDao();
        emailSender = appContext.getEmailSender();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute(TOPIC_LIST, topicDao.topicsThatDoNotIncludeTheCourse(
                ((Course) session.getAttribute(EDIT_COURSE)).getId()));
        request.getRequestDispatcher(EDIT_COURSE_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Course course = (Course) session.getAttribute(CORRECT_COURSE);
        if (course != null) {
            courseDao.update(course);
            if (!request.getParameter(TOPICS).equalsIgnoreCase("None")) {
                topicDao.createTopicCourse(topicDao.findById(Long.valueOf(request.getParameter(TOPICS))),
                        course);
            }
            Course updatedCourse = courseDao.findByTitle(course.getTitle());

            userDao.findAllStudentsByCourseId(updatedCourse.getId()).keySet().forEach(user -> new Thread(() -> {
                String content = String.format(MESSAGE_EDIT_COURSE,
                        user.getFirstName(),
                        course.getTitle(),
                        updatedCourse.getTitle(),
                        updatedCourse.getDuration(),
                        updatedCourse.getStartDate().toString(),
                        updatedCourse.getEndDate().toString());
                emailSender.sendEmail(user.getEmail(), EMAIL_SUBJECT, content);
            }).start());

            response.sendRedirect(TABLE_OF_COURSES_SERVLET);
        } else {
            response.sendRedirect(EDIT_COURSE_SERVLET);
        }
    }
}
