package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.services.emailSending.EmailSender;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.Constants.getTestCourse;
import static com.hristoforov.elective.Constants.getTestUser;
import static com.hristoforov.elective.constants.CRA_JSPFiles.ASSIGMENT_OF_THE_COURSE_TO_TEACHER_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.EDIT_COURSE_FOR_TEACHER;
import static com.hristoforov.elective.constants.HttpAttributes.ID_TEACHER_TO_EDIT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssignmentOfTheCourseToTeacherActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final UserDao userDao = mock(UserDao.class);
    private final CourseDao courseDao = mock(CourseDao.class);
    private final EmailSender emailSender = mock(EmailSender.class);

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(request.getRequestDispatcher(ASSIGMENT_OF_THE_COURSE_TO_TEACHER_PAGE)).thenReturn(rd);
        new AssignmentOfTheCourseToTeacherAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoPost() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(EDIT_COURSE_FOR_TEACHER)).thenReturn(String.valueOf(1));
        when(session.getAttribute(ID_TEACHER_TO_EDIT)).thenReturn(String.valueOf(1));
        when(appContext.getUserDao()).thenReturn(userDao);
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(appContext.getEmailSender()).thenReturn(emailSender);
        when(userDao.findById(getTestUser().getId())).thenReturn(getTestUser());
        when(courseDao.findById(getTestCourse().getId())).thenReturn(getTestCourse());
        new AssignmentOfTheCourseToTeacherAction(appContext).executeDoPost(request, response);
    }
}