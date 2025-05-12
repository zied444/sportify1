package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ActiviteSportive;

import java.sql.Date;

/**
 * Contrôleur pour afficher la liste des activités sportives.
 */
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

    private ObservableList<ActiviteSportive> activiteList;

    /**
     * Méthode d'initialisation appelée automatiquement après le chargement du FXML.
     */
    @FXML
    public void initialize() {
        // Initialisation des colonnes de la table
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSport.setCellValueFactory(new PropertyValueFactory<>("sport"));
        colDuree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateActivite"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colUtilisateurId.setCellValueFactory(new PropertyValueFactory<>("utilisateurId"));

        // Chargement initial des données
        loadData();
    }

    /**
     * Méthode appelée lors du clic sur "Actualiser" pour rafraîchir la table.
     */
    @FXML
    private void updateTable() {
        loadData();
    }

    /**
     * Charge les données dans l'ObservableList (à remplacer par une récupération DB).
     */
    private void loadData() {
        // Exemple de données statiques
        activiteList = FXCollections.observableArrayList(
                new ActiviteSportive(1, "Football", 90, Date.valueOf("2025-05-06"), 15.0, 101),
                new ActiviteSportive(2, "Natation", 60, Date.valueOf("2025-05-07"), 10.0, 102),
                new ActiviteSportive(3, "Yoga", 45, Date.valueOf("2025-05-08"), 12.5, 103)
        );

        tableView.setItems(activiteList);
    }
}
