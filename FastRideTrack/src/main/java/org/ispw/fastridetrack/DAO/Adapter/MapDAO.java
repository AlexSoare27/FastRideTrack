package org.ispw.fastridetrack.dao.adapter;

import org.ispw.fastridetrack.bean.MapRequestBean;

public interface MapDAO {
    String showRoute(String origin, String destination);
    void showRoute(MapRequestBean mapRequestBean);

    // Aggiungo tali metodi per ottenere latitudine e longitudine attuali
    double getCurrentLatitude();
    double getCurrentLongitude();
}


