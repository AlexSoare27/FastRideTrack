package org.ispw.fastridetrack.controller.guicontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.ispw.fastridetrack.bean.ClientBean;
import org.ispw.fastridetrack.controller.ApplicationFacade;
import org.ispw.fastridetrack.exception.FXMLLoadException;

import static org.ispw.fastridetrack.util.ViewPathFXML.HOMECLIENT_FXML;

public class MyAccountGUIController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField userIdField;
    @FXML private TextField userTypeField;
    @FXML private TextField phoneNumberField;
    @FXML public Button btnBack;

    // Facade iniettata da SceneNavigator
    @SuppressWarnings("java:S1104") // Field injection is intentional for SceneNavigator
    private ApplicationFacade facade;

    // Setter usato da SceneNavigator per iniettare il facade
    public void setFacade(ApplicationFacade facade) {
        this.facade = facade;
    }

    @FXML
    public void initialize() {
        ClientBean clientBean = facade.getSessionDataAC().getClientBean();

        if (clientBean == null) {
            System.err.println("Nessun client loggato.");
            return;
        }

        // Uso il metodo fromModel per popolare il bean
        //ClientBean clientBean = client;

        // Imposta i campi (UserType lo forziamo a CLIENT come da tua richiesta)
        nameField.setText(clientBean.getName());
        emailField.setText(clientBean.getEmail());
        userIdField.setText(String.valueOf(clientBean.getUserID()));
        userTypeField.setText("CLIENT");
        phoneNumberField.setText(clientBean.getPhoneNumber());

        // Rendo i campi non editabili
        nameField.setEditable(false);
        emailField.setEditable(false);
        userIdField.setEditable(false);
        userTypeField.setEditable(false);
        phoneNumberField.setEditable(false);
    }

    @FXML
    private void onBackPressed() throws FXMLLoadException {
        SceneNavigator.switchTo(HOMECLIENT_FXML, "Home");
    }
}


