package com.hristoforov.elective.controller.actions.implementations.general;

import com.hristoforov.elective.controller.actions.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.LOGIN_PAGE_SERVLET;
import static com.hristoforov.elective.constants.CRAPaths.USER_INFO_PAGE_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.ERROR_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.CURRENT_USER;

public class ErrorAction implements Action {
    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("statusCode", request.getAttribute("javax.servlet.error.status_code"));
        request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (request.getParameter("goBack") != null) {
            if (session.getAttribute(CURRENT_USER) != null) {
                response.sendRedirect(USER_INFO_PAGE_SERVLET);
            } else {
                response.sendRedirect(LOGIN_PAGE_SERVLET);
            }
        }
    }
}
