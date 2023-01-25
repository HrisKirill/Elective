package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.services.SessionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.ASSIGNMENT_OF_THE_COURSE_SERVLET;
import static com.hristoforov.elective.constants.CRAPaths.TABLE_OF_TEACHERS_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.TABLE_OF_TEACHERS_PAGE;
import static com.hristoforov.elective.constants.CRA_QueriesFiles.FIND_ALL_TEACHERS_WITH_OFFSET;
import static com.hristoforov.elective.constants.CommonConstants.RECORDS_PER_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class TableOfTeachersForAdminAction implements Action {

    private final UserDao userDao;

    public TableOfTeachersForAdminAction(AppContext appContext) {
        userDao = appContext.getUserDao();
    }
    private int page;

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SessionService.removeErrorFromUserSignUpPage(session);

        if (session.getAttribute(CURRENT_PAGE) != null) {
            page = (int) session.getAttribute(CURRENT_PAGE);
        } else {
            page = 1;
        }

        request.setAttribute(NUMBER_OF_PAGES,
                (userDao.findAllTeachers().size() + RECORDS_PER_PAGE - 1) / RECORDS_PER_PAGE);
        request.setAttribute(CURRENT_PAGE, page);
        request.setAttribute(TEACHERS, userDao.
                findTeachersOrStudentsOrAllWithOffset((page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE, FIND_ALL_TEACHERS_WITH_OFFSET));
        request.getRequestDispatcher(TABLE_OF_TEACHERS_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (request.getParameter(EDIT_TEACHER) != null) {
            session.setAttribute(ID_TEACHER_TO_EDIT, request.getParameter(EDIT_TEACHER));
            response.sendRedirect(ASSIGNMENT_OF_THE_COURSE_SERVLET);
        }

        if (request.getParameter(PREVIOUS) != null) {
            page = Integer.parseInt(request.getParameter(PREVIOUS));
            setAttributeAndRedirect(session, response);
        } else if (request.getParameter(NEXT) != null) {
            page = Integer.parseInt(request.getParameter(NEXT));
            setAttributeAndRedirect(session, response);
        }
    }

    private void setAttributeAndRedirect(HttpSession session, HttpServletResponse response) throws IOException {
        session.setAttribute(CURRENT_PAGE, page);
        response.sendRedirect(TABLE_OF_TEACHERS_SERVLET);
    }

}
