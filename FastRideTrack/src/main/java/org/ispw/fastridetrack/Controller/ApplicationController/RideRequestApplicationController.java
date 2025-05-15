package org.ispw.fastridetrack.Controller.ApplicationController;

import org.ispw.fastridetrack.Bean.RideRequestBean;
import org.ispw.fastridetrack.DAO.RideRequestDAO;
import org.ispw.fastridetrack.Model.Session.SessionManager;

public class RideRequestApplicationController {

    private RideRequestDAO rideRequestDAO;

    public RideRequestApplicationController() {
        // Determina l'implementazione di RideRequestDAO in base alla variabile d'ambiente
        this.rideRequestDAO = SessionManager.getInstance().getRideRequestDAO();
    }

    // Crea una nuova richiesta di corsa
    public void createRideRequest(RideRequestBean rideRequestBean) {
        // Salva la richiesta nel DAO (Database o in-memory)
        rideRequestDAO.save(rideRequestBean);
    }

    // Visualizza una richiesta di corsa
    public RideRequestBean viewRideRequest(int rideID) {
        return rideRequestDAO.findById(rideID);
    }
}



