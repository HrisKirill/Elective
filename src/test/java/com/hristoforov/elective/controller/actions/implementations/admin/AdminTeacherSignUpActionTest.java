package com.hristoforov.elective.controller.actions.implementations.admin;

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

import static com.hristoforov.elective.Constants.getTestTopic;
import static com.hristoforov.elective.Constants.getTestUser;
import static com.hristoforov.elective.constants.CRA_JSPFiles.TEACHER_REGISTRATION_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdminTeacherSignUpActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final TopicDao topicDao = mock(TopicDao.class);
    private final UserDao userDao = mock(UserDao.class);
    private final CourseDao courseDao = mock(CourseDao.class);

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(request.getRequestDispatcher(TEACHER_REGISTRATION_PAGE)).thenReturn(rd);
        new AdminTeacherSignUpAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoPost() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CORRECT_USER)).thenReturn(getTestUser());
        when(request.getParameter(SET_COURSE_FOR_TEACHER)).thenReturn(String.valueOf(1));
        when(appContext.getUserDao()).thenReturn(userDao);
        when(appContext.getCourseDao()).thenReturn(courseDao);
        new AdminTeacherSignUpAction(appContext).executeDoPost(request, response);
    }

    @Test
    void testExecuteDoPostUserNull() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CORRECT_USER)).thenReturn(null);
        new AdminTeacherSignUpAction(appContext).executeDoPost(request, response);
    }
}