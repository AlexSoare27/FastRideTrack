package org.ispw.fastridetrack.Controller.GUIController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import org.ispw.fastridetrack.Bean.MapRequestBean;
import org.ispw.fastridetrack.DAO.Adapter.MapDAO;
import org.ispw.fastridetrack.Model.Client;
import org.ispw.fastridetrack.Model.Session.SessionManager;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeGUIController implements Initializable {

    @FXML
    private AnchorPane homePane;
    @FXML
    private Polygon triangle;
    @FXML
    private Pane mapPane;
    @FXML
    private ChoiceBox<String> rangeChoiceBox;
    @FXML
    private TextField destinationField;
    @FXML
    private Button checkRiderButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button homepageButton;
    @FXML
    private Button myAccountButton;
    @FXML
    private Button myWalletButton;
    @FXML
    private Label welcomeLabel;
    @FXML
    private WebView mapWebView;

    private MapDAO mapDAO;
    private final String currentLocation = "Via Roma 10, Napoli"; // Mock posizione attuale

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mapDAO = SessionManager.getInstance().getMapDAO();
        showGPSAlert();
        initializeChoiceBox();
        displayUserLocationOnMap();
        displayUserName();
    }

    private void displayUserName() {
        Client client = SessionManager.getInstance().getLoggedClient();  // Ottengo l'utente loggato
        if (client != null) {
            welcomeLabel.setText("Benvenuto, " + client.getName());  // Visualizzo il nome
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
        rangeChoiceBox.getItems().addAll("1 km", "3 km", "5 km", "10 km");
        rangeChoiceBox.setValue("1 km"); // default
    }

    private void displayUserLocationOnMap() {
        triangle.setLayoutX(mapPane.getPrefWidth() / 2);
        triangle.setLayoutY(mapPane.getPrefHeight() / 2);
    }

    @FXML
    private void onCheckRider() {
        String destination = destinationField.getText();
        String range = rangeChoiceBox.getValue();

        if (destination == null || destination.isBlank()) {
            showAlert("Inserisci una destinazione valida.");
            return;
        }

        int radiusKm = convertRangeToInt(range);
        if (radiusKm == -1) {
            showAlert("Raggio non valido.");
            return;
        }

        // Simula mappa
        String routeHtml = mapDAO.showRoute(currentLocation, destination);
        mapWebView.getEngine().loadContent(routeHtml);  // Visualizza mappa nel WebView

        // Crea e passa la bean
        MapRequestBean requestBean = new MapRequestBean(currentLocation, destination, radiusKm);
        goToSelectTaxi(requestBean);
    }


    private void goToSelectTaxi(MapRequestBean bean) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ispw/fastridetrack/views/SelectTaxi.fxml"));
            AnchorPane pane = loader.load();

            // Ottieni il controller dopo il caricamento
            SelectTaxiGUIController controller = loader.getController();
            controller.setMapRequestBean(bean);

            Stage stage = (Stage) homePane.getScene().getWindow();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Errore nel caricamento della schermata SelectTaxi.");
        }
    }


    @FXML
    private void onMyWallet() {
        loadFXML("/org/ispw/fastridetrack/views/MyWallet.fxml");
    }

    @FXML
    private void onMyAccount() {
        loadFXML("/org/ispw/fastridetrack/views/MyAccount.fxml");
    }

    @FXML
    private void onLogout() {
        loadFXML("/org/ispw/fastridetrack/views/Homepage.fxml");
    }

    private void loadFXML(String fxmlPath) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) homePane.getScene().getWindow();
            stage.setScene(new Scene(pane));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Errore nel caricamento della schermata.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private int convertRangeToInt(String range) {
        // Rimuovi " km" e convertilo in int
        try {
            return Integer.parseInt(range.split(" ")[0]);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}






