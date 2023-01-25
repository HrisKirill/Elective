package com.hristoforov.elective.controller.actions.implementations.student;

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

import static com.hristoforov.elective.Constants.getTestUser;
import static com.hristoforov.elective.constants.CRA_JSPFiles.TABLE_FOR_STUDENT_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoursesTableForStudentActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final UserDao userDao = mock(UserDao.class);
    private final CourseDao courseDao = mock(CourseDao.class);
    private final TopicDao topicDao = mock(TopicDao.class);;

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_USER)).thenReturn(getTestUser());
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(userDao.findByLogin(getTestUser().getLogin())).thenReturn(getTestUser());
        when(session.getAttribute(NUMBER_OF_PAGES)).thenReturn(null);
        when(appContext.getTopicDao()).thenReturn(topicDao);
        when(session.getAttribute(COURSE_MAP)).thenReturn(null);
        when(request.getRequestDispatcher(TABLE_FOR_STUDENT_PAGE)).thenReturn(rd);
        new CoursesTableForStudentAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoGetPageNotNull() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_USER)).thenReturn(getTestUser());
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(session.getAttribute(CURRENT_PAGE)).thenReturn(1);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(userDao.findByLogin(getTestUser().getLogin())).thenReturn(getTestUser());
        when(session.getAttribute(NUMBER_OF_PAGES)).thenReturn(null);
        when(appContext.getTopicDao()).thenReturn(topicDao);
        when(session.getAttribute(COURSE_MAP)).thenReturn(null);
        when(request.getRequestDispatcher(TABLE_FOR_STUDENT_PAGE)).thenReturn(rd);
        new CoursesTableForStudentAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoPost() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_USER)).thenReturn(getTestUser());
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(appContext.getUserDao()).thenReturn(userDao);
        new CoursesTableForStudentAction(appContext).executeDoPost(request, response);
    }
}