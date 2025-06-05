package org.ispw.fastridetrack.DAO;

import org.ispw.fastridetrack.Bean.RideRequestBean;

public interface RideRequestDAO {

    RideRequestBean save(RideRequestBean rideRequestBean);

    RideRequestBean findById(int requestID);

    void update(RideRequestBean rideRequestBean);
}

