package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.utils.SortAndSelect;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.EDIT_COURSE_SERVLET;
import static com.hristoforov.elective.constants.CRAPaths.TABLE_OF_COURSES_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.TABLE_OF_COURSES_PAGE;
import static com.hristoforov.elective.constants.CommonConstants.RECORDS_PER_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;


public class TableOfCoursesForAdminAction implements Action {
    private final CourseDao courseDao;

    private int page;

    public TableOfCoursesForAdminAction(AppContext appContext) {
        courseDao = appContext.getCourseDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        session.removeAttribute(TITLE_SPELLING_ERROR);
        session.removeAttribute(DURATION_SPELLING_ERROR);
        session.removeAttribute(START_DATE_SPELLING_ERROR);
        session.removeAttribute(END_DATE_SPELLING_ERROR);
        session.removeAttribute(END_DATE_BEFORE_START_ERROR);
        session.removeAttribute(TITLE_EXISTS_ERROR);
        session.removeAttribute(CORRECT_COURSE);

        session.removeAttribute(INCORRECT_COURSE_TITLE);
        session.removeAttribute(INCORRECT_COURSE_DURATION);
        session.removeAttribute(INCORRECT_COURSE_START_DATE);
        session.removeAttribute(INCORRECT_COURSE_END_DATE);

        if (session.getAttribute(CURRENT_PAGE) != null) {
            page = (int) session.getAttribute(CURRENT_PAGE);
        } else {
            page = 1;
        }

        request.setAttribute(NUMBER_OF_PAGES,
                (courseDao.findAll().size() + RECORDS_PER_PAGE - 1) / RECORDS_PER_PAGE);

        if (session.getAttribute(SORT_WAY_FOR_PAGINATION) != null) {
            request.setAttribute(COURSES_FOR_ADMIN,
                    SortAndSelect.sortAdminTableBySomeValue(courseDao, String.valueOf(session.getAttribute(SORT_WAY_FOR_PAGINATION)),
                            (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE));
        } else {
            request.setAttribute(COURSES_FOR_ADMIN, courseDao.findAllWithOffset((page - 1) * RECORDS_PER_PAGE,
                    RECORDS_PER_PAGE));
        }

        request.setAttribute(CURRENT_PAGE, page);

        request.getRequestDispatcher(TABLE_OF_COURSES_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (request.getParameter(EDIT_COURSE_ID) != null) {
            session.setAttribute(EDIT_COURSE, courseDao.findById(Long.valueOf(request.getParameter(EDIT_COURSE_ID))));
            response.sendRedirect(EDIT_COURSE_SERVLET);
        } else if (request.getParameter(DELETE_COURSE_ID) != null) {
            courseDao.remove(Long.valueOf(request.getParameter(DELETE_COURSE_ID)));
            page = 1;
            session.setAttribute(CURRENT_PAGE, page);
            session.setAttribute(SORT_WAY_FOR_PAGINATION, request.getParameter(SORT));
            response.sendRedirect(TABLE_OF_COURSES_SERVLET);
        }

        if (request.getParameter(OK) != null) {
            if (request.getParameter(SORT) != null) {
                page = 1;
                session.setAttribute(CURRENT_PAGE, page);
                session.setAttribute(SORT_WAY_FOR_PAGINATION, request.getParameter(SORT));
                response.sendRedirect(TABLE_OF_COURSES_SERVLET);
            }
        }

        if (request.getParameter(PREVIOUS) != null) {
            page = Integer.parseInt(request.getParameter(PREVIOUS));
            session.setAttribute(CURRENT_PAGE, page);
            response.sendRedirect(TABLE_OF_COURSES_SERVLET);
        } else if (request.getParameter(NEXT) != null) {
            page = Integer.parseInt(request.getParameter(NEXT));
            session.setAttribute(CURRENT_PAGE, page);
            response.sendRedirect(TABLE_OF_COURSES_SERVLET);
        }
    }
}
