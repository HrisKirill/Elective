package com.hristoforov.elective.controller.actions.implementations.general;

import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.services.emailSending.EmailSender;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;

import static com.hristoforov.elective.Constants.*;
import static com.hristoforov.elective.constants.CRA_JSPFiles.*;
import static com.hristoforov.elective.constants.HttpAttributes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserInfoActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final UserDao userDao = mock(UserDao.class);
    private final CourseDao courseDao = mock(CourseDao.class);
    private final EmailSender emailSender = mock(EmailSender.class);

    @Test
    void testExecuteDoGetPageNull() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(appContext.getEmailSender()).thenReturn(emailSender);
        when(session.getAttribute(CURRENT_USER)).thenReturn(new User.Builder()
                .id(ID_VALUE)
                .lastName(LAST_NAME_VALUE)
                .firstName(FIRST_NAME_VALUE)
                .login(LOGIN_VALUE)
                .password(PASSWORD_VALUE)
                .email(EMAIL_VALUE)
                .role(ROLE_VALUE)
                .status(STATUS_VALUE)
                .build());
        when(request.getRequestDispatcher(USER_INFO_PAGE)).thenReturn(rd);
        new UserInfoAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoGetPageNotNull() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(appContext.getEmailSender()).thenReturn(emailSender);
        when(session.getAttribute(CURRENT_PAGE_FOR_USER_INFO)).thenReturn(2);
        when(session.getAttribute(CURRENT_USER)).thenReturn(getTestUser());
        when(request.getRequestDispatcher(USER_INFO_PAGE)).thenReturn(rd);
        new UserInfoAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoPostCourseNotNull() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(appContext.getEmailSender()).thenReturn(emailSender);
        when(session.getAttribute(CURRENT_USER)).thenReturn(getTestUser());
        when(request.getParameter(JOIN_TO_COURSE)).thenReturn(String.valueOf(1));
        when(courseDao.findById(Long.valueOf(request.getParameter(JOIN_TO_COURSE)))).thenReturn(getTestCourse());
        new UserInfoAction(appContext).executeDoPost(request, response);
    }

    @Test
    void testPreviousPage() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(PREVIOUS)).thenReturn(String.valueOf(2));
        new UserInfoAction(appContext).executeDoPost(request, response);
    }

    @Test
    void testNextPage() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(NEXT)).thenReturn(String.valueOf(2));
        new UserInfoAction(appContext).executeDoPost(request, response);
    }

}