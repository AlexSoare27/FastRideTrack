package org.ispw.fastridetrack.DAO.MYSQL;

import org.ispw.fastridetrack.Bean.ClientBean;
import org.ispw.fastridetrack.DAO.ClientDAO;
import org.ispw.fastridetrack.Model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAOMYSQL implements ClientDAO {

    private final Connection connection;

    // Costruttore che inizializza la connessione al database
    public ClientDAOMYSQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Client client) {
        // già esistente
    }

    @Override
    public Client findByUsername(String username) {
        // già esistente
        return null;
    }

    @Override
    public ClientBean retrieveClientByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM client WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Integer userID = rs.getInt("userID");
                String uname = rs.getString("username");
                String pwd = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phoneNumber");
                String paymentMethod = rs.getString("paymentMethod");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");

                Client client = new Client(userID, uname, pwd, name, email, phone, paymentMethod);
                client.setLatitude(latitude);
                client.setLongitude(longitude);

                // Uso il metodo statico da ClientBean
                return ClientBean.fromModel(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il login del client", e);
        }
        return null;
    }


}

