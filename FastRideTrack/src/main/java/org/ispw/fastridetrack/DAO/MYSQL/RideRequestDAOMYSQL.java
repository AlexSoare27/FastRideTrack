package org.ispw.fastridetrack.DAO.MYSQL;

import org.ispw.fastridetrack.Bean.RideRequestBean;
import org.ispw.fastridetrack.DAO.RideRequestDAO;
import org.ispw.fastridetrack.Model.RideRequest;

import java.sql.Connection;

public class RideRequestDAOMYSQL implements RideRequestDAO {
    private final Connection connection;

    public RideRequestDAOMYSQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveRideRequest(RideRequest request) {
        // JDBC logic to save ride request using 'connection'
    }

    @Override
    public RideRequest getRideRequestById(int id) {
        // JDBC logic to retrieve ride request using 'connection'
        return null;
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
