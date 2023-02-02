package com.hristoforov.elective.services.pdfConverter;

import com.hristoforov.elective.dao.interfaces.UserDao;
import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.user.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * ConvertToPdf class with methods for creating information in pdf format
 *
 * @author Khrystoforov Kyrylo
 * @version 1.0
 */
public class ConvertToPdf {
    private static final Logger LOGGER = LogManager.getLogger(ConvertToPdf.class);

    private final String[] headersForTeacherTablePdf = new String[]{"Title", "Last name", "First name", "Email", "Mark"};
    private final String[] headersForStudentTablePdf = new String[]{"Title", "Start Date", "End Date", "Mark"};
    private final String[] headersForAdminTablePdf = new String[]{"Title", "Duration", "Start Date", "End Date"};

    /**
     * Method to create journal for teacher in pdf format
     *
     * @param courses set of courses
     * @param userDao userDao
     * @return output stream with information in pdf format
     */
    public ByteArrayOutputStream createTeacherJournalPdf(Set<Course> courses, UserDao userDao) {
        Document document = new Document();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, output);
            document.open();
            document.add(createParagraph("Journal"));
            document.add(createTableForTeacherPdf(courses, userDao));
            document.close();
            writer.close();
            return output;
        } catch (DocumentException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to create certificate for user with grades for courses
     *
     * @param courses map with courses and marks
     * @return output stream with information in pdf format
     */
    public ByteArrayOutputStream createStudentCertificatePdf(Map<Course, Integer> courses) {
        Document document = new Document();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, output);
            document.open();
            document.add(createParagraph("Certificate"));
            document.add(createTableForStudentPdf(courses));
            document.close();
            writer.close();
            return output;
        } catch (DocumentException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to create table with all courses for admin
     *
     * @param courses list of courses
     * @return output stream with information in pdf format
     */
    public ByteArrayOutputStream createCoursesListForAdminPdf(List<Course> courses) {
        Document document = new Document();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, output);
            document.open();
            document.add(createParagraph("Courses"));
            document.add(createTableForAdminPdf(courses));
            document.close();
            writer.close();
            return output;
        } catch (DocumentException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private PdfPTable createTableForAdminPdf(List<Course> courses) {
        PdfPTable table = new PdfPTable(new float[]{10, 7, 15, 15});
        addTableHeader(table, headersForAdminTablePdf);
        addRowsForAdmin(table, courses);
        return table;
    }

    private PdfPTable createTableForStudentPdf(Map<Course, Integer> courses) {
        PdfPTable table = new PdfPTable(new float[]{10, 15, 10, 5});
        addTableHeader(table, headersForStudentTablePdf);
        addRowsForStudent(table, courses);
        return table;
    }

    private PdfPTable createTableForTeacherPdf(Set<Course> courses, UserDao userDao) {
        PdfPTable table = new PdfPTable(new float[]{10, 15, 10, 25, 5});
        addTableHeader(table, headersForTeacherTablePdf);
        addRowsForTeacher(table, courses, userDao);
        return table;
    }

    private void addRowsForAdmin(PdfPTable table, List<Course> courses) {
        for (Course course :
                courses) {
            table.addCell(course.getTitle());
            table.addCell(String.valueOf(course.getDuration()));
            table.addCell(String.valueOf(course.getStartDate()));
            table.addCell(String.valueOf(course.getEndDate()));
        }
    }

    private void addRowsForStudent(PdfPTable table, Map<Course, Integer> courses) {
        for (Map.Entry<Course, Integer> entry :
                courses.entrySet()) {
            table.addCell(entry.getKey().getTitle());
            table.addCell(String.valueOf(entry.getKey().getStartDate()));
            table.addCell(String.valueOf(entry.getKey().getEndDate()));
            table.addCell(String.valueOf(entry.getValue()));
        }
    }


    private void addRowsForTeacher(PdfPTable table, Set<Course> courses, UserDao userDao) {
        for (Course course :
                courses) {
            for (Map.Entry<User, Integer> entry :
                    userDao.findAllStudentsByCourseId(course.getId()).entrySet()) {
                table.addCell(course.getTitle());
                table.addCell(entry.getKey().getLastName());
                table.addCell(entry.getKey().getFirstName());
                table.addCell(entry.getKey().getEmail());
                table.addCell(String.valueOf(entry.getValue()));
            }
        }
    }

    private Paragraph createParagraph(String title) {
        Paragraph paragraph = new Paragraph(title, new Font(Font.FontFamily.TIMES_ROMAN, 20.0f));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.getSpacingAfter();
        return paragraph;
    }

    private void addTableHeader(PdfPTable table, String[] headers) {
        Stream.of(headers)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
}
