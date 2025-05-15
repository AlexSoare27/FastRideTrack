package org.ispw.fastridetrack.Controller.GUIController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.ispw.fastridetrack.Bean.TaxiRideConfirmationBean;
import org.ispw.fastridetrack.Model.Driver;
import org.ispw.fastridetrack.DAO.Adapter.EmailDAO;
import org.ispw.fastridetrack.Model.Session.SessionManager;

public class SelectDriverGUIController {

    @FXML
    private Label driverNameLabel;
    @FXML
    private Label vehiclePlateLabel;
    @FXML
    private Label estimatedFareLabel;
    @FXML
    private Label estimatedTimeLabel;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;

    private TaxiRideConfirmationBean taxiRideBean;
    private EmailDAO emailDAO;

    public SelectDriverGUIController() {
        // Il DAO viene gestito dal SessionManager, che decide se usare l'implementazione persistente o in-memory
        this.emailDAO = SessionManager.getInstance().getEmailDAO();
    }

    // Questo metodo viene chiamato dal controller precedente (SelectTaxiGUIController)
    public void initData(TaxiRideConfirmationBean rideBean) {
        this.taxiRideBean = rideBean;

        // Mostra i dati del driver
        Driver driver = rideBean.getDriver();
        driverNameLabel.setText("Driver: " + driver.getName());
        vehiclePlateLabel.setText("Vehicle Plate: " + driver.getVehiclePlate());
        estimatedFareLabel.setText("Estimated Fare: €" + rideBean.getEstimatedFare());
        estimatedTimeLabel.setText("Estimated Time: " + rideBean.getEstimatedTime() + " mins");
    }

    // Metodo per confermare la corsa
    @FXML
    private void onConfirmRide() {
        if (taxiRideBean == null) {
            showError("Dati corsa non trovati", "Impossibile confermare la corsa.");
            return;
        }

        // Aggiorna lo stato della corsa
        taxiRideBean.setStatus("Confirmed");

        // Invio email al driver con i dettagli della corsa
        boolean emailSent = sendEmailToDriver();

        if (emailSent) {
            showInfo("Corsa confermata", "La corsa è stata confermata e il driver è stato avvisato.");
        } else {
            showError("Errore invio email", "Non è stato possibile inviare l'email al driver.");
        }
    }

    // Metodo per inviare l'email al driver
    private boolean sendEmailToDriver() {
        try {
            String subject = "Nuova corsa: " + taxiRideBean.getRideID();
            String message = "Caro " + taxiRideBean.getDriver().getName() + ",\n\n" +
                    "Hai ricevuto una nuova corsa:\n" +
                    "Cliente: " + taxiRideBean.getClient().getName() + "\n" +
                    "Destinazione: " + taxiRideBean.getDestination() + "\n" +
                    "Tariffa stimata: €" + taxiRideBean.getEstimatedFare() + "\n" +
                    "Tempo stimato: " + taxiRideBean.getEstimatedTime() + " mins\n\n" +
                    "Buon viaggio!";
            return emailDAO.sendEmail(taxiRideBean.getDriver().getEmail(), subject, message);
        } catch (Exception e) {
            return false;
        }
    }

    // Metodo per annullare l'azione e tornare alla schermata precedente
    @FXML
    private void onCancelRide() {
        // Tornare alla schermata precedente o gestire l'annullamento
    }

    // Metodo per mostrare un messaggio di errore
    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Metodo per mostrare un messaggio di successo
    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}





