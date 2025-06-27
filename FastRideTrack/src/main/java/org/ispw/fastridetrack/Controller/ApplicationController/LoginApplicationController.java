package org.ispw.fastridetrack.controller.applicationcontroller;

import org.ispw.fastridetrack.dao.RideDAO;
import org.ispw.fastridetrack.exception.ClientDAOException;
import org.ispw.fastridetrack.exception.DriverDAOException;
import org.ispw.fastridetrack.model.Client;
import org.ispw.fastridetrack.model.Driver;
import org.ispw.fastridetrack.model.Ride;
import org.ispw.fastridetrack.model.session.SessionManager;
import org.ispw.fastridetrack.model.enumeration.UserType;
import org.ispw.fastridetrack.dao.ClientDAO;
import org.ispw.fastridetrack.dao.DriverDAO;

import java.util.Optional;

public class LoginApplicationController {

    private final ClientDAO clientDAO;
    private final DriverDAO driverDAO;
    private final RideDAO rideDAO;
    
    public LoginApplicationController() {
        // Inizializzo il SessionManager (solo se non già inizializzato)
        SessionManager.init();

        // Ottiene i dao dalla SessionFactory corretta (persistente o in-memory)
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

    private void loadPossibleActiveRide(int rideId){
        Optional<Ride> existingRide = rideDAO.findActiveRideByDriver(rideId);
        if (existingRide.isPresent()) {
            SessionManager.getInstance().setDriverSessionContext(null, existingRide.get());
        }
    }

    // Validazione credenziali per il driver
    public boolean validateDriverCredentials(String username, String password, UserType userType) throws DriverDAOException {
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

    // Logout
    public void closeLoggedSession() {
        SessionManager.getInstance().clearSession();
    }
}
