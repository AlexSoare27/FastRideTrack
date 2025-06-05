package org.ispw.fastridetrack.Controller.GUIController;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.ispw.fastridetrack.Bean.*;
import org.ispw.fastridetrack.Controller.ApplicationController.DriverMatchingApplicationController;
import org.ispw.fastridetrack.Model.Map;
import org.ispw.fastridetrack.Model.Session.SessionManager;
import org.ispw.fastridetrack.Util.TemporaryMemory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class SelectTaxiGUIController {

    @FXML
    private AnchorPane selectTaxiPane;

    @FXML
    private WebView mapView;

    @FXML
    private TableView<AvailableDriverBean> taxiTable;

    @FXML
    private TableColumn<AvailableDriverBean, String> driverNameColumn;

    @FXML
    private TableColumn<AvailableDriverBean, String> carModelColumn;

    @FXML
    private TableColumn<AvailableDriverBean, String> plateColumn;

    @FXML
    private TableColumn<AvailableDriverBean, Double> etaColumn;

    @FXML
    private TableColumn<AvailableDriverBean, Double> priceColumn;

    @FXML
    private ChoiceBox<String> paymentChoiceBox;

    @FXML
    private Button confirmRideButton;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField destinationField;

    private MapRequestBean mapRequestBean;

    private final DriverMatchingApplicationController driverMatchingController = new DriverMatchingApplicationController();

    private final TemporaryMemory tempMemory = TemporaryMemory.getInstance();

    @FXML
    public void initialize() {
        initializeTable();
        initializePaymentChoices();
        destinationField.setEditable(false);

        // Se TemporaryMemory ha già dati, caricali (utile se torni da altre schermate)
        if (tempMemory.getMapRequestBean() != null) {
            this.mapRequestBean = tempMemory.getMapRequestBean();
            destinationField.setText(this.mapRequestBean.getDestination());
            if (tempMemory.getAvailableDrivers() != null) {
                taxiTable.getItems().setAll(tempMemory.getAvailableDrivers());
            }
        }
    }

    private void initializeTable() {
        driverNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        carModelColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getVehicleInfo()));
        plateColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getVehiclePlate()));
        etaColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEstimatedTime()));
        priceColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEstimatedPrice()));
    }

    private void initializePaymentChoices() {
        paymentChoiceBox.getItems().addAll("Cash", "Card");
        paymentChoiceBox.getSelectionModel().selectFirst();

        // Se TemporaryMemory ha un metodo di pagamento selezionato, impostalo
        if (tempMemory.getSelectedPaymentMethod() != null) {
            paymentChoiceBox.getSelectionModel().select(tempMemory.getSelectedPaymentMethod());
        }
    }

    public void setMapAndRequest(MapRequestBean bean, Map map) {
        if (bean == null || map == null || map.getHtmlContent() == null) {
            showAlert("Dati mappa o richiesta non validi.");
            return;
        }
        this.mapRequestBean = bean;
        destinationField.setText(bean.getDestination());

        try {
            List<AvailableDriverBean> drivers = driverMatchingController.findAvailableDrivers(bean);
            taxiTable.getItems().setAll(drivers);

            // Salva su TemporaryMemory
            tempMemory.setMapRequestBean(bean);
            tempMemory.setAvailableDrivers(drivers);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Errore nel caricamento dei driver disponibili.");
        }

        mapView.getEngine().loadContent(map.getHtmlContent());
    }

    @FXML
    private void onConfirmRide() {
        AvailableDriverBean selectedDriver = taxiTable.getSelectionModel().getSelectedItem();
        if (selectedDriver == null) {
            showAlert("Seleziona un driver dalla tabella.");
            return;
        }

        String paymentMethod = paymentChoiceBox.getSelectionModel().getSelectedItem();
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            showAlert("Seleziona un metodo di pagamento.");
            return;
        }

        ClientBean currentClient = ClientBean.fromModel(SessionManager.getInstance().getLoggedClient());
        if (currentClient == null) {
            showAlert("Utente non loggato o client non trovato.");
            return;
        }

        try {
            // 1. Creazione richiesta corsa
            RideRequestBean rideRequest = new RideRequestBean(
                    mapRequestBean.getOrigin(),
                    mapRequestBean.getDestination(),
                    mapRequestBean.getRadiusKm(),
                    paymentMethod
            );
            rideRequest.setDriver(selectedDriver.toModel());
            rideRequest.setClient(currentClient.toModel());

            RideRequestBean savedRequest = driverMatchingController.saveRideRequest(rideRequest);

            // 2. Salvataggio in TemporaryMemory
            tempMemory.setSelectedDriver(selectedDriver);
            tempMemory.setSelectedPaymentMethod(paymentMethod);

            // 3. Creazione TaxiRideConfirmationBean
            TaxiRideConfirmationBean confirmationBean = new TaxiRideConfirmationBean(
                    savedRequest.getRequestID(),
                    selectedDriver.toModel(),                      // Driver
                    currentClient.toModel(),                        // Client
                    savedRequest.getOriginAsCoordinateBean(),      // CoordinateBean (origine)
                    savedRequest.getDestination(),                  // String (destinazione)
                    "Pending",                                      // status iniziale
                    selectedDriver.getEstimatedPrice(),             // double estimatedFare
                    selectedDriver.getEstimatedTime(),              // double estimatedTime
                    savedRequest.getPaymentMethod(),                 // paymentStatus? o "Pending" se non hai ancora stato
                    LocalDateTime.now()                              // confirmationTime
            );


            tempMemory.setRideConfirmation(confirmationBean);

            // 4. Vai a SelectDriver.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ispw/fastridetrack/views/SelectDriver.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = (Stage) confirmRideButton.getScene().getWindow();
            stage.setTitle("Conferma Driver");
            stage.setScene(new Scene(pane));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Errore nella conferma della corsa.");
        }
    }

    @FXML
    private void onGoBackHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/ispw/fastridetrack/views/Home.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = (Stage) goBackButton.getScene().getWindow();
            stage.setTitle("Home");
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Errore nel ritorno alla schermata Home.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attenzione");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}






