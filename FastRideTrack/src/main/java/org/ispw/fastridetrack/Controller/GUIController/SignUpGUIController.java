package org.ispw.fastridetrack.Controller.GUIController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.ispw.fastridetrack.Util.SceneNavigator;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpGUIController implements Initializable {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField emailField;
    @FXML private ChoiceBox<String> userTypeChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inizializza solo con "Client"
        userTypeChoiceBox.getItems().add("Client");
        userTypeChoiceBox.setValue("Client");

        // Restringi phoneNumberField ai numeri
        phoneNumberField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                phoneNumberField.setText(newText.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private void onHomepage() {
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Homepage.fxml", "Homepage");
    }

    @FXML
    private void onSignUp() {
        // Qui posso anche validare i campi e poi procedere con la schermata iniziale dell'app!!
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Home.fxml", "Home");
    }
}


