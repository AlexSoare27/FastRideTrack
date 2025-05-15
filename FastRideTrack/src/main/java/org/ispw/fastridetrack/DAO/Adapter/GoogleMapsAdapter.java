package org.ispw.fastridetrack.DAO.Adapter;

import org.ispw.fastridetrack.Bean.MapRequestBean;

public abstract class GoogleMapsAdapter implements MapDAO {
    @Override
    public String showRoute(String origin, String destination) {
        return """
            <!DOCTYPE html>
            <html>
            <head><meta charset='utf-8'><title>Percorso</title></head>
            <body>
                <h2>Simulazione percorso</h2>
                <p>Da: %s</p>
                <p>A: %s</p>
            </body>
            </html>
        """.formatted(origin, destination);
    }

    @Override
    public void showRoute(MapRequestBean mapRequestBean) {
        String routeHTML = showRoute(mapRequestBean.getOrigin(), mapRequestBean.getDestination());
        System.out.println("Percorso calcolato:");
        System.out.println(routeHTML);
    }
}