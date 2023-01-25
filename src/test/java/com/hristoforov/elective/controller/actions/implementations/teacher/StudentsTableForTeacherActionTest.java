package com.hristoforov.elective.controller.actions.implementations.teacher;

import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
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
import static com.hristoforov.elective.constants.CRA_JSPFiles.USERS_TABLE_FOR_TEACHER_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentsTableForTeacherActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final UserDao userDao = mock(UserDao.class);

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(USERS_TABLE_FOR_TEACHER_PAGE)).thenReturn(rd);
        new StudentsTableForTeacherAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoPost() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(request.getParameter(EDIT_MARK)).thenReturn(String.valueOf(1));
        when(userDao.findById(1L)).thenReturn(getTestUser());
        when(session.getAttribute(COURSE)).thenReturn(getTestCourse());
        new StudentsTableForTeacherAction(appContext).executeDoPost(request, response);
    }
}