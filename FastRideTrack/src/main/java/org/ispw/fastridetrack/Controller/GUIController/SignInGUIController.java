package org.ispw.fastridetrack.Controller.GUIController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.ispw.fastridetrack.Controller.ApplicationController.LoginApplicationController;
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
            // Crea il controller senza il parametro booleano per la persistenza
            LoginApplicationController loginController = new LoginApplicationController();
            // Verifico le credenziali
            boolean isValid = loginController.validateClientCredentials(username, password, UserType.CLIENT);

            if (isValid) {
                // Crea la sessione dell'utente loggato
                loginController.createLoggedSession();
                SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Home.fxml", "Home");
            } else {
                showErrorAlert("Login Fallito", "Credenziali errate. Riprova.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // 👈 Stampa stack trace completo
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
    private void onHomepageClick() {
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Homepage.fxml", "Homepage");
    }
}


