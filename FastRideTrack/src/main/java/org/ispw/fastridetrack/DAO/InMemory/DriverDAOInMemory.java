package org.ispw.fastridetrack.DAO.InMemory;

import org.ispw.fastridetrack.Bean.AvailableDriverBean;
import org.ispw.fastridetrack.Bean.CoordinateBean;
import org.ispw.fastridetrack.Bean.DriverBean;
import org.ispw.fastridetrack.DAO.DriverDAO;
import org.ispw.fastridetrack.Model.Driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverDAOInMemory implements DriverDAO {
    private final Map<String, Driver> driverMap = new HashMap<>();

    // Costruttore con driver di test pre-caricati
    public DriverDAOInMemory() {
            Driver d1 = new Driver(
                    1,
                    "marco92",
                    "pass1",
                    "Marco Rossi",
                    "marco@example.com",
                    "1234567890",
                    45.456,
                    9.2964,
                    "Fiat Panda",
                    "AA123BB",
                    "CoopTaxi",
                    true
            );

            Driver d2 = new Driver(
                    2,
                    "luca88",
                    "pass2",
                    "Luca Bianchi",
                    "luca@example.com",
                    "0987654321",
                    45.455,
                    9.19,
                    "Toyota Yaris",
                    "BB456CC",
                    "TaxiRoma",
                    true
            );

            Driver d3 = new Driver(
                    3,
                    "giulia77",
                    "pass3",
                    "Giulia Verdi",
                    "giulia@example.com",
                    "1122334455",
                    42.0000,
                    12.6000,
                    "Renault Clio",
                    "CC789DD",
                    "Radiotaxi",
                    false
            );

            save(d1);
            save(d2);
            save(d3);

    }

    @Override
    public void save(Driver driver) {
        driverMap.put(driver.getUsername(), driver);
    }

    @Override
    public DriverBean retrieveDriverByUsernameAndPassword(String username, String password) {
        Driver driver = driverMap.get(username);
        if (driver != null && driver.getPassword().equals(password)) {
            return DriverBean.fromModel(driver);
        }
        return null;
    }

    @Override
    public Driver findById(int id_driver) {
        for (Driver driver : driverMap.values()) {
            if (driver.getUserID() == id_driver) {
                return driver;
            }
        }
        return null;
    }

    @Override
    public List<AvailableDriverBean> findDriversAvailableWithinRadius(CoordinateBean origin, int radiusKm) {
        List<AvailableDriverBean> result = new ArrayList<>();

        for (Driver driver : driverMap.values()) {
            if (!driver.isAvailable()) continue;

            double distance = calculateDistance(
                    origin.getLatitude(), origin.getLongitude(),
                    driver.getLatitude(), driver.getLongitude());

            if (distance <= radiusKm) {
                double etaMinutes = calculateEstimatedTime(distance);
                double price = calculateEstimatedPrice(distance);

                DriverBean driverBean = DriverBean.fromModel(driver);

                AvailableDriverBean availableBean = new AvailableDriverBean(driverBean, etaMinutes, price);
                result.add(availableBean);
            }
        }

        return result;
    }

    // Haversine formula per distanza in km tra due coordinate
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    private double calculateEstimatedTime(double distanceKm) {
        double averageSpeedKmPerH = 40.0;
        return (distanceKm / averageSpeedKmPerH) * 60; // in minuti
    }

    private double calculateEstimatedPrice(double distanceKm) {
        double baseFare = 3.0;
        double costPerKm = 1.2;
        return baseFare + (costPerKm * distanceKm);
    }
}




