package org.ispw.fastridetrack.Controller.GUIController;

import javafx.fxml.FXML;
import org.ispw.fastridetrack.Exception.FXMLLoadException;
import org.ispw.fastridetrack.Util.SceneNavigator;

public class SignUpAsGUIController {

    // Metodo che gestisce il click su "Client"
    @FXML
    public void onClientSignUp() throws FXMLLoadException {
        // Forza l'UserType a "CLIENT"
        // Cambia scena a SignUp.fxml per il client
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/SignUp.fxml", "Sign Up-Client");
    }

    // Metodo che gestisce il click su "Driver"
    @FXML
    public void onDriverSignUp() throws FXMLLoadException {
        // Forza l'UserType a "DRIVER"
        // Cambia scena a ?????.fxml per il driver (quella di Alex)
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Sign Up.fxml", "Sign Up-Driver");
    }

    // Metodo che gestisce il click su "Back to Homepage"
    @FXML
    public void onBackToHomepage() throws FXMLLoadException {
        // Torna alla schermata Homepage
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Homepage.fxml", "Homepage");
    }
}


