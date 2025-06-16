package org.ispw.fastridetrack.dao;

import org.ispw.fastridetrack.bean.AvailableDriverBean;
import org.ispw.fastridetrack.dao.mysql.DriverDAOMYSQL;
import org.ispw.fastridetrack.exception.DriverDAOException;
import org.ispw.fastridetrack.model.Coordinate;
import org.ispw.fastridetrack.model.Driver;


import java.util.List;

public interface DriverDAO {
    void save(Driver driver) throws DriverDAOException, DriverDAOMYSQL.DriverDAOException;
    Driver findById(int iddriver) throws DriverDAOException, DriverDAOMYSQL.DriverDAOException;
    Driver retrieveDriverByUsernameAndPassword(String username, String password) throws DriverDAOException, DriverDAOMYSQL.DriverDAOException;
    List<AvailableDriverBean> findDriversAvailableWithinRadius(Coordinate origin, int radiusKm) throws DriverDAOException, DriverDAOMYSQL.DriverDAOException;
    //void updateAvailability(int driverId, boolean isAvailable);
}



