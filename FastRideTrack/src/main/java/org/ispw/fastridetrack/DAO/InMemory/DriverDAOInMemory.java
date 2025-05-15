package org.ispw.fastridetrack.DAO.InMemory;

import org.ispw.fastridetrack.Bean.DriverBean;
import org.ispw.fastridetrack.Bean.UserBean;
import org.ispw.fastridetrack.DAO.DriverDAO;
import org.ispw.fastridetrack.Model.Driver;

import java.util.HashMap;
import java.util.Map;

public class DriverDAOInMemory implements DriverDAO {

    private final Map<String, Driver> drivers = new HashMap<>();

    public DriverDAOInMemory() {
        Driver testDriver = new Driver(2, "testdriver", "driverpass", "Luca Verdi", "luca@email.com", "3200000000", "iban123", "AB123CD", "TaxiRoma");
        testDriver.setLatitude(41.9000);
        testDriver.setLongitude(12.4800);
        drivers.put(testDriver.getUsername(), testDriver);
    }

    @Override
    public void save(Driver driver) {
        drivers.put(driver.getUsername(), driver);
    }

    @Override
    public Driver findByUsername(String username) {
        return drivers.get(username);
    }

    @Override
    public DriverBean retrieveDriverByUsernameAndPassword(String username, String password) {
        Driver driver = drivers.get(username);
        if (driver != null && driver.getPassword().equals(password)) {
            // Usa il metodo statico per convertire Driver in DriverBean
            return DriverBean.fromModel(driver);
        }
        return null;
    }


    @Override
    public Driver findById(int driverID) {
        return null;
    }
}
