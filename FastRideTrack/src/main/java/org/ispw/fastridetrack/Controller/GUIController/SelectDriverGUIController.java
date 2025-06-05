package org.ispw.fastridetrack.Controller.GUIController;

import jakarta.mail.MessagingException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.ispw.fastridetrack.Bean.*;
import org.ispw.fastridetrack.Controller.ApplicationController.ClientRideManagementApplicationController;
import org.ispw.fastridetrack.DAO.RideRequestDAO;
import org.ispw.fastridetrack.DAO.TaxiRideDAO;
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
    @FXML public Button goBackButton;
    @FXML private WebView mapView;

    private final RideRequestDAO rideRequestDAO;
    private final TaxiRideDAO taxiRideDAO;
    private final ClientRideManagementApplicationController rideManagementController;
    private final TemporaryMemory tempMemory;

    private TaxiRideConfirmationBean taxiRideBean;

    public SelectDriverGUIController() {
        SessionManager session = SessionManager.getInstance();
        this.rideRequestDAO = session.getRideRequestDAO();
        this.taxiRideDAO = session.getTaxiRideDAO();
        this.rideManagementController = new ClientRideManagementApplicationController();
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

        loadMapInView();
    }

    private void loadMapInView() {
        if (taxiRideBean == null) return;

        CoordinateBean origin = taxiRideBean.getUserLocation();
        String destination = taxiRideBean.getDestination();

        GoogleMapsAdapter mapsAdapter = new GoogleMapsAdapter();

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
        try {
            EmailBean email = buildEmailBean();
            rideManagementController.confirmRideAndNotify(taxiRideBean, email);
            showInfo("Corsa confermata", "Il driver è stato notificato via email.");
            confirmButton.setDisable(true);
            goBackButton.setDisable(true);
        } catch (MessagingException e) {
            showError("Errore", "Errore durante l'invio dell'email al driver.");
        }
    }

    private EmailBean buildEmailBean() {
        Driver driver = taxiRideBean.getDriver();
        GoogleMapsAdapter mapsAdapter = new GoogleMapsAdapter();

        // Ottengo l'indirizzo leggibile dalle coordinate del cliente
        String originAddress = "Indirizzo non disponibile";
        CoordinateBean originCoord = taxiRideBean.getUserLocation();
        if (originCoord != null) {
            originAddress = mapsAdapter.getAddressFromCoordinates(
                    originCoord.getLatitude(), originCoord.getLongitude()
            );
        }

        String subject = "Nuova corsa: " + taxiRideBean.getRideID();
        String body = String.format(
                "Ciao %s,\n\nHai una nuova corsa assegnata.\n\n" +
                        "Cliente: %s\n" +
                        "Partenza: %s\n" +
                        "Destinazione: %s\n" +
                        "Tariffa stimata: €%.2f\n" +
                        "Tempo stimato: %.2f minuti\n\n" +
                        "Controlla l'app per maggiori dettagli.\n\nGrazie!",
                driver.getName(),
                taxiRideBean.getClient().getName(),
                originAddress,
                taxiRideBean.getDestination(),
                taxiRideBean.getEstimatedFare(),
                taxiRideBean.getEstimatedTime()
        );
        return new EmailBean(driver.getEmail(), subject, body);
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
            showError("Errore caricamento", "Errore nel tornare alla selezione del taxi.");
        }
    }

    private void showError(String title, String message) {
        showAlert(title, message, Alert.AlertType.ERROR);
    }

    private void showInfo(String title, String message) {
        showAlert(title, message, Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}







