package com.hristoforov.elective.controller.actions.implementations.general;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.enums.Role;
import com.hristoforov.elective.entities.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.hristoforov.elective.constants.CRAPaths.USER_INFO_PAGE_SERVLET;
import static com.hristoforov.elective.constants.CRA_JSPFiles.USER_INFO_PAGE;
import static com.hristoforov.elective.constants.CommonConstants.RECORDS_PER_PAGE;
import static com.hristoforov.elective.constants.HttpAttributes.*;

public class UserInfoAction implements Action {
    private int page;
    private final UserDao userDao;
    private final CourseDao courseDao;

    public UserInfoAction(AppContext appContext) {
        userDao = appContext.getUserDao();
        courseDao = appContext.getCourseDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(CURRENT_USER);
        if (currentUser.getRole().name().equalsIgnoreCase(Role.STUDENT.name())) {
            if (session.getAttribute(CURRENT_PAGE_FOR_USER_INFO) != null) {
                page = (int) session.getAttribute(CURRENT_PAGE_FOR_USER_INFO);
            } else {
                page = 1;
            }
            request.setAttribute(JOIN_COURSE_LIST,
                    courseDao.coursesInWhichTheStudentDoesNotParticipateWithOffset(currentUser.getId(),
                            (page - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE));
            session.setAttribute(NUMBER_OF_PAGES_FOR_USER_INFO,
                    (courseDao.coursesInWhichTheStudentDoesNotParticipateWithoutOffset
                            (currentUser.getId()).size() + RECORDS_PER_PAGE - 1) / RECORDS_PER_PAGE);

            request.setAttribute(CURRENT_PAGE_FOR_USER_INFO, page);
        }
        request.getRequestDispatcher(USER_INFO_PAGE).forward(request, response);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CURRENT_USER);

        if (request.getParameter(PREVIOUS) != null) {
            page = Integer.parseInt(request.getParameter(PREVIOUS));
        } else if (request.getParameter(NEXT) != null) {
            page = Integer.parseInt(request.getParameter(NEXT));
        }

        if (request.getParameter(JOIN_TO_COURSE) != null) {
            Course course = courseDao.findById(Long.valueOf(request.getParameter(JOIN_TO_COURSE)));
            if (course != null) {
                userDao.createUserCourse(userDao.findByLogin(user.getLogin()), course, 0);
                courseDao.incrementCountOfStudent(course.getId());
                page = 1;
                session.setAttribute(NUMBER_OF_PAGES_FOR_USER_INFO,
                        (courseDao.coursesInWhichTheStudentDoesNotParticipateWithoutOffset
                                (user.getId()).size() + RECORDS_PER_PAGE - 1) / RECORDS_PER_PAGE);

            }
        }

        session.setAttribute(CURRENT_PAGE_FOR_USER_INFO, page);
        response.sendRedirect(USER_INFO_PAGE_SERVLET);
    }
}
