package org.ispw.fastridetrack.DAO;

import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;

public interface TaxiRideDAO {
    void save(TaxiRideConfirmationBean ride);
    TaxiRideConfirmationBean findById(int rideID);
}

