package org.ispw.fastridetrack.DAO.MYSQL;

import org.ispw.fastridetrack.Bean.AvailableDriverBean;
import org.ispw.fastridetrack.Bean.CoordinateBean;
import org.ispw.fastridetrack.Bean.DriverBean;
import org.ispw.fastridetrack.DAO.DriverDAO;
import org.ispw.fastridetrack.Model.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDAOMYSQL implements DriverDAO {
    private final Connection connection;

    public DriverDAOMYSQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Driver driver) {
        String sql = "INSERT INTO driver (username, password, name, email, phonenumber, latitude, longitude, vehicleInfo, vehiclePlate, affiliation, available) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, driver.getUsername());
            stmt.setString(2, driver.getPassword());
            stmt.setString(3, driver.getName());
            stmt.setString(4, driver.getEmail());
            stmt.setString(5, driver.getPhoneNumber());
            stmt.setDouble(6, driver.getLatitude());
            stmt.setDouble(7, driver.getLongitude());
            stmt.setString(8, driver.getVehicleInfo());
            stmt.setString(9, driver.getVehiclePlate());
            stmt.setString(10, driver.getAffiliation());
            // available stored as tinyint (0 or 1)
            stmt.setInt(11, driver.isAvailable() ? 1 : 0);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("Save driver failed: no rows affected.");
            }
        } catch (SQLException e) {
            System.err.println("SQL error during save driver:");
            e.printStackTrace();
        }
    }

    @Override
    public DriverBean retrieveDriverByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM driver WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Driver driver = extractDriverFromResultSet(rs);
                    return DriverBean.fromModel(driver);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL error during retrieveDriverByUsernameAndPassword:");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Driver findById(int id_driver) {
        String sql = "SELECT * FROM driver WHERE userID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_driver);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractDriverFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL error during findById:");
            e.printStackTrace();
        }
        return null;
    }

    private Driver extractDriverFromResultSet(ResultSet rs) throws SQLException {
        return new Driver(
                rs.getInt("userID"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("phoneNumber"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"),
                rs.getString("vehicleInfo"),
                rs.getString("vehiclePlate"),
                rs.getString("affiliation"),
                rs.getInt("available") == 1
        );
    }

    @Override
    public List<AvailableDriverBean> findDriversAvailableWithinRadius(CoordinateBean origin, int radiusKm) {
        List<AvailableDriverBean> availableDrivers = new ArrayList<>();
        String sql = "SELECT * FROM (\n" +
                "  SELECT *, \n" +
                "    (6371 * acos(\n" +
                "      cos(radians(?)) * cos(radians(latitude)) * cos(radians(longitude) - radians(?)) +\n" +
                "      sin(radians(?)) * sin(radians(latitude))\n" +
                "    )) AS distance \n" +
                "  FROM driver \n" +
                "  WHERE available = 1\n" +
                "    AND latitude BETWEEN ? AND ?\n" +
                "    AND longitude BETWEEN ? AND ?\n" +
                ") AS sub\n" +
                "WHERE distance <= ?\n" +
                "ORDER BY distance ASC;\n";

        double lat = origin.getLatitude();
        double lon = origin.getLongitude();

        double radiusLatDegrees = radiusKm / 111.0;
        double radiusLonDegrees = radiusKm / (111.320 * Math.cos(Math.toRadians(lat)));

        double minLat = lat - radiusLatDegrees;
        double maxLat = lat + radiusLatDegrees;
        double minLon = lon - radiusLonDegrees;
        double maxLon = lon + radiusLonDegrees;
        System.out.println("findDriversAvailableWithinRadius:");
        System.out.println("Origin lat: " + lat + ", lon: " + lon);
        System.out.println("Radius km: " + radiusKm);
        System.out.println("Bounding box:");
        System.out.println("minLat = " + minLat + ", maxLat = " + maxLat);
        System.out.println("minLon = " + minLon + ", maxLon = " + maxLon);


        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, lat);       // cos(radians(?))
            stmt.setDouble(2, lon);       // cos(radians(longitude) - radians(?))
            stmt.setDouble(3, lat);       // sin(radians(?))

            stmt.setDouble(4, minLat);
            stmt.setDouble(5, maxLat);
            stmt.setDouble(6, minLon);
            stmt.setDouble(7, maxLon);

            stmt.setDouble(8, radiusKm);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Driver driver = extractDriverFromResultSet(rs);
                    DriverBean driverBean = DriverBean.fromModel(driver);

                    double estimatedTime = calculateEstimatedTime(origin, driverBean);
                    double estimatedPrice = calculateEstimatedPrice(origin, driverBean);

                    AvailableDriverBean availableDriver = new AvailableDriverBean(driverBean, estimatedTime, estimatedPrice);
                    availableDrivers.add(availableDriver);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return availableDrivers;
    }

    // Haversine formula for distance
    private double calculateDistanceKm(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    private double calculateEstimatedTime(CoordinateBean origin, DriverBean driverBean) {
        double distanceKm = calculateDistanceKm(
                origin.getLatitude(), origin.getLongitude(),
                driverBean.getLatitude(), driverBean.getLongitude()
        );
        double averageSpeedKmPerH = 40.0;
        return (distanceKm / averageSpeedKmPerH) * 60; // minutes
    }

    private double calculateEstimatedPrice(CoordinateBean origin, DriverBean driverBean) {
        double distanceKm = calculateDistanceKm(
                origin.getLatitude(), origin.getLongitude(),
                driverBean.getLatitude(), driverBean.getLongitude()
        );
        double baseFare = 3.0;
        double costPerKm = 1.2;
        return baseFare + (costPerKm * distanceKm);
    }
}



