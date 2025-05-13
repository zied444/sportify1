package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class MenuController {

    // Méthode pour changer de scène
    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Récupérer la fenêtre actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Mettre à jour la scène de la fenêtre actuelle
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            // Afficher l'erreur en cas d'échec du chargement
            e.printStackTrace();
        }
    }

    // Méthodes d'action pour chaque bouton
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

    @FXML
    public void goToVisualisationGraphique(ActionEvent event) {
        switchScene(event, "/fxml/visualisationGraphique.fxml");
    }

    @FXML
    public void goToCalories(ActionEvent event) {
        switchScene(event, "/fxml/AjouterActAvancee.fxml");
    }

    @FXML
    public void goToHydratation(ActionEvent event) {
        switchScene(event, "/fxml/hydratation.fxml");
    }
}

