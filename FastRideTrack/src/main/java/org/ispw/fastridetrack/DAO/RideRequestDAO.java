package org.ispw.fastridetrack.DAO;

import org.ispw.fastridetrack.Bean.RideRequestBean;
import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;

public interface RideRequestDAO {

    // Cambiato in RideRequestBean per restituire bean aggiornato con ID (utile dopo inserimento DB)
    RideRequestBean save(RideRequestBean rideRequestBean);

    RideRequestBean findById(int requestID);

    void update(RideRequestBean rideRequestBean);
}

