package org.ispw.fastridetrack.Model.Session;

import org.ispw.fastridetrack.DAO.Adapter.EmailService;
import org.ispw.fastridetrack.DAO.Adapter.GmailAdapter;
import org.ispw.fastridetrack.DAO.Adapter.GoogleMapsAdapter;
import org.ispw.fastridetrack.DAO.Adapter.MapService;
import org.ispw.fastridetrack.DAO.ClientDAO;
import org.ispw.fastridetrack.DAO.DriverDAO;
import org.ispw.fastridetrack.DAO.RideRequestDAO;
import org.ispw.fastridetrack.DAO.TaxiRideDAO;
import org.ispw.fastridetrack.Model.Client;
import org.ispw.fastridetrack.Model.Driver;

public class SessionManager {

    private static SessionManager instance;
    private final boolean persistenceEnabled;
    private final SessionFactory sessionFactory;
    private Client loggedClient;  // Client loggato
    private Driver loggedDriver;  // Driver loggato

    private SessionManager() {
        // Carico la configurazione dalla variabile d'ambiente
        String usePersistenceEnv = System.getenv("USE_PERSISTENCE");
        this.persistenceEnabled = "true".equalsIgnoreCase(usePersistenceEnv);

        // Inizializzazione dinamica della session factory
        if (this.persistenceEnabled) {
            this.sessionFactory = new PersistenceSessionFactory();
        } else {
            this.sessionFactory = new InMemorySessionFactory();
        }
        // Adapter unificati per Gmail e GoogleMaps
        this.mapService = new GoogleMapsAdapter();
        this.emailService = new GmailAdapter();

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


    // === CLIENT ===
    public Client getLoggedClient() {
        return loggedClient;
    }

    public void setLoggedClient(Client client) {
        this.loggedClient = client;
    }

    // === DRIVER ===
    public Driver getLoggedDriver() {
        return loggedDriver;
    }

    public void setLoggedDriver(Driver driver) {
        this.loggedDriver = driver;
    }

    // === CLEAR SESSION ===
    public void clearSession() {
        System.out.println("Sessione utente terminata.");
        this.loggedClient = null;
        this.loggedDriver = null;
    }

    // === SERVIZI ESTERNI ===
    private final MapService mapService;
    private final EmailService emailService;

    public MapService getMapService() {
        return mapService;
    }

    public EmailService getEmailService() {
        return emailService;
    }
}



