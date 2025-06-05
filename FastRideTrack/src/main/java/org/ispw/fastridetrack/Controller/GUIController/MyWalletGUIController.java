package org.ispw.fastridetrack.Controller.GUIController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.ispw.fastridetrack.Exception.FXMLLoadException;
import org.ispw.fastridetrack.Util.SceneNavigator;

public class MyWalletGUIController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnConfirm;

    @FXML
    private CheckBox checkBoxCash;

    @FXML
    private CheckBox checkBoxCard;

    @FXML
    public void initialize() {
        // Rende le checkbox mutuamente esclusive
        checkBoxCash.setOnAction(e -> {
            if (checkBoxCash.isSelected()) {
                checkBoxCard.setSelected(false);
            }
        });

        checkBoxCard.setOnAction(e -> {
            if (checkBoxCard.isSelected()) {
                checkBoxCash.setSelected(false);
            }
        });
    }

    @FXML
    void onBackPressed(ActionEvent event) throws FXMLLoadException {
        SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Home.fxml", "Home");
    }

    @FXML
    void onConfirmPressed(ActionEvent event) throws FXMLLoadException {
        if (checkBoxCard.isSelected()) {
            showAddCardDialog();
        } else if (checkBoxCash.isSelected()) {
            System.out.println("Pagamento in contanti selezionato.");
            SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Home.fxml", "Home");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText("Metodo di pagamento non selezionato");
            alert.setContentText("Per favore seleziona un metodo di pagamento.");
            alert.showAndWait();
        }
    }

    private void showAddCardDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Aggiungi Carta");
        dialog.setHeaderText("Inserisci i dati della tua carta");

        ButtonType addButtonType = new ButtonType("Aggiungi", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField cardNumber = new TextField();
        cardNumber.setPromptText("Numero carta");

        TextField cardHolder = new TextField();
        cardHolder.setPromptText("Intestatario");

        TextField expiryDate = new TextField();
        expiryDate.setPromptText("MM/YY");

        PasswordField cvv = new PasswordField();
        cvv.setPromptText("CVV");

        grid.add(new Label("Numero carta:"), 0, 0);
        grid.add(cardNumber, 1, 0);
        grid.add(new Label("Intestatario:"), 0, 1);
        grid.add(cardHolder, 1, 1);
        grid.add(new Label("Scadenza:"), 0, 2);
        grid.add(expiryDate, 1, 2);
        grid.add(new Label("CVV:"), 0, 3);
        grid.add(cvv, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                System.out.println("Carta aggiunta:");
                System.out.println("Numero: " + cardNumber.getText());
                System.out.println("Intestatario: " + cardHolder.getText());
                System.out.println("Scadenza: " + expiryDate.getText());
                System.out.println("CVV: " + cvv.getText());

                try {
                    SceneNavigator.switchTo("/org/ispw/fastridetrack/views/Home.fxml", "Home");
                } catch (FXMLLoadException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });

        dialog.showAndWait();
    }
}



