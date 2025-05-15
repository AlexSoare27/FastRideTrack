package org.ispw.fastridetrack.DAO.Adapter;

import org.ispw.fastridetrack.Bean.MapRequestBean;

public interface MapDAO {
    String showRoute(String origin, String destination);
    void showRoute(MapRequestBean mapRequestBean);

    // Aggiungo tali metodi per ottenere latitudine e longitudine attuali
    double getCurrentLatitude();
    double getCurrentLongitude();
}


