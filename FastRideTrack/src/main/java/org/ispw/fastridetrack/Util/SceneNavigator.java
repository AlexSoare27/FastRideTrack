package org.ispw.fastridetrack.Util;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ispw.fastridetrack.Exception.FXMLLoadException;

public class SceneNavigator {
    private static Stage mainStage;

    public static void setStage(Stage stage) {
        mainStage = stage;
    }
    /**
     * Cambio scena caricando il file FXML indicato.
     * @param fxmlPath percorso relativo del file FXML
     * @param title titolo della finestra
     * @throws FXMLLoadException se il file FXML non può essere caricato
     */
    public static void switchTo(String fxmlPath, String title) throws FXMLLoadException {
        try {
            FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource(fxmlPath));
            Parent root = loader.load();
            mainStage.setTitle(title);
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (Exception e) {
            throw new FXMLLoadException("Errore nel caricamento del file FXML: " + fxmlPath, e);
        }
    }
}


