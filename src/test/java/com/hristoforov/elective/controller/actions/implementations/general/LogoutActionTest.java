package com.hristoforov.elective.controller.actions.implementations.general;

import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.LOGIN_PAGE_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.ERROR_PAGE;
import static com.hristoforov.elective.constants.CRA_JSPFiles.LOGIN_PAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogoutActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(session);
        session.setAttribute("1",1);
        when(request.getRequestDispatcher(LOGIN_PAGE)).thenReturn(rd);
        when(request.getSession(true)).thenReturn(session);
        new LogoutAction().executeDoGet(request, response);
    }


}