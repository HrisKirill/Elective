package com.hristoforov.elective.controller.filters;

import com.hristoforov.elective.entities.enums.Role;
import com.hristoforov.elective.entities.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.*;
import static com.hristoforov.elective.constants.HttpAttributes.CURRENT_USER;

/**
 * AuthorizationFilter to check if the user has access to a certain page,
 * if not, returns to the page with information about the user
 * if the user is registered, and to the login page if not
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {START_PATH_FOR_ADMIN + "/*", START_PATH_FOR_TEACHER + "/*",
        TABLE_FOR_STUDENT_SERVLET, USER_INFO_PAGE_SERVLET})
public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpServletRequest.getSession();

        User currentUser = (User) httpSession.getAttribute(CURRENT_USER);
        String uri = httpServletRequest.getRequestURI();
        String contextPath = httpServletRequest.getContextPath();
        uri = uri.substring(contextPath.length());
        if (currentUser == null && !(uri.startsWith(LOGIN_PAGE_SERVLET) || uri.startsWith(REGISTRATION_SERVLET))) {
            LOGGER.info("access is denied");
            httpServletResponse.sendRedirect(LOGIN_PAGE_SERVLET);
        } else if (currentUser != null && currentUser.getRole().name().equalsIgnoreCase(Role.SYSADMIN.name())
                && !(uri.startsWith(START_PATH_FOR_ADMIN) || uri.startsWith(USER_INFO_PAGE_SERVLET))) {
            LOGGER.info("access is denied");
            httpServletResponse.sendRedirect(USER_INFO_PAGE_SERVLET);
        } else if (currentUser != null && currentUser.getRole().name().equalsIgnoreCase(Role.TEACHER.name())
                && !(uri.startsWith(START_PATH_FOR_TEACHER) || uri.startsWith(USER_INFO_PAGE_SERVLET))) {
            LOGGER.info("access is denied");
            httpServletResponse.sendRedirect(USER_INFO_PAGE_SERVLET);
        } else if (currentUser != null && currentUser.getRole().name().equalsIgnoreCase(Role.STUDENT.name())
                && !(uri.startsWith(START_PATH_FOR_STUDENT) || uri.startsWith(USER_INFO_PAGE_SERVLET))) {
            LOGGER.info("access is denied");
            httpServletResponse.sendRedirect(USER_INFO_PAGE_SERVLET);
        } else {
            LOGGER.info("access is allowed");
            chain.doFilter(request, response);
        }
    }
}
