package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import Service.ActiviteSportiveService;
import Models.ActiviteSportive;

import java.io.IOException;
import java.sql.SQLException;

public class supprimeractController {

    @FXML
    private TableView<ActiviteSportive> tableView;

    @FXML
    private TableColumn<ActiviteSportive, Integer> colId;

    @FXML
    private TableColumn<ActiviteSportive, String> colSport;

    @FXML
    private TableColumn<ActiviteSportive, Integer> colDuree;

    @FXML
    private TableColumn<ActiviteSportive, String> colDate;

    @FXML
    private TableColumn<ActiviteSportive, Double> colPrix;

    @FXML
    private TableColumn<ActiviteSportive, Integer> colUtilisateurId;

    @FXML
    private Button supprimerButton;

    @FXML
    private Button btnRetour;

    private ObservableList<ActiviteSportive> activiteList;
    private ActiviteSportiveService activiteService;

    @FXML
    public void initialize() {
        activiteService = new ActiviteSportiveService();

        // Configuration des colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id_activite"));
        colSport.setCellValueFactory(new PropertyValueFactory<>("sport"));
        colDuree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date_activite"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix_par_activite"));
        colUtilisateurId.setCellValueFactory(new PropertyValueFactory<>("utilisateur_id"));

        loadData(); // Charger les données initiales
    }

    private void loadData() {
        try {
            // Récupérer les données actuelles depuis la base de données
            activiteList = FXCollections.observableArrayList(activiteService.getAllActivites());
            tableView.setItems(activiteList);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de récupération des activités.");
        }
    }

    @FXML
    private void supprimer() {
        ActiviteSportive selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                // Supprimer de la base de données
                activiteService.delete(selected);

                // Supprimer de la liste observable et rafraîchir la table
                activiteList.remove(selected);
                tableView.refresh();

                showAlert(Alert.AlertType.INFORMATION, "Supprimé", "Activité supprimée avec succès.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de suppression de l'activité.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner une activité à supprimer.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
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





