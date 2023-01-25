package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.entities.course.Course;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.ADD_COURSE_SERVLET;
import static com.hristoforov.elective.constants.CRAPaths.TABLE_OF_COURSES_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.ADD_COURSE_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class AddCourseAction implements Action {
    private final CourseDao courseDao;
    private final TopicDao topicDao;

    public AddCourseAction(AppContext appContext) {
        courseDao = appContext.getCourseDao();
        topicDao = appContext.getTopicDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(TOPIC_LIST, topicDao.findAll());
        request.getRequestDispatcher(ADD_COURSE_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Course course = (Course) session.getAttribute(CORRECT_COURSE);
        if (course != null) {
            courseDao.create(course);
            if (!request.getParameter(TOPICS).equalsIgnoreCase("None")) {
                topicDao.createTopicCourse(topicDao.findById(Long.valueOf(request.getParameter(TOPICS))),
                        courseDao.findByTitle(course.getTitle()));
            }
            response.sendRedirect(TABLE_OF_COURSES_SERVLET);
        } else {
            response.sendRedirect(ADD_COURSE_SERVLET);
        }
    }
}
