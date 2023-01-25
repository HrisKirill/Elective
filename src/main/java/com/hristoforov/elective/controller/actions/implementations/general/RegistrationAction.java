package com.hristoforov.elective.controller.actions.implementations.general;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.LOGIN_PAGE_SERVLET;
import static com.hristoforov.elective.constants.CRAPaths.REGISTRATION_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.REGISTRATION_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class RegistrationAction implements Action {

    private final UserDao userDao;

    public RegistrationAction(AppContext appContext) {
        userDao = appContext.getUserDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute(INCORRECT_SIGN_IN_LOGIN);
        session.removeAttribute(INCORRECT_SIGN_IN_PASSWORD);
        session.removeAttribute(AUTHENTICATION_ERROR);
        request.getRequestDispatcher(REGISTRATION_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CORRECT_USER);
        if (user != null) {
            userDao.create(user);
            response.sendRedirect(LOGIN_PAGE_SERVLET);
        } else {
            response.sendRedirect(REGISTRATION_SERVLET);
        }
    }
}
