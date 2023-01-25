package com.hristoforov.elective.controller.filters;

import com.hristoforov.elective.entities.enums.Role;
import com.hristoforov.elective.entities.user.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.*;
import static com.hristoforov.elective.constants.HttpAttributes.CURRENT_USER;

@WebFilter(filterName = "LogoutFilter", urlPatterns = {LOGIN_PAGE_SERVLET, REGISTRATION_SERVLET})
public class LogoutFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession httpSession = httpServletRequest.getSession();
        String uri = httpServletRequest.getRequestURI();
        uri = uri.substring(httpServletRequest.getContextPath().length());

        if (httpSession.getAttribute(CURRENT_USER) != null
                && (uri.startsWith(LOGIN_PAGE_SERVLET) || uri.startsWith(REGISTRATION_SERVLET))) {
            httpServletResponse.sendRedirect(USER_INFO_PAGE_SERVLET);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}
