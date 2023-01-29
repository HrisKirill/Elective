package com.hristoforov.elective.controller.actions.implementations.general;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.services.emailSending.EmailSender;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.LOGIN_PAGE_SERVLET;
import static com.hristoforov.elective.constants.CRAPaths.REGISTRATION_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.REGISTRATION_PAGE;
import static com.hristoforov.elective.constants.EmailConstants.EMAIL_SUBJECT;
import static com.hristoforov.elective.constants.EmailConstants.MESSAGE_CONGRATULATIONS;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class RegistrationAction implements Action {

    private final UserDao userDao;
    private final EmailSender emailSender;

    public RegistrationAction(AppContext appContext) {
        userDao = appContext.getUserDao();
        emailSender = appContext.getEmailSender();
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
            String content = String.format(MESSAGE_CONGRATULATIONS, user.getFirstName());
            new Thread(() -> emailSender.sendEmail(user.getEmail(), EMAIL_SUBJECT, content)).start();
            response.sendRedirect(LOGIN_PAGE_SERVLET);
        } else {
            response.sendRedirect(REGISTRATION_SERVLET);
        }
    }
}
