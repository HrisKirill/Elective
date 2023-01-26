package com.hristoforov.elective.utils;

import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.*;
import static com.hristoforov.elective.constants.CRA_JSPFiles.*;
import static com.hristoforov.elective.constants.CommonConstants.RECORDS_PER_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

/**
 * DoPostForUserAndTeacherTables to get rid of code duplication
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public class DoPostForUserAndTeacherTables {

    public static void doPostForTables(UserDao userDao, CourseDao courseDao,
                                       HttpServletRequest req, HttpServletResponse resp, String address)
            throws IOException {
        HttpSession session = req.getSession();
        User user = userDao.findByLogin(((User) session.getAttribute(CURRENT_USER)).getLogin());
        int page;

        if (req.getParameter(OK) != null) {
            if (req.getParameter(SELECT) != null) {
                page = 1;

                session.setAttribute(COURSE_MAP, SortAndSelect.selectTableBySomeValue(courseDao, user.getId(),
                        req.getParameter(SELECT),
                        (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE));

                session.setAttribute(NUMBER_OF_PAGES,
                        (SortAndSelect.getSelectingTableWithoutOffset(courseDao, user.getId(),
                                req.getParameter(SELECT)).size() + RECORDS_PER_PAGE - 1) / RECORDS_PER_PAGE);

                session.setAttribute(CURRENT_PAGE, page);

                session.setAttribute(SELECT_WAY_FOR_PAGINATION, req.getParameter(SELECT));
                resp.sendRedirect(address);
            }
        }


        if (req.getParameter(PREVIOUS) != null) {
            page = Integer.parseInt(req.getParameter(PREVIOUS));
            choosingPage(courseDao, session, resp, page, user, address);
        } else if (req.getParameter(NEXT) != null) {
            page = Integer.parseInt(req.getParameter(NEXT));
            choosingPage(courseDao, session, resp, page, user, address);
        }

        if (address.equals(TABLE_OF_COURSES_FOR_TEACHER_SERVLET) && req.getParameter(SHOW_STUDENTS) != null) {
            session.setAttribute(COURSE,
                    courseDao.findById(Long.valueOf(req.getParameter(SHOW_STUDENTS))));

            session.setAttribute(USER_MAP,
                    userDao.findAllStudentsByCourseId(Long.valueOf(req.getParameter(SHOW_STUDENTS))));

            resp.sendRedirect(USERS_TABLES_FOR_TEACHER_SERVLET);
        }
    }

    private static void choosingPage(CourseDao courseDao, HttpSession session, HttpServletResponse resp,
                                     int page, User user, String dispatcherPage) throws IOException {

        if (session.getAttribute(SELECT_WAY_FOR_PAGINATION) != null) {
            session.setAttribute(COURSE_MAP, SortAndSelect.selectTableBySomeValue(courseDao, user.getId(),
                    String.valueOf(session.getAttribute(SELECT_WAY_FOR_PAGINATION)),
                    (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE));

        } else {
            session.setAttribute(COURSE_MAP, courseDao.findAllCoursesByUserIdWithOffset
                    (user.getId(), (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE));
        }

        session.setAttribute(CURRENT_PAGE, page);
        resp.sendRedirect(dispatcherPage);
    }

}
