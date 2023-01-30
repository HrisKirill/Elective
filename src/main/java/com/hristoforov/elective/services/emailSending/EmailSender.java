package com.hristoforov.elective.services.emailSending;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    private static final Logger LOGGER = LogManager.getLogger(EmailSender.class);
    private final Session session;
    private final String userName;

    public EmailSender(Properties properties) {
        userName = properties.getProperty("email.user");
        session = getSession(properties);
    }

    public void sendEmail(String to, String subject, String content) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(userName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(content, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            LOGGER.info("message send successfully...");
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private Session getSession(Properties properties) {
        properties.put("mail.smtp.port", properties.getProperty("email.port"));
        properties.put("mail.smtp.host", properties.getProperty("email.host"));
        properties.put("mail.smtp.ssl.enable", properties.getProperty("email.ssl.enable"));
        properties.put("mail.smtp.auth", properties.getProperty("email.auth"));
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, properties.getProperty("email.password"));
            }
        });
    }

}
