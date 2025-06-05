package org.ispw.fastridetrack.Controller.ApplicationController;

import org.ispw.fastridetrack.Bean.EmailBean;
import org.ispw.fastridetrack.DAO.Adapter.EmailService;
import org.ispw.fastridetrack.DAO.Adapter.GmailAdapter;

public class NotificationApplicationController {

    private final EmailService emailService;

    public NotificationApplicationController() {
        this.emailService = new GmailAdapter();
    }

    // Invia una notifica via email
    public void sendEmailNotification(EmailBean emailBean) {
        boolean success = emailService.sendEmail(
                emailBean.getEmail(),
                emailBean.getSubject(),
                emailBean.getBody()
        );

        if (!success) {
            // Qui puoi gestire l'errore, es. log o rilancia eccezione
            System.err.println("Invio email fallito per destinatario: " + emailBean.getEmail());
        }
    }
}

