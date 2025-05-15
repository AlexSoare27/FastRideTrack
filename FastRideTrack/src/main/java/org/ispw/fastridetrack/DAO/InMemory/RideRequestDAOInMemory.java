package org.ispw.fastridetrack.DAO.InMemory;

import org.ispw.fastridetrack.Bean.RideRequestBean;
import org.ispw.fastridetrack.DAO.RideRequestDAO;
import org.ispw.fastridetrack.Model.RideRequest;

import java.util.HashMap;
import java.util.Map;

public class RideRequestDAOInMemory implements RideRequestDAO {
    private final Map<Integer, RideRequest> storage = new HashMap<>();

    @Override
    public void saveRideRequest(RideRequest request) {
        storage.put(request.getRequestId(), request);
    }

    @Override
    public RideRequest getRideRequestById(int id) {
        return storage.get(id);
    }

    @Override
    public RideRequestBean findById(int rideID) {
        return null;
    }

    @Override
    public void update(RideRequestBean rideRequest) {

    }

    @Override
    public void save(RideRequestBean rideRequestBean) {

    }
}