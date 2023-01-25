package com.hristoforov.elective.controller.actions.implementations.general;

import com.hristoforov.elective.controller.actions.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.LOGIN_PAGE_SERVLET;
import static com.hristoforov.elective.constants.HttpAttributes.LOCALE;

public class LogoutAction implements Action {
    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String locale = String.valueOf(session.getAttribute(LOCALE));
            session.invalidate();
            request.getSession(true).setAttribute(LOCALE, locale);
            response.sendRedirect(LOGIN_PAGE_SERVLET);
        }

    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
