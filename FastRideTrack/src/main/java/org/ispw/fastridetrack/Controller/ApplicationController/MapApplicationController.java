package org.ispw.fastridetrack.Controller.ApplicationController;

import org.ispw.fastridetrack.Bean.MapRequestBean;
import org.ispw.fastridetrack.DAO.Adapter.MapDAO;
import org.ispw.fastridetrack.Model.Session.SessionManager;

public class MapApplicationController {

    private MapDAO mapDAO;

    public MapApplicationController() {
        this.mapDAO = SessionManager.getInstance().getMapDAO();
    }

    // Mostra la mappa
    public void showMap(MapRequestBean mapRequestBean) {
        // Logica per visualizzare la mappa
        // Questo potrebbe invocare un'API di Google Maps o una rappresentazione interna
        mapDAO.showRoute(mapRequestBean);
    }
}

