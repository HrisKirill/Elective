package com.hristoforov.elective.controller.actions.implementations.teacher;

import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.services.emailSending.EmailSender;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.Constants.getTestCourse;
import static com.hristoforov.elective.Constants.getTestUser;
import static com.hristoforov.elective.constants.CRA_JSPFiles.EDIT_MARK_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EditMarkActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final UserDao userDao = mock(UserDao.class);
    private final EmailSender emailSender = mock(EmailSender.class);

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(EDIT_MARK_PAGE)).thenReturn(rd);
        new EditMarkAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoPost() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(appContext.getEmailSender()).thenReturn(emailSender);
        when(request.getParameter(MARKS)).thenReturn(String.valueOf(28));
        when(session.getAttribute(USER_TO_EDIT)).thenReturn(getTestUser());
        when(session.getAttribute(COURSE)).thenReturn(getTestCourse());
        when(session.getAttribute(USERS_COURSE_TO_EDIT)).thenReturn(getTestCourse());
        new EditMarkAction(appContext).executeDoPost(request, response);
    }
}