package Controllers;

import Api.GeoLocalisationApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Service.ActiviteSportiveService;
import Models.ActiviteSportive;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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

            // Création de l'objet ActiviteSportive
            ActiviteSportive activite = new ActiviteSportive(sport, duree, Date.valueOf(date), prix, utilisateurId);

            // Création du service et appel de la méthode Create
            ActiviteSportiveService service = new ActiviteSportiveService();
            service.Create(activite);  // <-- Cette ligne permet d'ajouter l'activité à la base de données

            // Récupérer les données de la base après insertion
            List<ActiviteSportive> activites = service.getAllActivites();  // ✅ CORRECT

            // Affichage des activités dans la console (ou toute autre logique pour l'afficher)
            for (ActiviteSportive a : activites) {
                System.out.println("Activité : " + a.getSport() + ", Durée : " + a.getDuree() + " min, Date : " + a.getDate_activite() + ", Prix : " + a.getPrix_par_activite() + " €");
            }

            // **Appel à l'API de géolocalisation**
            String location = sport;  // Ou tu peux récupérer une autre donnée de localisation selon ton besoin
            String coordinates = GeoLocalisationApi.getCoordinates(location);  // Appel à l'API
            coordsLabel.setText(coordinates);  // Affichage des coordonnées dans le Label

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

    @FXML
    void cancelAction(ActionEvent event) throws IOException {
        // Utilisation du chemin relatif correct à partir de 'resources' dans le dossier 'src/main/resources'
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));  // Notez le '/'

        // Charge la scène dans un nouveau parent (root)
        Parent root = loader.load();

        // Crée la nouvelle scène et l'ajoute à la fenêtre principale (stage)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

        // Affiche la nouvelle scène
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
