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

import static com.hristoforov.elective.Constants.getTestTopic;
import static com.hristoforov.elective.constants.CRA_JSPFiles.ADD_TOPIC_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.CORRECT_TOPIC;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddTopicActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final TopicDao topicDao = mock(TopicDao.class);

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(ADD_TOPIC_PAGE)).thenReturn(rd);
        new AddTopicAction(appContext).executeDoGet(request, response);
    }

    @Test
    void testExecuteDoPost() throws ServletException, IOException {
        when(appContext.getTopicDao()).thenReturn(topicDao);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CORRECT_TOPIC)).thenReturn(getTestTopic());
        new AddTopicAction(appContext).executeDoPost(request, response);
    }

    @Test
    void testExecuteDoPostTopicNull() throws ServletException, IOException {
        when(appContext.getTopicDao()).thenReturn(topicDao);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(CORRECT_TOPIC)).thenReturn(null);
        new AddTopicAction(appContext).executeDoPost(request, response);
    }
}