package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.TopicDao;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.Constants.getTestCourse;
import static com.hristoforov.elective.constants.CRA_JSPFiles.EDIT_COURSE_PAGE;
import static com.hristoforov.elective.constants.CRA_JSPFiles.TABLE_OF_COURSES_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TableOfCoursesForAdminActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final CourseDao courseDao = mock(CourseDao.class);

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(SORT_WAY_FOR_PAGINATION)).thenReturn("1");
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(request.getRequestDispatcher(TABLE_OF_COURSES_PAGE)).thenReturn(rd);
        new TableOfCoursesForAdminAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoGetSortNull() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(SORT_WAY_FOR_PAGINATION)).thenReturn(null);
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(request.getRequestDispatcher(TABLE_OF_COURSES_PAGE)).thenReturn(rd);
        new TableOfCoursesForAdminAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoGetCurrentPageNotNull() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CURRENT_PAGE)).thenReturn(2);
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(request.getRequestDispatcher(TABLE_OF_COURSES_PAGE)).thenReturn(rd);
        new TableOfCoursesForAdminAction(appContext).executeDoGet(request, response);
    }


    @Test
    void testExecuteDoPostToEditCourse() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(request.getParameter(EDIT_COURSE_ID)).thenReturn(String.valueOf(1));
        when(request.getParameter(NEXT)).thenReturn(String.valueOf(1));
        new TableOfCoursesForAdminAction(appContext).executeDoPost(request, response);
    }

    @Test
    void testExecuteDoPostToDeleteCourse() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(request.getParameter(DELETE_COURSE_ID)).thenReturn(String.valueOf(1));
        when(request.getParameter(PREVIOUS)).thenReturn(String.valueOf(1));
        new TableOfCoursesForAdminAction(appContext).executeDoPost(request, response);
    }

    @Test
    void testExecuteDoPostSort() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(OK)).thenReturn(String.valueOf(1));
        when(request.getParameter(SORT)).thenReturn(String.valueOf(1));
        new TableOfCoursesForAdminAction(appContext).executeDoPost(request, response);
    }
}