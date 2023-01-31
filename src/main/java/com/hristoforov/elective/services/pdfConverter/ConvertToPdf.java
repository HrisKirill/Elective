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
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;


public class ConvertToPdf {
    private static final Logger LOGGER = LogManager.getLogger(ConvertToPdf.class);

    private final String[] headersForTeacherTablePdf = new String[]{"Title", "Last name", "First name", "Email", "Mark"};

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

    private PdfPTable createTableForTeacherPdf(Set<Course> courses, UserDao userDao) {
        PdfPTable table = new PdfPTable(new float[]{10, 15, 10, 25, 5});
        addTableHeader(table, headersForTeacherTablePdf);
        addRowsForTeacher(table, courses, userDao);
        return table;
    }

    private Paragraph createParagraph(String title) {
        Paragraph paragraph = new Paragraph(title, new Font(Font.FontFamily.TIMES_ROMAN, 20.0f));
        paragraph.setAlignment(Element.ALIGN_CENTER);
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
}
