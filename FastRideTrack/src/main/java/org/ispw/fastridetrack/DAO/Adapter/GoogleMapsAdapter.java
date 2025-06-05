package org.ispw.fastridetrack.DAO.Adapter;

import org.ispw.fastridetrack.Bean.MapRequestBean;
import org.ispw.fastridetrack.Model.Map;
import org.ispw.fastridetrack.Model.Session.SessionManager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleMapsAdapter implements MapService {

    private static final String API_KEY = System.getenv("GOOGLE_MAPS_API_KEY"); // Variabile d’ambiente

    @Override
    public Map calculateRoute(MapRequestBean requestBean) {
        if (requestBean == null) {
            throw new IllegalArgumentException("MapRequestBean non può essere nullo");
        }

        String from = requestBean.getOriginAsString();
        String to = requestBean.getDestination();

        boolean persistenceEnabled = SessionManager.getInstance().isPersistenceEnabled();
        boolean apiKeyAvailable = API_KEY != null && !API_KEY.isBlank();

        String html;
        double estimatedTimeMinutes;
        double distanceKm;

        if (persistenceEnabled && apiKeyAvailable) {
            // Modalità reale con API key
            html = generateRouteHtml(from, to);
            // Tempo e distanza placeholder, in futuro puoi calcolarli con API reali
            estimatedTimeMinutes = 15.0;
            distanceKm = 7.0;
        } else {
            // Modalità mock o senza API key
            html = generateMockHtml(from, to);
            estimatedTimeMinutes = 10.0;
            distanceKm = 5.0;
        }

        return new Map(html, from, to, distanceKm, estimatedTimeMinutes);
    }

    // Metodo helper privato, non fa parte dell’interfaccia
    private String generateMockHtml(String origin, String destination) {
        return "<html><body><h3>[MOCK MAP] Da " + origin + " a " + destination + "</h3></body></html>";
    }

    // Metodo helper privato, genera l’iframe Google Maps reale
    private String generateRouteHtml(String origin, String destination) {
        if (!SessionManager.getInstance().isPersistenceEnabled() || API_KEY == null || API_KEY.isBlank()) {
            return generateMockHtml(origin, destination);
        }

        // Codifica URL per sicurezza
        String encodedOrigin = URLEncoder.encode(origin, StandardCharsets.UTF_8);
        String encodedDestination = URLEncoder.encode(destination, StandardCharsets.UTF_8);

        return "<iframe width=\"100%\" height=\"400\" frameborder=\"0\" style=\"border:0\" " +
                "src=\"https://www.google.com/maps/embed/v1/directions?key=" + API_KEY +
                "&origin=" + encodedOrigin +
                "&destination=" + encodedDestination +
                "\" allowfullscreen></iframe>";
    }
}








