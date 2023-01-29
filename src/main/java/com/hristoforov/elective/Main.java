package com.hristoforov.elective;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);

    public static String toHexString(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));

        BigInteger number = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public static Session getSession() {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.auth", "true");
        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("elective.smart.app@gmail.com", "ewlxjffkuglwirng");
            }
        });
    }

    private static void sendEmail() throws MessagingException {
        Message message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress("elective.smart.app@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse("hristoforovkirill8@gmail.com"));
        message.setSubject("Mail Subject");

        String msg = "This is my first email using JavaMailer";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
        System.out.println("message sent successfully...");
    }


    /* Driver code */
    public static void main(String args[]) {
       /* try {
            String string1 = "max124";
            System.out.println("\n" + string1 + " : " + toHexString(string1));

            String string2 = "12341";
            System.out.println("\n" + string2 + " : " + toHexString(string2));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }*/
        try {
            sendEmail();
        } catch (MessagingException e) {
            System.err.println(e);
        }
    }
}
