package org.ispw.fastridetrack;

import javafx.application.Application;
import javafx.stage.Stage;
import org.ispw.fastridetrack.Model.Session.SessionManager;
import org.ispw.fastridetrack.Util.SceneNavigator;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Inizializzazione globale della persistenza in SessionManager
        SessionManager.init();
        SceneNavigator.setStage(primaryStage);
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Homepage.fxml", "Homepage");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

