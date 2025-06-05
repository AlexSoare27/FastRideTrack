package org.ispw.fastridetrack.DAO.InMemory;

import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.DAO.TaxiRideDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TaxiRideDAOInMemory implements TaxiRideDAO {
    private final Map<Integer, TaxiRideConfirmationBean> rides = new HashMap<>();

    @Override
    public void save(TaxiRideConfirmationBean ride) {
        rides.put(ride.getRideID(), ride);
    }

    @Override
    public Optional<TaxiRideConfirmationBean> findById(int rideID) {
        return Optional.ofNullable(rides.get(rideID));
    }

    @Override
    public void update(int rideID, TaxiRideConfirmationBean updatedRide) {
        if (!rides.containsKey(rideID)) {
            throw new RuntimeException("Nessuna corsa trovata con rideID " + rideID);
        }
        rides.put(rideID, updatedRide);
    }

    @Override
    public boolean exists(int rideID) {
        return rides.containsKey(rideID);
    }
}

