package org.ispw.fastridetrack.Controller.GUIController;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.ispw.fastridetrack.Exception.FXMLLoadException;
import org.ispw.fastridetrack.Util.SceneNavigator;


import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomepageGUIController implements Initializable {

    @FXML public Button signInButton;
    @FXML public Button signUpButton;
    @FXML private ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/ispw/fastridetrack/images/Homepage.jpg")));
        imageView.setImage(image);
    }

    @FXML
    public void onSignInClick() throws FXMLLoadException {
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/SignIn.fxml","Sign in");
    }

    @FXML
    public void onSignUpClick() throws FXMLLoadException {
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/SignUpAs.fxml","Sign up");
    }
}

