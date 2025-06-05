package org.ispw.fastridetrack.Controller.GUIController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.ispw.fastridetrack.Controller.ApplicationController.LoginApplicationController;
import org.ispw.fastridetrack.Exception.FXMLLoadException;
import org.ispw.fastridetrack.Model.UserType;
import org.ispw.fastridetrack.Util.SceneNavigator;

public class SignInGUIController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    // Metodo che gestisce l'evento di click sul pulsante "Next"
    @FXML
    private void onNextClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            LoginApplicationController loginController = new LoginApplicationController();
            boolean isValid = loginController.validateClientCredentials(username, password, UserType.CLIENT);
            boolean isValidDriver = loginController.validateDriverCredentials(username, password, UserType.DRIVER);

            if (isValid) {
                SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Home.fxml", "Home");
            }else if(isValidDriver){
                SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Home_driver.fxml", "Login");
            }else {
                showErrorAlert("Login Fallito", "Credenziali errate. Riprova.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Errore di connessione", "Impossibile connettersi al database:\n" + e.getMessage());
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Metodo che gestisce l'evento di click sul pulsante "Homepage"
    @FXML
    private void onHomepageClick() throws FXMLLoadException {
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Homepage.fxml", "Homepage");
    }
}


