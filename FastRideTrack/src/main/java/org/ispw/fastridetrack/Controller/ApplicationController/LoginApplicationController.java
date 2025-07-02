package org.ispw.fastridetrack.controller.applicationcontroller;

import org.ispw.fastridetrack.bean.RideBean;
import org.ispw.fastridetrack.exception.ClientDAOException;
import org.ispw.fastridetrack.exception.DriverDAOException;
import org.ispw.fastridetrack.model.Client;
import org.ispw.fastridetrack.model.Driver;
import org.ispw.fastridetrack.model.Ride;
import org.ispw.fastridetrack.session.SessionManager;
import org.ispw.fastridetrack.model.enumeration.UserType;
import org.ispw.fastridetrack.dao.ClientDAO;
import org.ispw.fastridetrack.dao.DriverDAO;
import org.ispw.fastridetrack.dao.RideDAO;

import java.util.Optional;

public class LoginApplicationController {

    private final ClientDAO clientDAO;
    private final DriverDAO driverDAO;
    private final RideDAO rideDAO;

    public LoginApplicationController() {
        SessionManager.init();

        SessionManager sessionManager = SessionManager.getInstance();
        this.clientDAO = sessionManager.getClientDAO();
        this.driverDAO = sessionManager.getDriverDAO();
        this.rideDAO = sessionManager.getRideDAO();
    }

    // Validazione credenziali per il client
    public boolean validateClientCredentials(String username, String password, UserType userType) throws ClientDAOException {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return false;
        }

        Client client = clientDAO.retrieveClientByUsernameAndPassword(username, password);
        if (client == null || client.getUserType() != userType) {
            return false;
        }

        SessionManager.getInstance().setLoggedClient(client);
        return true;
    }

    public RideBean loadPossibleActiveRide(int driverID){
        Optional<Ride> existingRide = rideDAO.findActiveRideByDriver(driverID);
        if (existingRide.isPresent()) {
            Ride ride = existingRide.get();
            return RideBean.fromModel(ride);
        }
        return null;
    }

    // Validazione credenziali per il driver
    public boolean validateDriverCredentials(String username, String password, UserType userType) throws DriverDAOException{
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return false;
        }

        Driver driver = driverDAO.retrieveDriverByUsernameAndPassword(username, password);
        if (driver == null || driver.getUserType() != userType) {
            return false;
        }

        SessionManager.getInstance().setLoggedDriver(driver);
        loadPossibleActiveRide(driver.getUserID());
        return true;
    }

}
