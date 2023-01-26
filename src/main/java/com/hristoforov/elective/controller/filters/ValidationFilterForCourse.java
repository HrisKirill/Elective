package com.hristoforov.elective.controller.filters;

import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.exceptions.IncorrectDataFormatException;
import com.hristoforov.elective.services.validation.ValidationService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import static com.hristoforov.elective.constants.CRAPaths.ADD_COURSE_SERVLET;
import static com.hristoforov.elective.constants.CRAPaths.EDIT_COURSE_SERVLET;
import static com.hristoforov.elective.constants.ErrorMessage.*;
import static com.hristoforov.elective.constants.HttpAttributes.*;

/**
 * ValidationFilterForCourse to validate course data
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
@WebFilter(filterName = "ValidationFilterForCourse", urlPatterns = {EDIT_COURSE_SERVLET, ADD_COURSE_SERVLET})
public class ValidationFilterForCourse implements Filter {
    private CourseDao courseDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        courseDao = AppContext.getAppContext().getCourseDao();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession httpSession = req.getSession();
        String uri = req.getRequestURI();
        String title = "";
        int duration = 0;
        Date startDate = Date.valueOf(LocalDate.now());
        Date endDate = Date.valueOf(LocalDate.now());
        int errorCount = 0;

        if (req.getMethod().equalsIgnoreCase(POST)) {

            try {
                title = ValidationService.titleCheck(req.getParameter(TITLE));
                httpSession.removeAttribute(TITLE_SPELLING_ERROR);
            } catch (IncorrectDataFormatException e) {
                errorCount++;
                httpSession.setAttribute(TITLE_SPELLING_ERROR, ERROR_SPELLING);
            }

            try {
                duration = ValidationService.durationCheck(Integer.parseInt(req.getParameter(DURATION)));
                httpSession.removeAttribute(DURATION_SPELLING_ERROR);
            } catch (IncorrectDataFormatException e) {
                errorCount++;
                httpSession.setAttribute(DURATION_SPELLING_ERROR, ERROR_SPELLING);
            }

            if (uri.equals(ADD_COURSE_SERVLET)) {
                try {
                    startDate = ValidationService.checkDateAfterNow(req.getParameter(START_DATE));
                    httpSession.removeAttribute(START_DATE_SPELLING_ERROR);
                } catch (IncorrectDataFormatException e) {
                    errorCount++;
                    httpSession.setAttribute(START_DATE_SPELLING_ERROR, ERROR_SPELLING);
                }

                try {
                    endDate = ValidationService.checkDateAfterNow(req.getParameter(END_DATE));
                    httpSession.removeAttribute(END_DATE_SPELLING_ERROR);
                } catch (IncorrectDataFormatException e) {
                    errorCount++;
                    httpSession.setAttribute(END_DATE_SPELLING_ERROR, ERROR_SPELLING);
                }

            } else {
                startDate = Date.valueOf(req.getParameter(START_DATE));
                endDate = Date.valueOf(req.getParameter(END_DATE));
            }

            try {
                ValidationService.checkBothDates(req.getParameter(START_DATE),
                        req.getParameter(END_DATE));
                httpSession.removeAttribute(END_DATE_BEFORE_START_ERROR);
            } catch (IncorrectDataFormatException e) {
                errorCount++;
                httpSession.setAttribute(END_DATE_BEFORE_START_ERROR, ERROR_END_DATE_BEFORE_START);
            }

            if (uri.equals(ADD_COURSE_SERVLET) &&
                    courseDao.findByTitle(req.getParameter(TITLE)) != null) {
                errorCount++;
                httpSession.setAttribute(TITLE_EXISTS_ERROR, ERROR_TITLE_EXISTS);
            } else {
                httpSession.removeAttribute(TITLE_EXISTS_ERROR);
            }

            if (errorCount == 0) {
                Course course = (uri.equals(ADD_COURSE_SERVLET)) ? Course.builder()
                        .title(title)
                        .duration(duration)
                        .startDate(startDate)
                        .endDate(endDate)
                        .build()
                        : Course.builder()
                        .id(((Course) httpSession.getAttribute(EDIT_COURSE)).getId())
                        .title(title)
                        .duration(duration)
                        .startDate(startDate)
                        .endDate(endDate)
                        .build();

                httpSession.removeAttribute(INCORRECT_COURSE_TITLE);
                httpSession.removeAttribute(INCORRECT_COURSE_DURATION);
                httpSession.removeAttribute(INCORRECT_COURSE_START_DATE);
                httpSession.removeAttribute(INCORRECT_COURSE_END_DATE);
                httpSession.setAttribute(CORRECT_COURSE, course);
            } else {
                httpSession.setAttribute(CORRECT_COURSE, null);
                httpSession.setAttribute(INCORRECT_COURSE_TITLE, req.getParameter(TITLE));
                httpSession.setAttribute(INCORRECT_COURSE_DURATION, req.getParameter(DURATION));
                httpSession.setAttribute(INCORRECT_COURSE_START_DATE, req.getParameter(START_DATE));
                httpSession.setAttribute(INCORRECT_COURSE_END_DATE, req.getParameter(END_DATE));
            }

        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
