package org.ispw.fastridetrack.Controller.GUIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.ispw.fastridetrack.Bean.MapRequestBean;
import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.Model.Client;
import org.ispw.fastridetrack.Model.Driver;
import org.ispw.fastridetrack.Model.Session.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectTaxiGUIController {

    @FXML
    private ChoiceBox<String> paymentChoiceBox;

    @FXML
    private Button confirmRideButton;

    @FXML
    private GridPane driverGridPane;

    private List<Button> driverButtons = new ArrayList<>();
    private Driver selectedDriver;
    private List<Driver> availableDrivers;
    private MapRequestBean mapRequestBean; // Viene settato da chi carica questa schermata

    @FXML
    public void initialize() {
        // Inizializza metodi di pagamento
        paymentChoiceBox.getItems().addAll("Cash", "Debit Card");

        // Recupera driver disponibili (mock o reale)
        availableDrivers = fetchAvailableDrivers();

        if (availableDrivers.isEmpty()) {
            showAlert("No drivers available at the moment.");
        } else {
            createDriverButtons();
        }
    }

    private void createDriverButtons() {
        driverGridPane.getChildren().clear();
        driverButtons.clear();

        for (int i = 0; i < availableDrivers.size(); i++) {
            Driver driver = availableDrivers.get(i);
            Button button = new Button("Driver " + (i + 1) + ": " + driver.getName());
            button.setPrefSize(325, 25);
            button.setStyle("-fx-background-color: transparent;");

            final int index = i;
            button.setOnAction(event -> {
                selectedDriver = availableDrivers.get(index);
                highlightSelectedDriverButton(index);
            });

            driverButtons.add(button);
            driverGridPane.add(button, 0, i);
        }
    }

    private void highlightSelectedDriverButton(int driverIndex) {
        resetButtonStyles();
        if (driverIndex >= 0 && driverIndex < driverButtons.size()) {
            driverButtons.get(driverIndex).setStyle("-fx-background-color: #add8e6;");
        }
    }

    private void resetButtonStyles() {
        driverButtons.forEach(button -> button.setStyle("-fx-background-color: transparent;"));
    }

    @FXML
    private void onConfirmRide(ActionEvent event) throws IOException {
        if (selectedDriver == null || paymentChoiceBox.getValue() == null) {
            showAlert("Please select a driver and a payment method.");
            return;
        }

        Client client = SessionManager.getInstance().getLoggedClient();
        if (client == null) {
            showAlert("No client logged in.");
            return;
        }

        TaxiRideConfirmationBean rideBean = new TaxiRideConfirmationBean();
        rideBean.setRideID(generateRideID());
        rideBean.setDriver(selectedDriver);
        rideBean.setClient(client);

        // Usa dati da mapRequestBean se presenti, altrimenti fallback statico
        if (mapRequestBean != null) {
            rideBean.setUserLocation(mapRequestBean.getUserLocation());
            rideBean.setDestination(mapRequestBean.getDestination());
        } else {
            rideBean.setUserLocation(List.of(41.9028, 12.4964)); // Roma centro
            rideBean.setDestination("Piazza Venezia");
        }

        rideBean.setStatus("Pending");
        rideBean.setEstimatedFare(15.0f);  // placeholder, calcola reale se vuoi
        rideBean.setEstimatedTime(12.0f);  // placeholder
        rideBean.setPaymentStatus(paymentChoiceBox.getValue());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ispw/fastridetrack/views/SelectDriver.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        // Passa dati al controller successivo
        SelectDriverGUIController controller = loader.getController();
        controller.initData(rideBean);
    }

    private int generateRideID() {
        return (int) (Math.random() * 100000);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private List<Driver> fetchAvailableDrivers() {
        List<Driver> list = new ArrayList<>();

        // Driver con costruttore corretto (aggiunti campi mancanti)
        list.add(new Driver(1, "mario", "pass", "Mario Rossi", "mario@example.com", "1234567890", "ibanMario", "AB123CD", "TaxiRoma"));
        list.add(new Driver(2, "luigi", "pass", "Luigi Verdi", "luigi@example.com", "0987654321", "ibanLuigi", "CD456EF", "TaxiRoma"));
        list.add(new Driver(3, "anna", "pass", "Anna Bianchi", "anna@example.com", "1122334455", "ibanAnna", "EF789GH", "TaxiRoma"));
        list.add(new Driver(4, "carlo", "pass", "Carlo Neri", "carlo@example.com", "6677889900", "ibanCarlo", "GH012IJ", "TaxiRoma"));
        list.add(new Driver(5, "elena", "pass", "Elena Gialli", "elena@example.com", "2233445566", "ibanElena", "IJ345KL", "TaxiRoma"));
        list.add(new Driver(6, "francesco", "pass", "Francesco Blu", "francesco@example.com", "9988776655", "ibanFrancesco", "KL678MN", "TaxiRoma"));
        list.add(new Driver(7, "giulia", "pass", "Giulia Rossi", "giulia@example.com", "5566778899", "ibanGiulia", "MN901OP", "TaxiRoma"));

        return list;
    }

    // Setter per ricevere dati da altri controller
    public void setMapRequestBean(MapRequestBean bean) {
        this.mapRequestBean = bean;
    }
}





