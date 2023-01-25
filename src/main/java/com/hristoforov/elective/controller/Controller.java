package com.hristoforov.elective.controller;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.actions.ActionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.*;


@WebServlet(name = "Controller", urlPatterns = {LOGIN_PAGE_SERVLET, USER_INFO_PAGE_SERVLET, TABLE_FOR_STUDENT_SERVLET,
        TABLE_OF_COURSES_FOR_TEACHER_SERVLET, USERS_TABLES_FOR_TEACHER_SERVLET, EDIT_MARK_SERVLET, LOGOUT_SERVLET,
        REGISTRATION_SERVLET, ADD_COURSE_SERVLET, ADD_TOPIC_SERVLET, ADMIN_SERVLET, TEACHER_REGISTRATION_SERVLET,
        ASSIGNMENT_OF_THE_COURSE_SERVLET, EDIT_COURSE_SERVLET, TABLE_OF_COURSES_SERVLET, CHANGE_STATUS_SERVLET,
        TABLE_OF_TEACHERS_SERVLET,ERROR})
public class Controller extends HttpServlet {


    private static final ActionFactory ACTION_FACTORY = ActionFactory.getActionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req).executeDoGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req).executeDoPost(req, resp);
    }

    private Action process(HttpServletRequest req) {
        return ACTION_FACTORY.createAction(req.getRequestURI());
    }
}
