package org.ispw.fastridetrack.Controller.ApplicationController;

import org.ispw.fastridetrack.Bean.*;
import org.ispw.fastridetrack.DAO.DriverDAO;
import org.ispw.fastridetrack.DAO.RideRequestDAO;
import org.ispw.fastridetrack.Model.Driver;
import org.ispw.fastridetrack.Model.RideRequest;
import org.ispw.fastridetrack.Model.Session.SessionManager;

import java.util.List;
import java.util.Objects;

public class DriverMatchingApplicationController {

    private final RideRequestDAO rideRequestDAO;
    private final DriverDAO driverDAO;

    public DriverMatchingApplicationController() {
        this.rideRequestDAO = SessionManager.getInstance().getRideRequestDAO();
        this.driverDAO = SessionManager.getInstance().getDriverDAO();
    }

    /**
     * Assegna un driver a una richiesta usando il bean DriverAssignmentBean.
     */
    public void assignDriverToRequest(DriverAssignmentBean assignmentBean) {
        Objects.requireNonNull(assignmentBean, "DriverAssignmentBean non può essere nullo");
        assignDriverToRequest(assignmentBean.getRequestID(), assignmentBean.getDriver().getUserID());
    }

    /**
     * Assegna un driver alla richiesta di corsa e aggiorna la persistenza.
     *
     * @param requestID ID della richiesta di corsa
     * @param driverID  ID del driver da assegnare
     */
    public void assignDriverToRequest(int requestID, int driverID) {
        RideRequest model = rideRequestDAO.findById(requestID);
        Driver driver = driverDAO.findById(driverID);

        Objects.requireNonNull(model, "RideRequest con ID " + requestID + " non trovato");
        Objects.requireNonNull(driver, "Driver con ID " + driverID + " non trovato");

        model.setDriver(driver);
        rideRequestDAO.update(model);
    }

    /**
     * Trova i driver disponibili entro il raggio fornito dal MapRequestBean.
     *
     * @param mapRequestBean contiene origine, destinazione e raggio
     * @return lista di driver disponibili (bean)
     */
    public List<AvailableDriverBean> findAvailableDrivers(MapRequestBean mapRequestBean) {
        Objects.requireNonNull(mapRequestBean, "MapRequestBean non può essere nullo");

        CoordinateBean originBean = mapRequestBean.getOrigin();
        int radiusKm = mapRequestBean.getRadiusKm();

        // Conversione: CoordinateBean → Coordinate (model)
        return driverDAO.findDriversAvailableWithinRadius(originBean, radiusKm);
    }

    /**
     * Salva una nuova richiesta di corsa a partire dal bean.
     *
     * @param rideRequestBean la richiesta di corsa (bean)
     * @return RideRequestBean salvato (con ID aggiornato)
     */
    public RideRequestBean saveRideRequest(RideRequestBean rideRequestBean) {
        Objects.requireNonNull(rideRequestBean, "RideRequestBean non può essere nullo");

        // Conversione: Bean → Model
        RideRequest model = rideRequestBean.toModel();

        // Salvataggio nel DAO (Model)
        RideRequest savedModel = rideRequestDAO.save(model);

        if (savedModel == null) {
            throw new RuntimeException("Errore durante il salvataggio della richiesta di corsa");
        }

        // Conversione: Model → Bean
        return RideRequestBean.fromModel(savedModel);
    }

}



