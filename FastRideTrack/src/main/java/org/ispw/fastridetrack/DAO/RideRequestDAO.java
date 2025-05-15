package org.ispw.fastridetrack.DAO;

import org.ispw.fastridetrack.Bean.RideRequestBean;
import org.ispw.fastridetrack.Model.RideRequest;

public interface RideRequestDAO {
    void saveRideRequest(RideRequest request);
    RideRequest getRideRequestById(int id);

    RideRequestBean findById(int rideID);

    void update(RideRequestBean rideRequest);

    void save(RideRequestBean rideRequestBean);


}