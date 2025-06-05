package org.ispw.fastridetrack.DAO.MYSQL;

import org.ispw.fastridetrack.Bean.ClientBean;
import org.ispw.fastridetrack.Bean.RideRequestBean;
import org.ispw.fastridetrack.DAO.ClientDAO;
import org.ispw.fastridetrack.DAO.RideRequestDAO;

import java.sql.*;

public class RideRequestDAOMYSQL implements RideRequestDAO {
    private final Connection connection;
    private final ClientDAO clientDAO;

    public RideRequestDAOMYSQL(Connection connection, ClientDAO clientDAO) {
        this.connection = connection;
        this.clientDAO = clientDAO;
    }

    @Override
    public RideRequestBean save(RideRequestBean bean) {
        String query = "INSERT INTO ride_request (clientID, pickupLocation, destination, requestTime) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bean.getClient().getUserID());
            stmt.setString(2, bean.getPickupLocation());
            stmt.setString(3, bean.getDestination());
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis())); // salva il momento della richiesta

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    bean.setRequestID(generatedId);
                }
            }
            return bean;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il salvataggio di RideRequest", e);
        }
    }

    @Override
    public RideRequestBean findById(int requestID) {
        String query = "SELECT * FROM ride_request WHERE requestID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, requestID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int clientID = rs.getInt("clientID");
                    String pickup = rs.getString("pickupLocation");
                    String destination = rs.getString("destination");

                    // Driver non salvato qui, quindi null
                    org.ispw.fastridetrack.Model.Driver driver = null;

                    // Recupera client tramite DAO
                    var clientModel = clientDAO.findById(clientID);
                    ClientBean clientBean = ClientBean.fromModel(clientModel);

                    return new RideRequestBean(
                            requestID,
                            clientBean.toModel(),
                            pickup,
                            destination,
                            0,               // radiusKm non gestito nel DB, puoi mettere 0 o un valore di default
                            null,            // paymentMethod non salvato nel DB (puoi gestirlo altrove)
                            driver
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero di RideRequest con ID " + requestID, e);
        }
        return null;
    }

    @Override
    public void update(RideRequestBean bean) {
        String query = "UPDATE ride_request SET pickupLocation = ?, destination = ?, clientID = ? WHERE requestID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, bean.getPickupLocation());
            stmt.setString(2, bean.getDestination());
            stmt.setInt(3, bean.getClient().getUserID());
            stmt.setInt(4, bean.getRequestID());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Nessun record aggiornato per requestID " + bean.getRequestID());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'aggiornamento di RideRequest con ID " + bean.getRequestID(), e);
        }
    }
}


