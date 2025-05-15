package org.ispw.fastridetrack.DAO.MYSQL;

import org.ispw.fastridetrack.Bean.DriverBean;
import org.ispw.fastridetrack.DAO.DriverDAO;
import org.ispw.fastridetrack.Model.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DriverDAOMYSQL implements DriverDAO {

    private final Connection connection;

    public DriverDAOMYSQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Driver driver) {
        String sql = "INSERT INTO driver (userID, name, username, password, email, phoneNumber, userType, latitude, longitude, paymentInfo, vehiclePlate, affiliation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, driver.getUserID());
            stmt.setString(2, driver.getName());
            stmt.setString(3, driver.getUsername());
            stmt.setString(4, driver.getPassword());
            stmt.setString(5, driver.getEmail());
            stmt.setString(6, driver.getPhoneNumber());
            stmt.setString(7, driver.getUserType().name());
            stmt.setDouble(8, driver.getLatitude());
            stmt.setDouble(9, driver.getLongitude());
            stmt.setString(10, driver.getVehicleInfo());
            stmt.setString(11, driver.getVehiclePlate());
            stmt.setString(12, driver.getAffiliation());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore salvataggio driver", e);
        }
    }

    @Override
    public Driver findByUsername(String username) {
        String sql = "SELECT * FROM driver WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Driver driver = new Driver(
                        rs.getInt("userID"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getString("paymentInfo"),
                        rs.getString("vehiclePlate"),
                        rs.getString("affiliation")
                );
                driver.setLatitude(rs.getDouble("latitude"));
                driver.setLongitude(rs.getDouble("longitude"));
                return driver;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore ricerca driver", e);
        }
        return null;
    }



    @Override
    public DriverBean retrieveDriverByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM driver WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new DriverBean(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("userID"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getString("paymentInfo"),
                        rs.getString("vehiclePlate"),
                        rs.getString("affiliation")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il login del driver", e);
        }
        return null;
    }


}

