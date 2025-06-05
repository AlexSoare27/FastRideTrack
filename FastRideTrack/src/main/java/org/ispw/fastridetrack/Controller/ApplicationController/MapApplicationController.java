package org.ispw.fastridetrack.Controller.ApplicationController;

import org.ispw.fastridetrack.Bean.MapRequestBean;
import org.ispw.fastridetrack.DAO.Adapter.MapService;
import org.ispw.fastridetrack.Model.Map;
import org.ispw.fastridetrack.Model.Session.SessionManager;

public class MapApplicationController {

    private final MapService mapService;

    public MapApplicationController() {
        this.mapService = SessionManager.getInstance().getMapService();
    }

    /**
     * Calcola il percorso e aggiorna il MapRequestBean con il tempo stimato.
     * @param mapRequestBean richiesta con origine, destinazione e raggio
     * @return oggetto Map contenente dati della mappa, distanza e tempo stimato
     */
    public Map showMap(MapRequestBean mapRequestBean) {
        if (mapRequestBean == null) {
            throw new IllegalArgumentException("MapRequestBean non può essere nullo");
        }

        Map map = mapService.calculateRoute(mapRequestBean);

        // Aggiorna tempo stimato nel bean
        mapRequestBean.setEstimatedTimeMinutes(map.getEstimatedTimeMinutes());

        return map;
    }
}



