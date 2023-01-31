package com.hristoforov.elective.controller.filters;

import com.hristoforov.elective.entities.enums.Role;
import com.hristoforov.elective.entities.user.User;
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

class AuthorizationFilterTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final FilterChain chain = mock(FilterChain.class);
    private final HttpSession session = mock(HttpSession.class);

    @Test
    void doFilterTestUserStudentAccessDenied() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_USER)).thenReturn(getTestUser());
        when(request.getRequestURI()).thenReturn("");
        when(request.getContextPath()).thenReturn("");
        new AuthorizationFilter().doFilter(request, response, chain);
    }

    @Test
    void doFilterTestUserNullAccessDenied() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_USER)).thenReturn(null);
        when(request.getRequestURI()).thenReturn("");
        when(request.getContextPath()).thenReturn("");
        new AuthorizationFilter().doFilter(request, response, chain);
    }

    @Test
    void doFilterTestUserTeacherAccessDenied() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_USER)).thenReturn(new User.Builder().role(Role.TEACHER).build());
        when(request.getRequestURI()).thenReturn("");
        when(request.getContextPath()).thenReturn("");
        new AuthorizationFilter().doFilter(request, response, chain);
    }

    @Test
    void doFilterTestUserAdminAccessDenied() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_USER)).thenReturn(new User.Builder().role(Role.SYSADMIN).build());
        when(request.getRequestURI()).thenReturn("");
        when(request.getContextPath()).thenReturn("");
        new AuthorizationFilter().doFilter(request, response, chain);
    }

    @Test
    void doFilterTestAccessAllowed() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_USER)).thenReturn(null);
        when(request.getRequestURI()).thenReturn(LOGIN_PAGE_SERVLET);
        when(request.getContextPath()).thenReturn("");
        new AuthorizationFilter().doFilter(request, response, chain);
    }
}