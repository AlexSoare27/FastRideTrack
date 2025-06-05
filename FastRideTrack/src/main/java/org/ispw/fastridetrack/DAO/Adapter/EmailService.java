package org.ispw.fastridetrack.DAO.Adapter;

public interface EmailService {
    boolean sendEmail(String recipient, String subject, String body);
}