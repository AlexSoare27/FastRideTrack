package org.ispw.fastridetrack.DAO.Adapter;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String recipient, String subject, String body) throws MessagingException;
}