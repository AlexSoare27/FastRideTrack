package org.ispw.fastridetrack.Controller.ApplicationController;

import org.ispw.fastridetrack.Bean.DriverAssignmentBean;
import org.ispw.fastridetrack.Bean.RideRequestBean;
import org.ispw.fastridetrack.DAO.DriverDAO;
import org.ispw.fastridetrack.DAO.RideRequestDAO;
import org.ispw.fastridetrack.Model.Driver;
import org.ispw.fastridetrack.Model.Session.SessionManager;

public class DriverMatchingApplicationController {

    private RideRequestDAO rideRequestDAO;
    private DriverDAO driverDAO;

    public DriverMatchingApplicationController() {
        this.rideRequestDAO = SessionManager.getInstance().getRideRequestDAO();
        this.driverDAO = (DriverDAO) SessionManager.getInstance().getDriverDAO();
    }

    // Assegna un driver alla richiesta di corsa
    public void assignDriverToRide(int rideID, int driverID) {
        // Ottieni la richiesta di corsa
        RideRequestBean rideRequest = rideRequestDAO.findById(rideID);
        Driver driver = driverDAO.findById(driverID);

        if (rideRequest != null && driver != null) {
            DriverAssignmentBean assignment = new DriverAssignmentBean(rideRequest, driver);
            // Logica per salvare l'assegnazione
            rideRequest.setDriver(driver);
            rideRequest.setStatus("Driver Assigned");
            rideRequestDAO.update(rideRequest);
        }
    }
}


