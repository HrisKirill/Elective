package com.hristoforov.elective.controller.actions.implementations.student;

import com.hristoforov.elective.controller.actions.Action;
import com.hristoforov.elective.controller.context.AppContext;
import com.hristoforov.elective.dao.interfaces.CourseDao;
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

import static com.hristoforov.elective.constants.CRAPaths.TABLE_FOR_STUDENT_SERVLET;
import static com.hristoforov.elective.constants.HttpAttributes.CURRENT_USER;

public class CertificateToPdfAction implements Action {
    private static final Logger LOGGER = LogManager.getLogger(CertificateToPdfAction.class);

    private final ConvertToPdf convertToPdf;
    private final CourseDao courseDao;

    public CertificateToPdfAction(AppContext appContext) {
        convertToPdf = appContext.getConvertToPdf();
        courseDao = appContext.getCourseDao();
    }

    @Override
    public void executeDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentStudent = (User) session.getAttribute(CURRENT_USER);
        if (currentStudent != null) {
            ByteArrayOutputStream journalPdf = convertToPdf.createStudentCertificatePdf(
                    courseDao.findAllCoursesByUserId(currentStudent.getId()));
            response.setContentType("application/pdf");
            response.setContentLength(journalPdf.size());
            response.setHeader("Content-Disposition", "attachment; filename=\"Certificate.pdf\"");
            try (OutputStream outputStream = response.getOutputStream()) {
                journalPdf.writeTo(outputStream);
                outputStream.flush();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
        response.sendRedirect(TABLE_FOR_STUDENT_SERVLET);
    }

    @Override
    public void executeDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
