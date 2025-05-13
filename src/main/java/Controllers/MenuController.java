package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MenuController {

    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Activité Sportive");
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer l'ancienne fenêtre (celle du menu)
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToAjouter(ActionEvent event) {
        switchScene(event, "/fxml/ajouteract.fxml");
    }

    @FXML
    public void goToModifier(ActionEvent event) {
        switchScene(event, "/fxml/modifieract.fxml");
    }

    @FXML
    public void goToSupprimer(ActionEvent event) {
        switchScene(event, "/fxml/supprimeract.fxml");
    }

    @FXML
    public void goToAfficher(ActionEvent event) {
        switchScene(event, "/fxml/afficheract.fxml");
    }
}

