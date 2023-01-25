package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.TopicDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.ADMIN_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.ADMIN_FEATURES_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class AdminFeaturesAction implements Action {
    private final TopicDao topicDao;

    public AdminFeaturesAction(AppContext appContext) {
        topicDao = appContext.getTopicDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute(CURRENT_PAGE);
        session.removeAttribute(NUMBER_OF_PAGES);
        session.removeAttribute(TITLE_SPELLING_ERROR);
        session.removeAttribute(INCORRECT_COURSE_TITLE);

        request.setAttribute(TOPIC_LIST, topicDao.findAll());
        request.getRequestDispatcher(ADMIN_FEATURES_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
