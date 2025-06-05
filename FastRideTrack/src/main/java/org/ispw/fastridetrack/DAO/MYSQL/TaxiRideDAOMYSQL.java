package org.ispw.fastridetrack.DAO.MYSQL;

import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.DAO.ClientDAO;
import org.ispw.fastridetrack.DAO.DriverDAO;
import org.ispw.fastridetrack.DAO.TaxiRideDAO;
import org.ispw.fastridetrack.Model.Client;
import org.ispw.fastridetrack.Model.Driver;

import java.sql.*;
import java.util.Optional;

public class TaxiRideDAOMYSQL implements TaxiRideDAO {

    private final Connection connection;

    public TaxiRideDAOMYSQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(TaxiRideConfirmationBean ride) {
        String sql = "INSERT INTO taxi_rides (rideID, driverID, clientID, status, estimatedFare, estimatedTime, paymentStatus, confirmationTime) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ride.getRideID());
            stmt.setInt(2, ride.getDriver().getUserID());
            stmt.setInt(3, ride.getClient().getUserID());
            stmt.setString(4, ride.getStatus());
            stmt.setDouble(5, ride.getEstimatedFare());
            stmt.setDouble(6, ride.getEstimatedTime());
            stmt.setString(7, ride.getPaymentStatus());
            stmt.setTimestamp(8, Timestamp.valueOf(ride.getConfirmationTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio corsa confermata", e);
        }
    }

    @Override
    public Optional<TaxiRideConfirmationBean> findById(int rideID) {
        String sql = "SELECT * FROM taxi_rides WHERE rideID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rideID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int driverID = rs.getInt("driverID");
                    int clientID = rs.getInt("clientID");
                    String status = rs.getString("status");
                    float estimatedFare = rs.getFloat("estimatedFare");
                    float estimatedTime = rs.getFloat("estimatedTime");
                    String paymentStatus = rs.getString("paymentStatus");
                    Timestamp confirmationTimestamp = rs.getTimestamp("confirmationTime");

                    DriverDAO driverDAO = new DriverDAOMYSQL(connection);
                    Driver driver = driverDAO.findById(driverID);

                    ClientDAO clientDAO = new ClientDAOMYSQL(connection);
                    Client client = clientDAO.findById(clientID);

                    TaxiRideConfirmationBean bean = new TaxiRideConfirmationBean(
                            rideID,
                            driver,
                            client,
                            null,
                            null,
                            status,
                            estimatedFare,
                            estimatedTime,
                            paymentStatus,
                            confirmationTimestamp.toLocalDateTime()
                    );
                    return Optional.of(bean);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero di TaxiRide con rideID " + rideID, e);
        }
    }

    @Override
    public void update(TaxiRideConfirmationBean bean) {
        String sql = "UPDATE taxi_rides SET driverID = ?, clientID = ?, status = ?, estimatedFare = ?, estimatedTime = ?, paymentStatus = ?, confirmationTime = ? WHERE rideID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bean.getDriver().getUserID());
            stmt.setInt(2, bean.getClient().getUserID());
            stmt.setString(3, bean.getStatus());
            stmt.setDouble(4, bean.getEstimatedFare());
            stmt.setDouble(5, bean.getEstimatedTime());
            stmt.setString(6, bean.getPaymentStatus());
            stmt.setTimestamp(7, Timestamp.valueOf(bean.getConfirmationTime()));
            stmt.setInt(8, bean.getRideID()); // ← recuperato dal bean

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Nessuna corsa trovata con rideID " + bean.getRideID());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore aggiornamento corsa in taxi_rides con rideID " + bean.getRideID(), e);
        }
    }


    @Override
    public boolean exists(int rideID) {
        String sql = "SELECT 1 FROM taxi_rides WHERE rideID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rideID);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il controllo esistenza della corsa con rideID " + rideID, e);
        }
    }
}


