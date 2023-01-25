package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.entities.topic.Topic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.ADD_TOPIC_SERVLET;
import static com.hristoforov.elective.constants.CRAPaths.ADMIN_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.ADD_TOPIC_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.CORRECT_TOPIC;

public class AddTopicAction implements Action {
    private final TopicDao topicDao;

    public AddTopicAction(AppContext appContext) {
        topicDao = appContext.getTopicDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(ADD_TOPIC_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Topic topic = (Topic) session.getAttribute(CORRECT_TOPIC);
        if (topic != null) {
            topicDao.create(topic);
            response.sendRedirect(ADMIN_SERVLET);
        } else {
            response.sendRedirect(ADD_TOPIC_SERVLET);
        }
    }

}
