package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ActiviteSportive;

import java.sql.Date;

/**
 * Contrôleur pour supprimer une activité sportive existante.
 */
public class supprimeractController {

    @FXML
    private TableView<ActiviteSportive> tableView;

    @FXML
    private TableColumn<ActiviteSportive, Integer> colId;

    @FXML
    private TableColumn<ActiviteSportive, String> colSport;

    @FXML
    private TableColumn<ActiviteSportive, Date> colDate;

    @FXML
    private Button supprimerButton;

    private ObservableList<ActiviteSportive> activiteList;

    /**
     * Initialisation automatique après le chargement du FXML.
     */
    @FXML
    public void initialize() {
        // Configuration des colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSport.setCellValueFactory(new PropertyValueFactory<>("sport"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateActivite"));

        // Charger les données initiales
        loadData();
    }

    /**
     * Charge les données dans la table (à remplacer par récupération BD).
     */
    private void loadData() {
        activiteList = FXCollections.observableArrayList(
                new ActiviteSportive(1, "Football", 90, Date.valueOf("2025-05-06"), 15.0, 101),
                new ActiviteSportive(2, "Natation", 60, Date.valueOf("2025-05-07"), 10.0, 102),
                new ActiviteSportive(3, "Yoga", 45, Date.valueOf("2025-05-08"), 12.5, 103)
        );
        tableView.setItems(activiteList);
    }

    /**
     * Supprime l'activité sélectionnée.
     */
    @FXML
    private void supprimer() {
        ActiviteSportive selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            activiteList.remove(selected);
            tableView.refresh();
            showAlert(Alert.AlertType.INFORMATION, "Supprimé", "Activité supprimée avec succès.");
            // TODO: supprimer de la base de données si nécessaire
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner une activité à supprimer.");
        }
    }

    /**
     * Affiche une alerte.
     */
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
