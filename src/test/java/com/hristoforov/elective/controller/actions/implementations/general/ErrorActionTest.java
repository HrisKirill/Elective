package com.hristoforov.elective.controller.actions.implementations.general;

import com.hristoforov.elective.Constants;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.user.User;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.hristoforov.elective.Constants.*;
import static com.hristoforov.elective.Constants.STATUS_VALUE;
import static com.hristoforov.elective.constants.CRA_JSPFiles.ERROR_PAGE;
import static com.hristoforov.elective.constants.CRA_JSPFiles.REGISTRATION_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.CURRENT_USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ErrorActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(ERROR_PAGE)).thenReturn(rd);
        new ErrorAction().executeDoGet(request, response);
    }

    @Test
    void testExecuteDoPostUserNotNull() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
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
        when(request.getParameter("goBack")).thenReturn("goBack");
        new ErrorAction().executeDoPost(request, response);
    }

    @Test
    void testExecuteDoPostUserNull() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_USER)).thenReturn(null);
        when(request.getParameter("goBack")).thenReturn("goBack");
        new ErrorAction().executeDoPost(request, response);
    }

}