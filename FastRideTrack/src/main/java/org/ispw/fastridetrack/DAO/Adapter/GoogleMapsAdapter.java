package org.ispw.fastridetrack.DAO.Adapter;

import org.ispw.fastridetrack.Bean.MapRequestBean;
import org.ispw.fastridetrack.Model.Map;
import org.ispw.fastridetrack.Model.Session.SessionManager;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.*;

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

    /**
     * Metodo pubblico per ottenere l'indirizzo (reverse geocoding) a partire da latitudine e longitudine.
     * Restituisce l'indirizzo formattato o un messaggio di fallback.
     */
    public String getAddressFromCoordinates(double latitude, double longitude) {
        if (API_KEY == null || API_KEY.isBlank()) {
            return "Indirizzo non disponibile (API key mancante)";
        }

        try {
            String latlngParam = latitude + "," + longitude;
            String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
                    URLEncoder.encode(latlngParam, StandardCharsets.UTF_8) +
                    "&key=" + API_KEY;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                String status = json.get("status").getAsString();

                if ("OK".equals(status)) {
                    JsonArray results = json.getAsJsonArray("results");
                    if (results.size() > 0) {
                        JsonObject firstResult = results.get(0).getAsJsonObject();
                        return firstResult.get("formatted_address").getAsString();
                    } else {
                        return "Indirizzo non trovato";
                    }
                } else {
                    return "Errore API: " + status;
                }
            } else {
                return "Errore HTTP: " + response.statusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Errore durante il reverse geocoding";
        }
    }
}









