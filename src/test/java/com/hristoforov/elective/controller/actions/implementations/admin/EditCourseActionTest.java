package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.services.emailSending.EmailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static com.hristoforov.elective.Constants.getTestCourse;
import static com.hristoforov.elective.Constants.getTestUser;
import static com.hristoforov.elective.constants.CRA_JSPFiles.ASSIGMENT_OF_THE_COURSE_TO_TEACHER_PAGE;
import static com.hristoforov.elective.constants.CRA_JSPFiles.EDIT_COURSE_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EditCourseActionTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final CourseDao courseDao = mock(CourseDao.class);
    private final TopicDao topicDao = mock(TopicDao.class);
    private final UserDao userDao = mock(UserDao.class);
    private  final EmailSender emailSender = mock(EmailSender.class);

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(EDIT_COURSE)).thenReturn(getTestCourse());
        when(appContext.getTopicDao()).thenReturn(topicDao);
        when(request.getRequestDispatcher(EDIT_COURSE_PAGE)).thenReturn(rd);
        new EditCourseAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoPost() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CORRECT_COURSE)).thenReturn(getTestCourse());
        when(appContext.getCourseDao()).thenReturn(courseDao);
        when(request.getParameter(TOPICS)).thenReturn(String.valueOf(1));
        when(appContext.getTopicDao()).thenReturn(topicDao);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(appContext.getEmailSender()).thenReturn(emailSender);
        when(courseDao.findByTitle(getTestCourse().getTitle())).thenReturn(getTestCourse());
        when(userDao.findAllStudentsByCourseId(getTestCourse().getId())).thenReturn(Map.of(getTestUser(), 0));
        new EditCourseAction(appContext).executeDoPost(request, response);
    }

    @Test
    void testExecuteDoPostCourseNull() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CORRECT_COURSE)).thenReturn(null);
        new EditCourseAction(appContext).executeDoPost(request, response);
    }
}