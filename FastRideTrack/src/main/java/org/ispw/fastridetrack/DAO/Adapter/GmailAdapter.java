package org.ispw.fastridetrack.DAO.Adapter;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.PasswordAuthentication;
import org.ispw.fastridetrack.Model.Session.SessionManager;

import java.util.Properties;

public class GmailAdapter implements EmailService {

    @Override
    public boolean sendEmail(String recipient, String subject, String body) {
        boolean isMock = !SessionManager.getInstance().isPersistenceEnabled();

        if (isMock) {
            System.out.println("[MOCK] Email inviata a: " + recipient + " | Oggetto: " + subject + " | Contenuto: " + body);
            return true;
        }

        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("tuoindirizzo@gmail.com", "tuapassword");
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tuoindirizzo@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            return true;

        } catch (Exception e) {
            System.err.println("[ERROR] Invio email fallito: " + e.getMessage());
            return false;
        }
    }
}

