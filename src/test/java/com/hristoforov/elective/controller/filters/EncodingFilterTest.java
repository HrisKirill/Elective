package com.hristoforov.elective.controller.filters;

import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EncodingFilterTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final FilterChain chain = mock(FilterChain.class);
    private final FilterConfig filterConfig = mock(FilterConfig.class);

    @Test
    void initTest() {
        new EncodingFilter().init(filterConfig);
    }

    @Test
    void doFilterTest() throws ServletException, IOException {
        when(filterConfig.getInitParameter("encoding")).thenReturn("UTF-8");
        new EncodingFilter().doFilter(request, response, chain);
    }

}