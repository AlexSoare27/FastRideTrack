package org.ispw.fastridetrack.DAO;

import org.ispw.fastridetrack.Bean.ClientBean;
import org.ispw.fastridetrack.Model.Client;

public interface ClientDAO {
    void save(Client client);
    Client findById(Integer id_client);
    Client retrieveClientByUsernameAndPassword(String username, String password);
}


