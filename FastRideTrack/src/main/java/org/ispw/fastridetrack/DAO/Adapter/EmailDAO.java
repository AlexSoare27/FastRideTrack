package org.ispw.fastridetrack.dao.adapter;

public interface EmailDAO {
    boolean sendEmail(String recipient, String subject, String body);
}