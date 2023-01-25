package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.actions.implementations.general.RegistrationAction;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.dao.interfaces.UserDao;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.Constants.*;
import static com.hristoforov.elective.Constants.getTestCourse;
import static com.hristoforov.elective.constants.CRA_JSPFiles.ADD_COURSE_PAGE;
import static com.hristoforov.elective.constants.CRA_JSPFiles.REGISTRATION_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.CORRECT_COURSE;
import static com.hristoforov.elective.constants.HttpAttributes.TOPICS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddCourseActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final TopicDao topicDao = mock(TopicDao.class);
    private final CourseDao courseDao = mock(CourseDao.class);

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(ADD_COURSE_PAGE)).thenReturn(rd);
        when(appContext.getTopicDao()).thenReturn(topicDao);
        new AddCourseAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoPost() throws ServletException, IOException {
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(appContext.getTopicDao()).thenReturn(topicDao);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CORRECT_COURSE)).thenReturn(getTestCourse());
        when(request.getParameter(TOPICS)).thenReturn(String.valueOf(1));
        new AddCourseAction(appContext).executeDoPost(request, response);
    }

    @Test
    void testExecuteDoPostCourseNull() throws ServletException, IOException {
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CORRECT_COURSE)).thenReturn(null);
        new AddCourseAction(appContext).executeDoPost(request, response);
    }

    @Test
    void testExecuteDoPostTopicNone() throws ServletException, IOException {
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CORRECT_COURSE)).thenReturn(getTestCourse());
        when(request.getParameter(TOPICS)).thenReturn("None");
        new AddCourseAction(appContext).executeDoPost(request, response);
    }
}