package org.ispw.fastridetrack.util;

import org.ispw.fastridetrack.bean.LocationBean;
import org.ispw.fastridetrack.bean.RideBean;
import org.ispw.fastridetrack.bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.model.Ride;

@SuppressWarnings("java:S6548")
public class DriverSessionContext {

    private static DriverSessionContext INSTANCE;

    private RideBean currentRide;
    private TaxiRideConfirmationBean currentConfirmation;
    private LocationBean startPoint;
    private LocationBean endPoint;

    private DriverSessionContext() {}

    public static synchronized DriverSessionContext getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DriverSessionContext();
        }
        return INSTANCE;
    }


    // RideConfirmation accettata ma non ancora avviata
    public void setCurrentConfirmation(TaxiRideConfirmationBean confirmation) {
        this.currentConfirmation = confirmation;
    }

    public TaxiRideConfirmationBean getCurrentConfirmation() {
        return currentConfirmation;
    }

    // Ride in corso
    public void setCurrentRide(RideBean ridebean) {
        this.currentRide = ridebean;
    }

    public RideBean getCurrentRide() {
        return currentRide;
    }

    // True se il driver ha una conferma attiva accettata
    public boolean hasPendingConfirmation() {
        return currentConfirmation != null;
    }

    // True se la corsa è attualmente attiva
    public boolean hasActiveRide() {
        return currentRide != null;
    }

    public void clearConfirmation(){
        this.currentConfirmation = null;
    }

    public void setStartPoint(LocationBean startPoint) {
        this.startPoint = startPoint;
    }

    public LocationBean getStartPoint() {
        return startPoint;
    }

    public void setEndPoint(LocationBean endPoint) {
        this.endPoint = endPoint;
    }

    public LocationBean getEndPoint() {
        return endPoint;
    }

    // Resetta lo stato temporaneo della sessione (es. al termine della corsa)
    public void clear() {
        this.currentConfirmation = null;
        this.currentRide = null;
        this.startPoint = null;
        this.endPoint = null;
    }
}
