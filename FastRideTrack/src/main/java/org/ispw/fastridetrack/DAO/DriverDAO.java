package org.ispw.fastridetrack.DAO;

import org.ispw.fastridetrack.Bean.DriverBean;
import org.ispw.fastridetrack.Model.Driver;

public interface DriverDAO {
    void save(Driver driver);
    Driver findByUsername(String username);
    DriverBean retrieveDriverByUsernameAndPassword(String username, String password);

    Driver findById(int driverID);
}

