package com.hristoforov.elective.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.*;
import static com.hristoforov.elective.constants.HttpAttributes.CURRENT_USER;

/**
 * LogoutFilter to check that user can not log out from search bar
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
@WebFilter(filterName = "LogoutFilter", urlPatterns = {LOGIN_PAGE_SERVLET, REGISTRATION_SERVLET})
public class LogoutFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String uri = httpServletRequest.getRequestURI();
        uri = uri.substring(httpServletRequest.getContextPath().length());

        if (httpServletRequest.getSession().getAttribute(CURRENT_USER) != null
                && (uri.startsWith(LOGIN_PAGE_SERVLET) || uri.startsWith(REGISTRATION_SERVLET))) {
            httpServletResponse.sendRedirect(USER_INFO_PAGE_SERVLET);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}
