package org.ispw.fastridetrack.DAO.Adapter;

public class EmailDAOInMemory implements EmailDAO {

    @Override
    public boolean sendEmail(String recipient, String subject, String body) {
        // Simulazione invio email in modalità in-memory
        System.out.println("[InMemory] Email inviata a: " + recipient);
        System.out.println("[InMemory] Oggetto: " + subject);
        System.out.println("[InMemory] Corpo: " + body);
        return false;
    }
}
