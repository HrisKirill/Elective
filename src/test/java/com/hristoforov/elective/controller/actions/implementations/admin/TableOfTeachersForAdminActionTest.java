package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRA_JSPFiles.TABLE_OF_TEACHERS_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.CURRENT_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.EDIT_TEACHER;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TableOfTeachersForAdminActionTest {
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
        when(request.getRequestDispatcher(TABLE_OF_TEACHERS_PAGE)).thenReturn(rd);
        new TableOfTeachersForAdminAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoGetPageNotNull() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_PAGE)).thenReturn(1);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(request.getRequestDispatcher(TABLE_OF_TEACHERS_PAGE)).thenReturn(rd);
        new TableOfTeachersForAdminAction(appContext).executeDoGet(request, response);
    }

    @ParameterizedTest
    @CsvSource({"previous","next"})
    void testExecuteDoPost(String page) throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(request.getParameter(EDIT_TEACHER)).thenReturn(String.valueOf(1));
        when(request.getParameter(page)).thenReturn(String.valueOf(1));
        new TableOfTeachersForAdminAction(appContext).executeDoPost(request, response);
    }
}