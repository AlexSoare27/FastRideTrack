package org.ispw.fastridetrack.Controller.GUIController;

import javafx.fxml.FXML;
import org.ispw.fastridetrack.Exception.FXMLLoadException;
import org.ispw.fastridetrack.Util.SceneNavigator;

public class SignUpAsGUIController {

    @FXML
    public void onClientSignUp() throws FXMLLoadException {
        // Cambio scena a SignUp.fxml per il client
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/SignUp.fxml", "Sign Up-Client");
    }

    @FXML
    public void onDriverSignUp() throws FXMLLoadException {
        // Cambia scena a ?????.fxml per il driver (quella di Alex)
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Sign Up.fxml", "Sign Up-Driver");
    }

    @FXML
    public void onBackToHomepage() throws FXMLLoadException {
        // Torno alla schermata Homepage
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Homepage.fxml", "Homepage");
    }
}


