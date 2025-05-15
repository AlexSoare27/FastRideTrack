package org.ispw.fastridetrack.DAO.InMemory;

import org.ispw.fastridetrack.Bean.ClientBean;
import org.ispw.fastridetrack.DAO.ClientDAO;
import org.ispw.fastridetrack.Model.Client;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ClientDAOInMemory implements ClientDAO {

    private final Map<String, Client> clients = new ConcurrentHashMap<>();

    // Costruttore con client di test pre-caricato
    public ClientDAOInMemory() {
        Client testClient = new Client(
                1,
                "testclient",
                "testpass",
                "Mario Rossi",
                "mario@gmail.com",
                "1234567890",
                "Carta di Credito"
        );
        testClient.setLatitude(41.9028);
        testClient.setLongitude(12.4964);
        clients.put(testClient.getUsername(), testClient);
    }

    @Override
    public void save(Client client) {
        clients.put(client.getUsername(), client);
    }

    @Override
    public Client findByUsername(String username) {
        return clients.get(username);
    }

    @Override
    public ClientBean retrieveClientByUsernameAndPassword(String username, String password) {
        Client client = clients.get(username);
        if (client != null && client.getPassword().equals(password)) {
            return ClientBean.fromModel(client);// Utilizzo di un ClientBean per separare i dati sensibili
        }
        return null; // Se non trovato o la password non corrisponde
    }

    // Metodo per eliminare un cliente
    public void delete(String username) {
        clients.remove(username);
    }
}


