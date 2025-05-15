package org.ispw.fastridetrack.DAO.MYSQL;

import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.DAO.TaxiRideDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaxiRideDAOMYSQL implements TaxiRideDAO {

    private final Connection connection;

    public TaxiRideDAOMYSQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(TaxiRideConfirmationBean ride) {
        String sql = "INSERT INTO taxi_rides (rideID, driverID, clientID, latitude, longitude, destination, status, estimatedFare, estimatedTime, paymentStatus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ride.getRideID());
            stmt.setInt(2, ride.getDriver().getUserID());
            stmt.setInt(3, ride.getClient().getUserID());
            stmt.setDouble(4, ride.getUserLocation().get(0));
            stmt.setDouble(5, ride.getUserLocation().get(1));
            stmt.setString(6, ride.getDestination());
            stmt.setString(7, ride.getStatus());
            stmt.setFloat(8, ride.getEstimatedFare());
            stmt.setFloat(9, ride.getEstimatedTime());
            stmt.setString(10, ride.getPaymentStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio corsa confermata", e);
        }
    }

    @Override
    public TaxiRideConfirmationBean findById(int rideID) {
        // Simile al save: esegui una SELECT e costruisci un TaxiRideConfirmationBean
        return null; // puoi implementare la lettura se ti serve
    }
}
