package org.ispw.fastridetrack;

import javafx.application.Application;
import javafx.stage.Stage;
import org.ispw.fastridetrack.controller.ApplicationFacade;
import org.ispw.fastridetrack.model.session.SessionManager;
import org.ispw.fastridetrack.controller.SceneNavigator;

import static org.ispw.fastridetrack.util.ViewPath.HOMEPAGE_FXML;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SessionManager.init();
        SceneNavigator.setStage(primaryStage);
        SceneNavigator.setFacade(new ApplicationFacade());
        SceneNavigator.switchTo(HOMEPAGE_FXML, "Homepage");
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


