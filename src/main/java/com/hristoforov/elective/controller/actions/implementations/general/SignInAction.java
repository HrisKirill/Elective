package com.hristoforov.elective.controller.actions.implementations.general;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.exceptions.AuthenticationException;
import com.hristoforov.elective.services.SessionService;
import com.hristoforov.elective.services.authentication.UserAuthenticator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import static com.hristoforov.elective.constants.CRAPaths.LOGIN_PAGE_SERVLET;
import static com.hristoforov.elective.constants.CRAPaths.USER_INFO_PAGE_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.LOGIN_PAGE;
import static com.hristoforov.elective.constants.ErrorMessage.*;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class SignInAction implements Action {

    private final UserDao userDao;

    public SignInAction(AppContext appContext) {
        userDao = appContext.getUserDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SessionService.removeErrorFromUserSignUpPage(session);

        request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        try {
            User user = UserAuthenticator.authenticate(userDao,login, password);
            session.setAttribute(CURRENT_USER, user);
            session.setAttribute(CURRENT_DATE, Date.valueOf(LocalDate.now()));
            response.sendRedirect(USER_INFO_PAGE_SERVLET);
        } catch (AuthenticationException e) {
            if (e.getType().equals(AuthenticationException.Type.LOGIN)) {
                session.setAttribute(AUTHENTICATION_ERROR, ERROR_LOGIN);
            } else if (e.getType().equals(AuthenticationException.Type.PASS)) {
                session.setAttribute(AUTHENTICATION_ERROR, ERROR_PASSWORD);
            } else if (e.getType().equals(AuthenticationException.Type.BLOCKED)) {
                session.setAttribute(AUTHENTICATION_ERROR, ERROR_BLOCKED);
            }
            session.setAttribute(INCORRECT_SIGN_IN_LOGIN, login);
            session.setAttribute(INCORRECT_SIGN_IN_PASSWORD, password);
            response.sendRedirect(LOGIN_PAGE_SERVLET);
        }

    }
}
