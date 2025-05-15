package org.ispw.fastridetrack.Controller.ApplicationController;

import org.ispw.fastridetrack.Bean.RideRequestBean;
import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.DAO.RideRequestDAO;
import org.ispw.fastridetrack.DAO.TaxiRideDAO;
import org.ispw.fastridetrack.Model.Session.SessionManager;

public class RideManagementApplicationController {

    private RideRequestDAO rideRequestDAO;
    private TaxiRideDAO taxiRideDAO;

    public RideManagementApplicationController() {
        this.rideRequestDAO = SessionManager.getInstance().getRideRequestDAO();
        this.taxiRideDAO = SessionManager.getInstance().getTaxiRideDAO();
    }

    // Conferma una corsa
    public void confirmRide(int rideID, TaxiRideConfirmationBean confirmationBean) {
        // Ottieni la richiesta di corsa
        RideRequestBean rideRequest = rideRequestDAO.findById(rideID);

        if (rideRequest != null) {
            confirmationBean.setRideRequest(rideRequest);
            confirmationBean.setStatus("Confirmed");
            taxiRideDAO.save(confirmationBean);

            // Aggiorna lo stato della corsa
            rideRequest.setStatus("Confirmed");
            rideRequestDAO.update(rideRequest);
        }
    }

    // Cancella una corsa
    public void cancelRide(int rideID) {
        RideRequestBean rideRequest = rideRequestDAO.findById(rideID);
        if (rideRequest != null) {
            rideRequest.setStatus("Cancelled");
            rideRequestDAO.update(rideRequest);
        }
    }

    // Visualizza una conferma di corsa
    public TaxiRideConfirmationBean viewRideConfirmation(int rideID) {
        return taxiRideDAO.findById(rideID);
    }
}

