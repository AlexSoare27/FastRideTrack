package org.ispw.fastridetrack.Controller.ApplicationController;

import jakarta.mail.MessagingException;
import org.ispw.fastridetrack.Bean.EmailBean;
import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.DAO.TaxiRideDAO;
import org.ispw.fastridetrack.Exception.RideNotFoundException;
import org.ispw.fastridetrack.Model.Session.SessionManager;
import org.ispw.fastridetrack.DAO.Adapter.EmailService;
import org.ispw.fastridetrack.DAO.Adapter.GmailAdapter;

public class ClientRideManagementApplicationController {

    private final TaxiRideDAO taxiRideDAO;
    private final EmailService emailService;

    public ClientRideManagementApplicationController() {
        SessionManager session = SessionManager.getInstance();
        this.taxiRideDAO = session.getTaxiRideDAO();
        this.emailService = new GmailAdapter();
    }

    /**
     * Recupera i dati di conferma corsa a partire dall'ID.
     * @param rideID identificativo della corsa
     * @return TaxiRideConfirmationBean
     * @throws RideNotFoundException se la corsa non esiste
     */
    public TaxiRideConfirmationBean viewRideConfirmation(int rideID) {
        return taxiRideDAO.findById(rideID)
                .orElseThrow(() -> new RideNotFoundException(rideID));
    }

    /**
     * Conferma una corsa e invia una notifica al driver via email.
     * @param bean Bean contenente i dati della corsa confermata
     * @param email EmailBean con i dettagli della notifica
     * @throws MessagingException se si verifica un errore nell'invio della mail
     */
    public void confirmRideAndNotify(TaxiRideConfirmationBean bean, EmailBean email) throws MessagingException {
        bean.markConfirmed();

        // Salvataggio della corsa confermata
        if (!taxiRideDAO.exists(bean.getRideID())) {
            taxiRideDAO.save(bean); // Prima volta: salvataggio iniziale
        } else {
            taxiRideDAO.update(bean); // Corsa già esistente: aggiornamento
        }

        // Invio notifica al driver
        emailService.sendEmail(email.getRecipient(), email.getSubject(), email.getBody());
    }

}


