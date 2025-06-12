package org.ispw.fastridetrack.DAO;

import org.ispw.fastridetrack.Model.RideRequest;

public interface RideRequestDAO {
    RideRequest save(RideRequest rideRequest);
    RideRequest findById(int requestID);
    void update(RideRequest rideRequest);
}

