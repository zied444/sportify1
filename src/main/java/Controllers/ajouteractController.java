package controllers;
import Api.GeoLocalisationApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ajouteractController {

    @FXML
    private TextField sportField;

    @FXML
    private TextField dureeField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField prixField;

    @FXML
    private TextField utilisateurIdField;

    @FXML
    private Label coordsLabel;  // Déclaration du Label pour les coordonnées

    @FXML
    void ajouter(ActionEvent event) {
        try {
            String sport = sportField.getText().trim();
            String dureeText = dureeField.getText().trim();
            String prixText = prixField.getText().trim();
            String utilisateurIdText = utilisateurIdField.getText().trim();
            LocalDate date = datePicker.getValue();

            if (sport.isEmpty() || dureeText.isEmpty() || prixText.isEmpty()
                    || utilisateurIdText.isEmpty() || date == null) {
                throw new IllegalArgumentException("Veuillez remplir tous les champs.");
            }

            int duree = Integer.parseInt(dureeText);
            double prix = Double.parseDouble(prixText);
            int utilisateurId = Integer.parseInt(utilisateurIdText);

            // Affichage console (ou base de données)
            System.out.println("Nouvelle activité ajoutée :");
            System.out.println("Sport : " + sport);
            System.out.println("Durée : " + duree + " min");
            System.out.println("Date : " + date);
            System.out.println("Prix : " + prix + " €");
            System.out.println("ID Utilisateur : " + utilisateurId);

            // Récupérer les coordonnées à partir de l'API
            String lieu = sportField.getText(); // Exemple : lieu basé sur le sport ou un autre champ
            String coords = GeoLocalisationApi.getCoordinates(lieu);
            System.out.println("Coordonnées : " + coords);

            // Mettre à jour le Label avec les coordonnées
            coordsLabel.setText("Coordonnées : " + coords);

            // Message succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Activité ajoutée avec succès !");
            alert.showAndWait();

            resetFields(null);

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez entrer des valeurs numériques valides.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.WARNING, "Champs manquants", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    @FXML
    void resetFields(ActionEvent event) {
        sportField.clear();
        dureeField.clear();
        prixField.clear();
        utilisateurIdField.clear();
        datePicker.setValue(null);
        coordsLabel.setText("Coordonnées : Non disponibles"); // Réinitialiser le label
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}



