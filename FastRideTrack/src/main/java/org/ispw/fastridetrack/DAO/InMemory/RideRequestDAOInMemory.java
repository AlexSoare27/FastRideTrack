package org.ispw.fastridetrack.DAO.InMemory;

import org.ispw.fastridetrack.Bean.RideRequestBean;
import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.DAO.RideRequestDAO;

import java.util.HashMap;
import java.util.Map;

public class RideRequestDAOInMemory implements RideRequestDAO {
    private final Map<Integer, RideRequestBean> storage = new HashMap<>();

    @Override
    public RideRequestBean save(RideRequestBean bean) {
        if (bean.getRequestID() == null || bean.getRequestID() == 0) {
            // Genera un nuovo ID progressivo
            bean.setRequestID(storage.size() + 1); // attenzione, non thread-safe ma va bene per demo
        }
        storage.put(bean.getRequestID(), bean);
        return bean;
    }


    @Override
    public RideRequestBean findById(int requestID) {
        return storage.get(requestID);
    }

    @Override
    public void update(RideRequestBean rideRequest) {
        storage.put(rideRequest.getRequestID(), rideRequest);
    }

}

