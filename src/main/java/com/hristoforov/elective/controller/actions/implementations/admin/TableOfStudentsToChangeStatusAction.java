package com.hristoforov.elective.controller.actions.implementations.admin;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.enums.Status;
import com.hristoforov.elective.entities.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.CHANGE_STATUS_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.TABLE_OF_STUDENTS_TO_CHANGE_STATUS_PAGE;
import static com.hristoforov.elective.constants.CRA_QueriesFiles.FIND_ALL_STUDENTS_WITH_OFFSET;
import static com.hristoforov.elective.constants.CommonConstants.RECORDS_PER_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class TableOfStudentsToChangeStatusAction implements Action {
    private static final Logger LOGGER = LogManager.getLogger(TableOfStudentsToChangeStatusAction.class);
    private final UserDao userDao;
    private int page;

    public TableOfStudentsToChangeStatusAction(AppContext appContext) {
        userDao = appContext.getUserDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(CURRENT_PAGE) != null) {
            page = (int) session.getAttribute(CURRENT_PAGE);
        } else {
            page = 1;
        }

        request.setAttribute(NUMBER_OF_PAGES,
                (userDao.findAllStudents().size() + RECORDS_PER_PAGE - 1) / RECORDS_PER_PAGE);

        request.setAttribute(CURRENT_PAGE, page);

        request.setAttribute("students", userDao.
                findTeachersOrStudentsOrAllWithOffset((page - 1) * RECORDS_PER_PAGE,
                        RECORDS_PER_PAGE, FIND_ALL_STUDENTS_WITH_OFFSET));

        request.getRequestDispatcher(TABLE_OF_STUDENTS_TO_CHANGE_STATUS_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (request.getParameter(STATUS_STUDENT_ID) != null) {
            User student = userDao.findById(Long.valueOf(request.getParameter(STATUS_STUDENT_ID)));
            if (student != null && student.getStatus().equals(Status.UNBLOCKED)) {
                student.setStatus(Status.BLOCKED);
                userDao.update(student);
                LOGGER.info(student.getLogin() + " is blocked.");
            } else if (student != null && student.getStatus().equals(Status.BLOCKED)) {
                student.setStatus(Status.UNBLOCKED);
                userDao.update(student);
                LOGGER.info(student.getLogin() + " is unblocked.");
            }
            response.sendRedirect(CHANGE_STATUS_SERVLET);
        }

        if (request.getParameter(PREVIOUS) != null) {
            page = Integer.parseInt(request.getParameter(PREVIOUS));
            session.setAttribute(CURRENT_PAGE, page);
            response.sendRedirect(CHANGE_STATUS_SERVLET);
        } else if (request.getParameter(NEXT) != null) {
            page = Integer.parseInt(request.getParameter(NEXT));
            session.setAttribute(CURRENT_PAGE, page);
            response.sendRedirect(CHANGE_STATUS_SERVLET);
        }
    }
}
