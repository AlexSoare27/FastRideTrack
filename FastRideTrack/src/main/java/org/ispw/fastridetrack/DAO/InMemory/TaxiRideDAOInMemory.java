package org.ispw.fastridetrack.DAO.InMemory;

import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.DAO.TaxiRideDAO;

import java.util.HashMap;
import java.util.Map;

public class TaxiRideDAOInMemory implements TaxiRideDAO {
    private final Map<Integer, TaxiRideConfirmationBean> rides = new HashMap<>();

    @Override
    public void save(TaxiRideConfirmationBean ride) {
        rides.put(ride.getRideID(), ride);
    }

    @Override
    public TaxiRideConfirmationBean findById(int rideID) {
        return rides.get(rideID);
    }
}
