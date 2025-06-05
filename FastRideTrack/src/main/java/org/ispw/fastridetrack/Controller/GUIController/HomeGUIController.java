package org.ispw.fastridetrack.Controller.GUIController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.ispw.fastridetrack.Bean.MapRequestBean;
import org.ispw.fastridetrack.Bean.RideRequestBean;
import org.ispw.fastridetrack.Bean.CoordinateBean;
import org.ispw.fastridetrack.Controller.ApplicationController.DriverMatchingApplicationController;
import org.ispw.fastridetrack.Controller.ApplicationController.MapApplicationController;
import org.ispw.fastridetrack.Exception.FXMLLoadException;
import org.ispw.fastridetrack.Model.Client;
import org.ispw.fastridetrack.Model.Map;
import org.ispw.fastridetrack.Model.Session.SessionManager;
import org.ispw.fastridetrack.Util.*;
import org.ispw.fastridetrack.Util.TemporaryMemory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeGUIController implements Initializable {

    @FXML public Button checkRiderButton;
    @FXML public Button myAccountButton;
    @FXML public Button myWalletButton;
    @FXML public Button logoutButton;
    @FXML private AnchorPane homePane;
    @FXML private ChoiceBox<String> rangeChoiceBox;
    @FXML private TextField destinationField;
    @FXML private Label welcomeLabel;
    @FXML private WebView mapWebView;

    private CoordinateBean currentLocation = new CoordinateBean(40.8518, 14.2681); // Default Napoli centro
    private MapApplicationController mapAppController;
    private DriverMatchingApplicationController rideRequestController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mapAppController = new MapApplicationController();
        rideRequestController = new DriverMatchingApplicationController();

        showGPSAlert();
        initializeChoiceBox();
        displayUserName();
        restoreTemporaryData();
        loadCurrentLocationMap();
    }

    private void displayUserName() {
        Client client = SessionManager.getInstance().getLoggedClient();
        if (client != null) {
            welcomeLabel.setText("Benvenuto, " + client.getName());
        }
    }

    private void showGPSAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GPS");
        alert.setHeaderText("Attiva il GPS");
        alert.setContentText("Per continuare, attiva il GPS del dispositivo.");
        alert.showAndWait();
    }

    private void initializeChoiceBox() {
        List<Integer> ranges = List.of(1, 2, 3, 5);
        rangeChoiceBox.getItems().addAll(ranges.stream().map(km -> km + " km").toList());
        rangeChoiceBox.setValue("1 km");
    }

    private void restoreTemporaryData() {
        MapRequestBean bean = TemporaryMemory.getInstance().getMapRequestBean();
        if (bean != null) {
            if (bean.getDestination() != null && !bean.getDestination().isBlank()) {
                destinationField.setText(bean.getDestination());
            }
            String radiusStr = bean.getRadiusKm() + " km";
            if (rangeChoiceBox.getItems().contains(radiusStr)) {
                rangeChoiceBox.setValue(radiusStr);
            }

            if (bean.getOrigin() != null) {
                currentLocation = bean.getOrigin();
            }
        }
    }

    private void loadCurrentLocationMap() {
        new Thread(() -> {
            try {
                String ip = IPFetcher.getPublicIP();
                System.out.println("IP pubblico: " + ip);

                var coordModel = IPLocationService.getCoordinateFromIP(ip);
                currentLocation = new CoordinateBean(coordModel.getLatitude(), coordModel.getLongitude());

                System.out.println("Coordinate ottenute: " + coordModel.getLatitude() + ", " + coordModel.getLongitude());

                Platform.runLater(() -> {
                    try {
                        String html = MapHTMLGenerator.generateMapHtmlString(coordModel);
                        WebEngine engine = mapWebView.getEngine();
                        engine.setJavaScriptEnabled(true);
                        engine.loadContent(html);
                    } catch (IOException e) {
                        showAlert("Errore nella generazione della mappa dinamica.");
                    }
                });

            } catch (Exception e) {
                Platform.runLater(() -> {
                    showAlert("Impossibile recuperare la posizione. Verrà caricata la mappa di default.");
                    loadMapWithDefaultLocation();
                });
            }
        }).start();
    }

    private void loadMapWithDefaultLocation() {
        Platform.runLater(() -> {
            WebEngine engine = mapWebView.getEngine();
            engine.setJavaScriptEnabled(true);
            URL url = getClass().getResource("/org/ispw/fastridetrack/html/map.html");
            if (url != null) {
                engine.load(url.toExternalForm());
            } else {
                showAlert("File map.html non trovato nelle risorse.");
            }
        });
    }

    @FXML
    private void onCheckRider() {
        String destination = destinationField.getText();

        if (destination == null || destination.trim().isEmpty()) {
            destinationField.setStyle("-fx-border-color: red;");
            showAlert("Inserisci una destinazione valida.");
            return;
        } else {
            destinationField.setStyle(""); // Rimuovo il bordo rosso se corretto
        }

        int radiusKm = convertRangeToInt(rangeChoiceBox.getValue());
        if (radiusKm == -1) {
            showAlert("Raggio selezionato non valido.");
            return;
        }

        Client loggedClient = SessionManager.getInstance().getLoggedClient();

        String pickupLocationStr = CoordinateUtils.coordinateToString(currentLocation);

        String paymentMethod = "Cash"; // default

        RideRequestBean rideRequestBean = new RideRequestBean(currentLocation, destination.trim(), radiusKm, paymentMethod);
        rideRequestBean.setRequestID(null);
        rideRequestBean.setClient(loggedClient);
        rideRequestBean.setPickupLocation(pickupLocationStr);
        rideRequestBean.setDestination(destination.trim());

        try {
            rideRequestController.saveRideRequest(rideRequestBean);
            System.out.println("Ride request creata con successo");

            MapRequestBean mapRequestBean = new MapRequestBean(currentLocation, destination.trim(), radiusKm);

            // Salvo i dati temporanei
            TemporaryMemory.getInstance().setMapRequestBean(mapRequestBean);

            Map map = mapAppController.showMap(mapRequestBean);
            System.out.println("Mappa generata con successo");

            if (map == null || map.getHtmlContent() == null || map.getHtmlContent().isBlank()) {
                showAlert("Errore nel calcolo o visualizzazione del percorso.");
                return;
            }

            mapWebView.getEngine().loadContent(map.getHtmlContent());
            goToSelectTaxi(mapRequestBean, map);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Errore durante l'elaborazione della richiesta.");
        }
    }

    private void goToSelectTaxi(MapRequestBean bean, Map map) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource("/org/ispw/fastridetrack/views/SelectTaxi.fxml"));
            AnchorPane pane = loader.load();

            SelectTaxiGUIController controller = loader.getController();
            controller.setMapAndRequest(bean, map);

            Stage stage = (Stage) homePane.getScene().getWindow();
            stage.setTitle("Selezione Taxi");
            stage.setScene(new Scene(pane));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Errore nel caricamento della schermata di selezione taxi.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private int convertRangeToInt(String range) {
        try {
            return Integer.parseInt(range.split(" ")[0]);
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    @FXML
    private void onMyWallet() throws FXMLLoadException {
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/MyWallet.fxml", "Wallet");
    }

    @FXML
    private void onMyAccount() throws FXMLLoadException {
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/MyAccount.fxml", "Account");
    }

    @FXML
    private void onLogout() throws FXMLLoadException {
        SessionManager.getInstance().clearSession();
        TemporaryMemory.getInstance().clear();
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Homepage.fxml", "Homepage");
    }
}





