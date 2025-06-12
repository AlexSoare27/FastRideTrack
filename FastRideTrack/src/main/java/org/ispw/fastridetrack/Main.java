package org.ispw.fastridetrack;

import javafx.application.Application;
import javafx.stage.Stage;
import org.ispw.fastridetrack.Model.Session.SessionManager;
import org.ispw.fastridetrack.Util.SceneNavigator;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SessionManager.init();
        SceneNavigator.setStage(primaryStage);
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Homepage.fxml", "Homepage");

        // Aggiunta dell'handler che chiude DB e termina la GUI quando si chiude la finestra
        primaryStage.setOnCloseRequest(event -> SessionManager.getInstance().shutdown());
    }

    @Override
    public void stop() throws Exception {
        SessionManager.getInstance().shutdown();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


