package org.ispw.fastridetrack.dao.adapter;

public class EmailDAOInMemory implements EmailDAO {

    @Override
    public boolean sendEmail(String recipient, String subject, String body) {
        // Simulazione invio email in modalità in-memory
        System.out.println("[inmemory] Email inviata a: " + recipient);
        System.out.println("[inmemory] Oggetto: " + subject);
        System.out.println("[inmemory] Corpo: " + body);
        return false;
    }
}
