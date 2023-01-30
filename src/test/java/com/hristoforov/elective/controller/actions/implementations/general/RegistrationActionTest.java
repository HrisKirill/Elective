package com.hristoforov.elective.controller.actions.implementations.general;

import com.hristoforov.elective.Constants;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.services.emailSending.EmailSender;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.Constants.*;
import static com.hristoforov.elective.constants.CRAPaths.LOGIN_PAGE_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.REGISTRATION_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.CORRECT_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegistrationActionTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestDispatcher rd = mock(RequestDispatcher.class);
    private final UserDao userDao = mock(UserDao.class);
    private final EmailSender emailSender = mock(EmailSender.class);

    @Test
    void testExecuteDoGet() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(REGISTRATION_PAGE)).thenReturn(rd);
        new RegistrationAction(appContext).executeDoGet(request, response);
    }


    @Test
    void testExecuteDoPost() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(appContext.getUserDao()).thenReturn(userDao);
        when(appContext.getEmailSender()).thenReturn(emailSender);
        when(request.getRequestDispatcher(LOGIN_PAGE_SERVLET)).thenReturn(rd);
        when(session.getAttribute(CORRECT_USER)).thenReturn(new User.Builder()
                .id(ID_VALUE)
                .lastName(LAST_NAME_VALUE)
                .firstName(FIRST_NAME_VALUE)
                .login(LOGIN_VALUE)
                .password(PASSWORD_VALUE)
                .email(EMAIL_VALUE)
                .role(ROLE_VALUE)
                .status(STATUS_VALUE)
                .build());

        new RegistrationAction(appContext).executeDoPost(request, response);
    }


    @Test
    void testExecuteBadDoPost() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(REGISTRATION_PAGE)).thenReturn(rd);
        when(session.getAttribute(CORRECT_USER)).thenReturn(null);
        new RegistrationAction(appContext).executeDoPost(request, response);
    }
}
