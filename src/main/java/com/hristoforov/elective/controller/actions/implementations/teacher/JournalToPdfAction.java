package com.hristoforov.elective.controller.actions.implementations.teacher;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.user.User;
import com.hristoforov.elective.services.pdfConverter.ConvertToPdf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.hristoforov.elective.constants.CRAPaths.TABLE_OF_COURSES_FOR_TEACHER_SERVLET;
import static com.hristoforov.elective.constants.HttpAttributes.CURRENT_USER;

public class JournalToPdfAction implements Action {
    private static final Logger LOGGER = LogManager.getLogger(JournalToPdfAction.class);

    private final CourseDao courseDao;
    private final UserDao userDao;
    private final ConvertToPdf convertToPdf;

    public JournalToPdfAction(AppContext appContext) {
        courseDao = appContext.getCourseDao();
        userDao = appContext.getUserDao();
        convertToPdf = appContext.getConvertToPdf();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentTeacher = (User) session.getAttribute(CURRENT_USER);
        if (currentTeacher != null) {
            ByteArrayOutputStream journalPdf = convertToPdf.createTeacherJournalPdf(courseDao.findAllCoursesByUserId(
                    currentTeacher.getId()).keySet(), userDao);
            response.setContentType("application/pdf");
            response.setContentLength(journalPdf.size());
            response.setHeader("Content-Disposition", "attachment; filename=\"Journal.pdf\"");
            try (OutputStream outputStream = response.getOutputStream()) {
                journalPdf.writeTo(outputStream);
                outputStream.flush();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }

        response.sendRedirect(TABLE_OF_COURSES_FOR_TEACHER_SERVLET);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
