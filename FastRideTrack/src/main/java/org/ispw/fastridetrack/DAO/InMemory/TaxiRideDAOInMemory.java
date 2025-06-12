package org.ispw.fastridetrack.DAO.InMemory;

import org.ispw.fastridetrack.DAO.TaxiRideDAO;
import org.ispw.fastridetrack.Model.TaxiRideConfirmation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TaxiRideDAOInMemory implements TaxiRideDAO {
    private final Map<Integer, TaxiRideConfirmation> rides = new HashMap<>();

    @Override
    public void save(TaxiRideConfirmation ride) {
        rides.put(ride.getRideID(), ride);
    }

    @Override
    public Optional<TaxiRideConfirmation> findById(int rideID) {
        return Optional.ofNullable(rides.get(rideID));
    }

    @Override
    public void update(TaxiRideConfirmation updatedRide) {
        int rideID = updatedRide.getRideID();
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


