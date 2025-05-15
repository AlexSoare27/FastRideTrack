package org.ispw.fastridetrack.Model.Session;

import org.ispw.fastridetrack.DAO.Adapter.EmailDAO;
import org.ispw.fastridetrack.DAO.Adapter.MapDAO;
import org.ispw.fastridetrack.DAO.ClientDAO;
import org.ispw.fastridetrack.DAO.DriverDAO;
import org.ispw.fastridetrack.DAO.RideRequestDAO;
import org.ispw.fastridetrack.DAO.TaxiRideDAO;
import org.ispw.fastridetrack.Model.Client;

public class SessionManager {

    private static SessionManager instance;
    private final boolean persistenceEnabled;
    private SessionFactory sessionFactory;
    private Client loggedClient;  // Client loggato

    private SessionManager() {
        // Carica la configurazione dalla variabile d'ambiente
        String usePersistenceEnv = System.getenv("USE_PERSISTENCE");
        this.persistenceEnabled = "true".equalsIgnoreCase(usePersistenceEnv);
    }

    public static void init() {
        if (instance == null) {
            instance = new SessionManager();
        }
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("SessionManager non inizializzato. Chiama init() prima.");
        }
        return instance;
    }

    public boolean isPersistenceEnabled() {
        return persistenceEnabled;
    }

    public void setSessionFactory(SessionFactory factory) {
        this.sessionFactory = factory;
    }

    public ClientDAO getClientDAO() {
        return sessionFactory.createClientDAO();
    }

    public DriverDAO getDriverDAO() {
        return sessionFactory.createDriverDAO();
    }

    public RideRequestDAO getRideRequestDAO() {
        return sessionFactory.createRideRequestDAO();
    }

    public TaxiRideDAO getTaxiRideDAO() {
        return sessionFactory.createTaxiRideDAO();
    }

    public EmailDAO getEmailDAO() {
        return sessionFactory.createEmailDAO();
    }

    public MapDAO getMapDAO() {
        return sessionFactory.createMapDAO();
    }

    // Ottieni il client loggato
    public Client getLoggedClient() {
        return loggedClient;
    }

    // Setta il client loggato
    public void setLoggedClient(Client client) {
        this.loggedClient = client;
    }

    // Pulisce la sessione (al logout)
    public void clearSession() {
        this.sessionFactory = null;
        this.loggedClient = null;
    }
}

