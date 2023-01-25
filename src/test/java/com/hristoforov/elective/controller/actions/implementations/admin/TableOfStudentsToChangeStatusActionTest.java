package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.enums.Status;
import com.hristoforov.elective.entities.user.User;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.Constants.getTestUser;
import static com.hristoforov.elective.constants.CRA_JSPFiles.TABLE_OF_STUDENTS_TO_CHANGE_STATUS_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TableOfStudentsToChangeStatusActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final UserDao userDao = mock(UserDao.class);

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(request.getRequestDispatcher(TABLE_OF_STUDENTS_TO_CHANGE_STATUS_PAGE)).thenReturn(rd);
        new TableOfStudentsToChangeStatusAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoGetPageNotNull() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_PAGE)).thenReturn(1);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(request.getRequestDispatcher(TABLE_OF_STUDENTS_TO_CHANGE_STATUS_PAGE)).thenReturn(rd);
        new TableOfStudentsToChangeStatusAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoPostStatusUnblocked() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(userDao.findById(1L)).thenReturn(getTestUser());
        when(request.getParameter(STATUS_STUDENT_ID)).thenReturn(String.valueOf(1));
        when(request.getParameter(PREVIOUS)).thenReturn(String.valueOf(1));
        new TableOfStudentsToChangeStatusAction(appContext).executeDoPost(request, response);
    }

    @Test
    void testExecuteDoPostStatusBlocked() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(userDao.findById(1L)).thenReturn(new User.Builder().id(1L).status(Status.BLOCKED).build());
        when(request.getParameter(STATUS_STUDENT_ID)).thenReturn(String.valueOf(1));
        when(request.getParameter(NEXT)).thenReturn(String.valueOf(1));
        new TableOfStudentsToChangeStatusAction(appContext).executeDoPost(request, response);
    }

}