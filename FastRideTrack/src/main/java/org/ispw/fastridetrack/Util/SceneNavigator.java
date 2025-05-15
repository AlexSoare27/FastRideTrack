package org.ispw.fastridetrack.Util;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneNavigator {
    private static Stage mainStage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static void switchTo(String fxmlPath, String title) {
        try {
            // Usa il getClass().getResource per il caricamento del file FXML
            FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource(fxmlPath));
            Parent root = loader.load();  // Carica il file FXML
            mainStage.setTitle(title);  // Imposta il titolo della finestra
            mainStage.setScene(new Scene(root));  // Crea una nuova scena e la imposta
            mainStage.show();  // Mostra la finestra
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore nel caricamento del file FXML: " + fxmlPath);
        }
    }

}

