package org.ispw.fastridetrack.DAO;

import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;

import java.util.Optional;

public interface TaxiRideDAO {

    void save(TaxiRideConfirmationBean ride);

    Optional<TaxiRideConfirmationBean> findById(int rideID);

    void update(TaxiRideConfirmationBean bean);

    boolean exists(int rideID);
}

