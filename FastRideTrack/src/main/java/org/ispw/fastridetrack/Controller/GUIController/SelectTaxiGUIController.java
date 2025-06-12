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

    @FXML private WebView mapView;

    @FXML private TableView<AvailableDriverBean> taxiTable;

    @FXML private TableColumn<AvailableDriverBean, String> driverNameColumn;

    @FXML private TableColumn<AvailableDriverBean, String> carModelColumn;

    @FXML private TableColumn<AvailableDriverBean, String> plateColumn;

    @FXML private TableColumn<AvailableDriverBean, Double> etaColumn;

    @FXML private TableColumn<AvailableDriverBean, Double> priceColumn;

    @FXML private ChoiceBox<String> paymentChoiceBox;

    @FXML private Button confirmRideButton;

    @FXML private Button goBackButton;

    @FXML private TextField destinationField;

    private MapRequestBean mapRequestBean;

    private final DriverMatchingApplicationController driverMatchingController = new DriverMatchingApplicationController();

    private final TemporaryMemory tempMemory = TemporaryMemory.getInstance();

    @FXML
    public void initialize() {
        initializeTable();
        initializePaymentChoices();
        destinationField.setEditable(false);

        // Carica dati temporanei se presenti
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

        if (tempMemory.getSelectedPaymentMethod() != null) {
            paymentChoiceBox.getSelectionModel().select(tempMemory.getSelectedPaymentMethod());
        }
    }

    /**
     * Metodo per impostare la mappa e la richiesta di corsa.
     * @param bean MapRequestBean con i dati della richiesta (solo Bean)
     * @param map Model Map contenente html della mappa
     */
    public void setMapAndRequest(MapRequestBean bean, Map map) {
        if (bean == null || map == null || map.getHtmlContent() == null) {
            showAlert("Dati mappa o richiesta non validi.");
            return;
        }
        this.mapRequestBean = bean;
        destinationField.setText(bean.getDestination());

        try {
            // Ottengo lista driver disponibili come Bean, passandogli solo il bean della richiesta
            List<AvailableDriverBean> drivers = driverMatchingController.findAvailableDrivers(bean);
            taxiTable.getItems().setAll(drivers);

            // Salvo dati temporanei
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
            // Creo RideRequestBean con i dati dalla richiesta mappa + metodo di pagamento
            RideRequestBean rideRequest = new RideRequestBean(
                    mapRequestBean.getOrigin(),
                    mapRequestBean.getDestination(),
                    mapRequestBean.getRadiusKm(),
                    paymentMethod
            );
            // Imposto driver e client come Model convertiti dai rispettivi Bean
            rideRequest.setDriver(selectedDriver);  // selectedDriver è AvailableDriverBean, ma devi convertire a DriverBean se non è già
            rideRequest.setClient(currentClient);    // currentClient è ClientBean

            // Salvo in memoria temporanea scelta driver e metodo pagamento
            tempMemory.setSelectedDriver(selectedDriver);
            tempMemory.setSelectedPaymentMethod(paymentMethod);

            // Salvo richiesta di corsa tramite application controller (che converte e salva)
            RideRequestBean savedRequest = driverMatchingController.saveRideRequest(rideRequest);

            // Assegno driver alla richiesta appena salvata tramite DriverAssignmentBean
            DriverAssignmentBean assignmentBean = new DriverAssignmentBean(
                    savedRequest.getRequestID(),
                    selectedDriver.toModel()
            );
            driverMatchingController.assignDriverToRequest(assignmentBean);

            // Creo TaxiRideConfirmationBean per confermare corsa
            TaxiRideConfirmationBean confirmationBean = new TaxiRideConfirmationBean(
                    savedRequest.getRequestID(),
                    DriverBean.fromModel(selectedDriver.toModel()),                  // DriverBean
                    ClientBean.fromModel(currentClient.toModel()),                   // ClientBean
                    savedRequest.getOriginAsCoordinateBean(),                        // CoordinateBean (assumendo sia già CoordinateBean)
                    savedRequest.getDestination(),                                   // String (destinazione)
                    "Pending",                                                      // stato iniziale
                    selectedDriver.getEstimatedPrice(),                              // tariffa stimata (Double)
                    selectedDriver.getEstimatedTime(),                               // tempo stimato (Double)
                    savedRequest.getPaymentMethod(),                                 // metodo pagamento (String)
                    LocalDateTime.now()                                              // ora conferma
            );


            tempMemory.setRideConfirmation(confirmationBean);

            // Passa a schermata SelectDriver.fxml
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







