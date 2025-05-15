package org.ispw.fastridetrack.Controller.ApplicationController;

import org.ispw.fastridetrack.Bean.EmailBean;
import org.ispw.fastridetrack.DAO.Adapter.EmailDAO;
import org.ispw.fastridetrack.DAO.Adapter.GmailAdapter;
import org.ispw.fastridetrack.DAO.Adapter.EmailDAOInMemory;

public class NotificationApplicationController {

    private EmailDAO emailDAO;

    // Flag per determinare se si utilizza la persistenza
    private boolean persistence = false; // Modifica in base alla tua configurazione

    public NotificationApplicationController() {
        // Determina l'implementazione corretta di EmailDAO
        if (persistence) {
            this.emailDAO = new GmailAdapter(); // Usato quando è in modalità persistente
        } else {
            this.emailDAO = new EmailDAOInMemory(); // Usato quando è in modalità in-memory
        }
    }

    // Invia una notifica via email
    public void sendEmailNotification(EmailBean emailBean) {
        boolean success = emailDAO.sendEmail(emailBean.getEmail(), emailBean.getSubject(), emailBean.getBody());
        if (!success) {
            // Gestisci errore invio email
        }
    }
}
