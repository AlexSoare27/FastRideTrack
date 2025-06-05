package org.ispw.fastridetrack.Controller.GUIController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import org.ispw.fastridetrack.Bean.*;
import org.ispw.fastridetrack.DAO.RideRequestDAO;
import org.ispw.fastridetrack.DAO.TaxiRideDAO;
import org.ispw.fastridetrack.DAO.Adapter.EmailService;
import org.ispw.fastridetrack.DAO.Adapter.GoogleMapsAdapter;
import org.ispw.fastridetrack.Model.Map;
import org.ispw.fastridetrack.Model.Driver;
import org.ispw.fastridetrack.Model.Session.SessionManager;
import org.ispw.fastridetrack.Util.TemporaryMemory;

import java.io.IOException;

public class SelectDriverGUIController {

    @FXML private Label driverNameLabel;
    @FXML private Label vehiclePlateLabel;
    @FXML private Label vehicleInfoLabel;
    @FXML private Label estimatedFareLabel;
    @FXML private Label estimatedTimeLabel;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;
    @FXML private Button backButton;
    @FXML private WebView mapView;

    private final RideRequestDAO rideRequestDAO;
    private final TaxiRideDAO taxiRideDAO;
    private final EmailService emailService;

    private TaxiRideConfirmationBean taxiRideBean;

    private final TemporaryMemory tempMemory;

    public SelectDriverGUIController() {
        SessionManager session = SessionManager.getInstance();
        this.rideRequestDAO = session.getRideRequestDAO();
        this.taxiRideDAO = session.getTaxiRideDAO();
        this.emailService = session.getEmailService();

        this.tempMemory = TemporaryMemory.getInstance();
    }

    @FXML
    public void initialize() {
        this.taxiRideBean = tempMemory.getRideConfirmation();

        if (taxiRideBean == null || taxiRideBean.getDriver() == null) {
            showError("Dati mancanti", "Impossibile caricare i dati del driver.");
            confirmButton.setDisable(true);
            return;
        }

        Driver driver = taxiRideBean.getDriver();
        driverNameLabel.setText("Driver: " + driver.getName());
        vehicleInfoLabel.setText("Vehicle Model: " + driver.getVehicleInfo());
        vehiclePlateLabel.setText("Vehicle Plate: " + driver.getVehiclePlate());
        estimatedFareLabel.setText(taxiRideBean.getEstimatedFare() != null ?
                String.format("Estimated Fare: €%.2f", taxiRideBean.getEstimatedFare()) : "Estimated Fare: N/D");
        estimatedTimeLabel.setText(taxiRideBean.getEstimatedTime() != null ?
                "Estimated Time: " + taxiRideBean.getEstimatedTime() + " mins" : "Estimated Time: N/D");

        // Carica la mappa nella WebView
        loadMapInView();
    }

    private void loadMapInView() {
        if (taxiRideBean == null) return;

        CoordinateBean origin = taxiRideBean.getUserLocation();
        String destination = taxiRideBean.getDestination();

        GoogleMapsAdapter mapsAdapter = new GoogleMapsAdapter();

        // Prepara la richiesta mappa senza raggio (SelectDriver non usa raggio)
        MapRequestBean mapRequest = new MapRequestBean();
        mapRequest.setOrigin(origin);
        mapRequest.setDestination(destination);

        Map map = mapsAdapter.calculateRoute(mapRequest);

        if (map != null && map.getHtmlContent() != null) {
            mapView.getEngine().loadContent(map.getHtmlContent());
        } else {
            showError("Errore mappa", "Impossibile caricare la mappa del percorso.");
        }
    }

    @FXML
    private void onConfirmRide() {
        if (taxiRideBean == null) {
            showError("Dati corsa non trovati", "Impossibile confermare la corsa.");
            return;
        }

        taxiRideBean.markConfirmed();

        try {
            if (!taxiRideDAO.exists(taxiRideBean.getRideID())) {
                taxiRideDAO.save(taxiRideBean);
            } else {
                taxiRideDAO.update(taxiRideBean.getRideID(), taxiRideBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Errore DB", "Non è stato possibile salvare la corsa nel database.");
            return;
        }

        tempMemory.setRideConfirmation(taxiRideBean);

        if (sendEmailToDriver()) {
            showInfo("Corsa confermata", "La corsa è stata confermata e il driver è stato avvisato.");
            confirmButton.setDisable(true);
        } else {
            showError("Errore invio email", "Non è stato possibile inviare l'email al driver.");
        }
    }

    private boolean sendEmailToDriver() {
        try {
            String subject = "Nuova corsa: " + taxiRideBean.getRideID();
            String body = String.format("Ciao %s,\n\nHai una nuova corsa assegnata.\n" +
                            "Cliente: %s\n" +
                            "Partenza: %s\n" +
                            "Destinazione: %s\n" +
                            "Tariffa stimata: €%.2f\n" +
                            "Tempo stimato: %.2f minuti\n\nGrazie!",
                    taxiRideBean.getDriver().getName(),
                    taxiRideBean.getClient().getName(),
                    formatCoordinates(taxiRideBean.getUserLocation()),
                    taxiRideBean.getDestination(),
                    taxiRideBean.getEstimatedFare(),
                    taxiRideBean.getEstimatedTime());

            emailService.sendEmail(taxiRideBean.getDriver().getEmail(), subject, body);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String formatCoordinates(CoordinateBean coordinate) {
        if (coordinate == null) return "N/D";
        return String.format("Lat: %.6f, Lng: %.6f", coordinate.getLatitude(), coordinate.getLongitude());
    }

    @FXML
    private void onGoBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ispw/fastridetrack/views/SelectTaxi.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Errore caricamento", "Non è stato possibile tornare alla selezione del taxi.");
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}







