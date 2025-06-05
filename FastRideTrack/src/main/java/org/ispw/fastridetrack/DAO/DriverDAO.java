package org.ispw.fastridetrack.DAO;

import org.ispw.fastridetrack.Bean.AvailableDriverBean;
import org.ispw.fastridetrack.Bean.DriverBean;
import org.ispw.fastridetrack.Model.Driver;

import org.ispw.fastridetrack.Bean.CoordinateBean;
import java.util.List;

public interface DriverDAO {
    void save(Driver driver);
    Driver findById(int id_driver);
    DriverBean retrieveDriverByUsernameAndPassword(String username, String password);
    List<AvailableDriverBean> findDriversAvailableWithinRadius(CoordinateBean origin, int radiusKm);
    //void updateAvailability(int driverId, boolean isAvailable); // consigliato
}



