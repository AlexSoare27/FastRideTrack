package org.ispw.fastridetrack.Controller.ApplicationController;

import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.DAO.TaxiRideDAO;
import org.ispw.fastridetrack.Exception.RideNotFoundException;
import org.ispw.fastridetrack.Model.Session.SessionManager;

import java.util.Optional;


public class RideManagementApplicationController {

    private final TaxiRideDAO taxiRideDAO;

    public RideManagementApplicationController() {
        this.taxiRideDAO = SessionManager.getInstance().getTaxiRideDAO();
    }

    /**
     * Recupera la conferma della corsa dato l'id della richiesta di corsa.
     *
     * @param rideID id della richiesta di corsa
     * @return TaxiRideConfirmationBean contenente i dettagli della corsa
     * @throws IllegalArgumentException se la conferma non viene trovata
     */
    public TaxiRideConfirmationBean viewRideConfirmation(int rideID) {
        Optional<TaxiRideConfirmationBean> confirmation = taxiRideDAO.findById(rideID);
        if (confirmation.isEmpty()) {
            throw new RideNotFoundException(rideID);
        }
        return confirmation.orElse(null);
    }

}
