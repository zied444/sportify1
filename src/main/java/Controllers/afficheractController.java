package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import Models.ActiviteSportive;
import Service.ActiviteSportiveService;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;

public class afficheractController {

    @FXML
    private TableView<ActiviteSportive> tableView;

    @FXML
    private TableColumn<ActiviteSportive, Integer> colId;

    @FXML
    private TableColumn<ActiviteSportive, String> colSport;

    @FXML
    private TableColumn<ActiviteSportive, Integer> colDuree;

    @FXML
    private TableColumn<ActiviteSportive, Date> colDate;

    @FXML
    private TableColumn<ActiviteSportive, Double> colPrix;

    @FXML
    private TableColumn<ActiviteSportive, Integer> colUtilisateurId;

    private final ActiviteSportiveService activiteService = new ActiviteSportiveService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id_activite"));
        colSport.setCellValueFactory(new PropertyValueFactory<>("sport"));
        colDuree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date_activite"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix_par_activite"));
        colUtilisateurId.setCellValueFactory(new PropertyValueFactory<>("utilisateur_id"));

        loadData();
    }

    @FXML
    private void updateTable(ActionEvent event) {
        loadData();
    }

    private void loadData() {
        try {
            ObservableList<ActiviteSportive> list = FXCollections.observableArrayList(
                    activiteService.getAllActivites()
            );
            tableView.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les données depuis la base.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // ✅ Correction : méthode manquante pour le bouton "Annuler"
    @FXML
    private void annulerAction(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
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



