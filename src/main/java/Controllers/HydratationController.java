package Controllers;

import Models.Hydratation;
import Service.HydratationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.time.LocalDate;

public class HydratationController {

    @FXML
    private TextField amountField;
    @FXML
    private TextField performanceBeforeField;
    @FXML
    private TextField performanceAfterField;
    @FXML
    private VBox statsVBox;

    private HydratationService hydratationService;

    public HydratationController() {
        hydratationService = new HydratationService();
    }

    // Ajouter une nouvelle entrée d'hydratation
    @FXML
    private void addHydration() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            double performanceBefore = Double.parseDouble(performanceBeforeField.getText());
            double performanceAfter = Double.parseDouble(performanceAfterField.getText());

            Hydratation newHydration = new Hydratation(LocalDate.now(), amount, performanceBefore, performanceAfter);
            hydratationService.addHydratation(newHydration);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Données d'hydratation ajoutées avec succès !");

            // Vider les champs
            clearFields();
            updateStats();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer des valeurs valides.");
        }
    }

    // Annuler : effacer les champs
    @FXML
    private void annuler() {
        clearFields();
        showAlert(Alert.AlertType.INFORMATION, "Annulation", "Les champs ont été réinitialisés.");
    }

    private void clearFields() {
        amountField.clear();
        performanceBeforeField.clear();
        performanceAfterField.clear();
    }

    // Mettre à jour les statistiques
    private void updateStats() {
        double averageImpact = hydratationService.calculateAverageImpact();
        statsVBox.getChildren().clear();
        statsVBox.getChildren().add(new javafx.scene.control.Label("Impact moyen de l'hydratation : " + averageImpact));
    }

    // Afficher une alerte
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour revenir au menu
    @FXML
    private void goToMenu(ActionEvent event) {
        try {
            // Charger le fichier FXML du menu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Récupérer la fenêtre actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Mettre à jour la scène de la fenêtre actuelle
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

