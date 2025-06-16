package org.ispw.fastridetrack.dao.adapter;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String recipient, String subject, String body) throws MessagingException;
}