package org.ispw.fastridetrack.Controller.ApplicationController;

import org.ispw.fastridetrack.Bean.ClientBean;
import org.ispw.fastridetrack.DAO.ClientDAO;
import org.ispw.fastridetrack.DAO.MYSQL.ClientDAOMYSQL;
import org.ispw.fastridetrack.DAO.InMemory.ClientDAOInMemory;
import org.ispw.fastridetrack.Model.Client;
import org.ispw.fastridetrack.Model.UserType;
import org.ispw.fastridetrack.Model.Session.SessionManager;
import org.ispw.fastridetrack.DAO.MYSQL.SingletonDBSession;
import org.ispw.fastridetrack.Model.Session.InMemorySessionFactory;
import org.ispw.fastridetrack.Model.Session.PersistenceSessionFactory;

import java.sql.Connection;

public class LoginApplicationController {

    private final ClientDAO clientDAO;

    public LoginApplicationController() {
        // Inizializzo il SessionManager (configurazione automatica della persistenza)
        SessionManager.init();

        // Ottiengo il flag di persistenza da SessionManager
        boolean persistence = SessionManager.getInstance().isPersistenceEnabled();

        // Inizializzo il DAO corretto in base alla modalità di persistenza
        if (persistence) {
            Connection connection = SingletonDBSession.getInstance().getConnection();
            if (connection != null) {
                this.clientDAO = new ClientDAOMYSQL(connection); // Passo la connessione al DAO
            } else {
                throw new RuntimeException("Connessione al database non riuscita");
            }
        } else {
            this.clientDAO = new ClientDAOInMemory(); // Uso il DAO in memoria
        }
    }

    // Metodo per validare le credenziali di login
    public boolean validateClientCredentials(String username, String password, UserType userType) {
        ClientBean clientBean = clientDAO.retrieveClientByUsernameAndPassword(username, password);

        // Converti ClientBean in Client
        Client client = Client.fromBean(clientBean);

        if (client != null) {
            if (client.getUserType() == userType) {
                SessionManager.getInstance().setLoggedClient(client);  // Imposta il Client nella sessione
                return true;
            }
        }

        return false;
    }


    // Metodo per creare una sessione per l'utente loggato
    public void createLoggedSession() {
        SessionManager sessionManager = SessionManager.getInstance();
        if (sessionManager.isPersistenceEnabled()) {
            // Se la persistenza è abilitata, imposto la session factory per il DB
            sessionManager.setSessionFactory(new PersistenceSessionFactory());
        } else {
            // Altrimenti, uso la session factory in memoria
            sessionManager.setSessionFactory(new InMemorySessionFactory());
        }
    }

    // Metodo per chiudere la sessione dell'utente
    public void closeLoggedSession() {
        // Chiudo la sessione dell'utente loggato
        SessionManager.getInstance().clearSession();
    }
}



