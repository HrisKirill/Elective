package com.hristoforov.elective.utils;

import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.entities.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CommonConstants.RECORDS_PER_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class Pagination {

    public static void choosingPageForUserAndTeacherTable(CourseDao courseDao, HttpSession session, HttpServletRequest req, HttpServletResponse resp,
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
