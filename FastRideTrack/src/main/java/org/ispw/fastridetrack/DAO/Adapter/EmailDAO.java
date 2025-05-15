package org.ispw.fastridetrack.DAO.Adapter;

public interface EmailDAO {
    boolean sendEmail(String recipient, String subject, String body);
}