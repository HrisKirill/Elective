package com.hristoforov.elective.controller.filters;

import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.HttpAttributes.LOCALE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LocaleFilterTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final FilterChain chain = mock(FilterChain.class);
    private final FilterConfig filterConfig = mock(FilterConfig.class);
    private final HttpSession session = mock(HttpSession.class);

    @Test
    void initTest() {
        new LocaleFilter().init(filterConfig);
    }

    @Test
    void doFilterTestIsNotBlank() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getHeader("referer")).thenReturn("referer");
        when(request.getParameter(LOCALE)).thenReturn("en");
        new LocaleFilter().doFilter(request, response, chain);
    }

    @Test
    void doFilterTestIsBlank() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getHeader("referer")).thenReturn("referer");
        when(request.getParameter(LOCALE)).thenReturn(null);
        when(session.getAttribute(LOCALE)).thenReturn(null);
        new LocaleFilter().doFilter(request, response, chain);
    }

}