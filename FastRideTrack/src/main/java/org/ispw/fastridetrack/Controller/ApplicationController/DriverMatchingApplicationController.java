package org.ispw.fastridetrack.Controller.ApplicationController;

import org.ispw.fastridetrack.Bean.AvailableDriverBean;
import org.ispw.fastridetrack.Bean.CoordinateBean;
import org.ispw.fastridetrack.Bean.MapRequestBean;
import org.ispw.fastridetrack.Bean.RideRequestBean;
import org.ispw.fastridetrack.DAO.DriverDAO;
import org.ispw.fastridetrack.DAO.RideRequestDAO;
import org.ispw.fastridetrack.Model.Driver;
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
     * Assegna un driver alla richiesta di corsa e aggiorna la persistenza.
     *
     * @param rideID   id della richiesta di corsa
     * @param driverID id del driver da assegnare
     * @throws IllegalArgumentException se rideID o driverID non validi
     */
    public void assignDriverToRide(int rideID, int driverID) {
        RideRequestBean rideRequest = rideRequestDAO.findById(rideID);
        Driver driver = driverDAO.findById(driverID);

        Objects.requireNonNull(rideRequest, "RideRequest con ID " + rideID + " non trovato");
        Objects.requireNonNull(driver, "Driver con ID " + driverID + " non trovato");

        rideRequest.setDriver(driver);
        rideRequestDAO.update(rideRequest);
    }

    /**
     * Trova i driver disponibili entro il raggio definito nel MapRequestBean.
     *
     * @param mapRequestBean contiene origine, destinazione e raggio di ricerca
     * @return lista di driver disponibili con stime di tempo e prezzo
     * @throws IllegalArgumentException se mapRequestBean è nullo
     */
    public List<AvailableDriverBean> findAvailableDrivers(MapRequestBean mapRequestBean) {
        Objects.requireNonNull(mapRequestBean, "MapRequestBean non può essere nullo");

        CoordinateBean origin = mapRequestBean.getOrigin();
        int radiusKm = mapRequestBean.getRadiusKm();

        return driverDAO.findDriversAvailableWithinRadius(origin, radiusKm);
    }

    /**
     * Salva una nuova richiesta di corsa.
     *
     * @param rideRequest richiesta di corsa da salvare
     * @return richiesta salvata con eventuale ID assegnato
     * @throws IllegalArgumentException se rideRequest è nullo
     * @throws RuntimeException se il salvataggio fallisce
     */
    public RideRequestBean saveRideRequest(RideRequestBean rideRequest) {
        Objects.requireNonNull(rideRequest, "RideRequestBean non può essere nullo");

        RideRequestBean savedRideRequest = rideRequestDAO.save(rideRequest);
        if (savedRideRequest == null) {
            throw new RuntimeException("Errore durante il salvataggio della richiesta di corsa");
        }
        return savedRideRequest;
    }

    /**
     * Recupera una richiesta di corsa per ID.
     *
     * @param rideID id della richiesta di corsa
     * @return RideRequestBean corrispondente
     * @throws IllegalArgumentException se la richiesta non viene trovata
     */
    public RideRequestBean viewRideRequest(int rideID) {
        RideRequestBean rideRequest = rideRequestDAO.findById(rideID);
        Objects.requireNonNull(rideRequest, "RideRequest con ID " + rideID + " non trovata");
        return rideRequest;
    }
}


