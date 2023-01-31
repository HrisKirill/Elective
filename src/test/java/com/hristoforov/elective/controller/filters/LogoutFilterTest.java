package com.hristoforov.elective.controller.filters;

import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.Constants.getTestUser;
import static com.hristoforov.elective.constants.CRAPaths.LOGIN_PAGE_SERVLET;
import static com.hristoforov.elective.constants.HttpAttributes.CURRENT_USER;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogoutFilterTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final FilterChain chain = mock(FilterChain.class);
    private final HttpSession session = mock(HttpSession.class);

    @Test
    void doFilterTestUserIsNotNull() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn(LOGIN_PAGE_SERVLET);
        when(request.getContextPath()).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_USER)).thenReturn(getTestUser());
        new LogoutFilter().doFilter(request,response,chain);
    }

    @Test
    void doFilterTestUserIsNull() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn(LOGIN_PAGE_SERVLET);
        when(request.getContextPath()).thenReturn("");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_USER)).thenReturn(null);
        new LogoutFilter().doFilter(request,response,chain);
    }
}