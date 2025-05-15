package org.ispw.fastridetrack.DAO.Adapter;

public class GmailAdapter implements EmailDAO {
    @Override
    public boolean sendEmail(String recipient, String subject, String body) {
        // Simulazione chiamata a Gmail API
        System.out.println("Email inviata a " + recipient + ": " + subject + " - " + body);
        // Integrazione reale: usare Gmail API con OAuth2 o SMTP
        return false;
    }
}
