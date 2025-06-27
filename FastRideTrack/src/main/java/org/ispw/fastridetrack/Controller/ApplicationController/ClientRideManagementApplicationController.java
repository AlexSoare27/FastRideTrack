package org.ispw.fastridetrack.controller.applicationcontroller;

import jakarta.mail.MessagingException;
import org.ispw.fastridetrack.bean.EmailBean;
import org.ispw.fastridetrack.bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.dao.adapter.EmailService;
import org.ispw.fastridetrack.dao.adapter.GmailAdapter;
import org.ispw.fastridetrack.dao.TaxiRideConfirmationDAO;
import org.ispw.fastridetrack.exception.RideConfirmationNotFoundException;
import org.ispw.fastridetrack.model.session.SessionManager;
import org.ispw.fastridetrack.model.TaxiRideConfirmation;


public class ClientRideManagementApplicationController {

    private final TaxiRideConfirmationDAO taxiRideConfirmationDAO;
    private final EmailService emailService;

    public ClientRideManagementApplicationController() {
        SessionManager session = SessionManager.getInstance();
        this.taxiRideConfirmationDAO = session.getTaxiRideDAO();
        this.emailService = new GmailAdapter();
    }

    /**
     * Recupera i dati di conferma corsa a partire dall'ID.
     * @param rideID identificativo della corsa
     * @return TaxiRideConfirmationBean
     * @throws RideConfirmationNotFoundException se la corsa non esiste
     */
    public TaxiRideConfirmationBean viewRideConfirmation(int rideID) {
        TaxiRideConfirmation model = taxiRideConfirmationDAO.findById(rideID)
                .orElseThrow(() -> new RideConfirmationNotFoundException(rideID));
        return TaxiRideConfirmationBean.fromModel(model);
    }

    /**
     * Conferma una corsa e invia una notifica al driver via email.
     * @param bean Bean contenente i dati della corsa confermata
     * @param email EmailBean con i dettagli della notifica
     * @throws MessagingException se si verifica un errore nell'invio della mail
     */
    public void confirmRideAndNotify(TaxiRideConfirmationBean bean, EmailBean email) throws MessagingException {
        bean.markPending();

        TaxiRideConfirmation model = bean.toModel();

        // Salvataggio della corsa
        if (!taxiRideConfirmationDAO.exists(model.getRideID())) {
            taxiRideConfirmationDAO.save(model);
        } else {
            taxiRideConfirmationDAO.update(model);
        }

        // Invio notifica al driver
        emailService.sendEmail(email.getRecipient(), email.getSubject(), email.getBody());
    }

}
