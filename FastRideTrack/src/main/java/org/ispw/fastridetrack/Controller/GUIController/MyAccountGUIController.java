package org.ispw.fastridetrack.Controller.GUIController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.ispw.fastridetrack.Bean.ClientBean;
import org.ispw.fastridetrack.Exception.FXMLLoadException;
import org.ispw.fastridetrack.Model.Client;
import org.ispw.fastridetrack.Model.Session.SessionManager;
import org.ispw.fastridetrack.Util.SceneNavigator;

public class MyAccountGUIController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField userIdField;
    @FXML private TextField userTypeField;
    @FXML private TextField phoneNumberField;
    @FXML public Button btnBack;

    @FXML
    public void initialize() {
        Client client = SessionManager.getInstance().getLoggedClient();

        if (client == null) {
            System.err.println("Nessun client loggato.");
            return;
        }

        // Usa il metodo fromModel per popolare il bean
        ClientBean clientBean = ClientBean.fromModel(client);

        // Imposta i campi (UserType lo forziamo a CLIENT come da tua richiesta)
        nameField.setText(clientBean.getName());
        emailField.setText(clientBean.getEmail());
        userIdField.setText(String.valueOf(clientBean.getUserID()));
        userTypeField.setText("CLIENT");
        phoneNumberField.setText(clientBean.getPhoneNumber());

        // Rendi i campi non editabili
        nameField.setEditable(false);
        emailField.setEditable(false);
        userIdField.setEditable(false);
        userTypeField.setEditable(false);
        phoneNumberField.setEditable(false);
    }

    @FXML
    private void onBackPressed() throws FXMLLoadException {
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Home.fxml", "Home");
    }
}


