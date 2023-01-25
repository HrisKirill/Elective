package com.hristoforov.elective.controller.filters;

import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.entities.topic.Topic;
import com.hristoforov.elective.exceptions.IncorrectDataFormatException;
import com.hristoforov.elective.services.validation.ValidationService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.ADD_TOPIC_SERVLET;
import static com.hristoforov.elective.constants.ErrorMessage.ERROR_SPELLING;
import static com.hristoforov.elective.constants.ErrorMessage.ERROR_TITLE_EXISTS;
import static com.hristoforov.elective.constants.HttpAttributes.*;

@WebFilter(filterName = "ValidationFilterForTopic", urlPatterns = {ADD_TOPIC_SERVLET})
public class ValidationFilterForTopic implements Filter {
    private TopicDao topicDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        topicDao = AppContext.getAppContext().getTopicDao();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession httpSession = req.getSession();
        int errorCount = 0;
        String title = "";
        if (req.getMethod().equalsIgnoreCase(POST)) {

            try {
                title = ValidationService.titleCheck(req.getParameter(TITLE));
                httpSession.removeAttribute(TITLE_SPELLING_ERROR);
            } catch (IncorrectDataFormatException e) {
                errorCount++;
                httpSession.setAttribute(TITLE_SPELLING_ERROR, ERROR_SPELLING);
            }

            if (topicDao.findByTitle(req.getParameter(TITLE)) != null) {
                errorCount++;
                httpSession.setAttribute(TITLE_EXISTS_ERROR, ERROR_TITLE_EXISTS);
            } else {
                httpSession.removeAttribute(TITLE_EXISTS_ERROR);
            }

            if (errorCount == 0) {
                httpSession.setAttribute(CORRECT_TOPIC, Topic.builder().title(title).build());
                httpSession.removeAttribute(INCORRECT_COURSE_TITLE);
            } else {
                httpSession.setAttribute(CORRECT_TOPIC, null);
                httpSession.setAttribute(INCORRECT_COURSE_TITLE, req.getParameter(TITLE));
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
