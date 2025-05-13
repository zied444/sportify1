package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ActiviteSportive;

import java.sql.Date;

/**
 * Contrôleur pour modifier une activité sportive existante.
 */
public class modifieractController {

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

    private ObservableList<ActiviteSportive> activiteList;
    private ActiviteSportive selectedActivite;

    /**
     * Initialise la table et ses colonnes, puis charge les données.
     */
    @FXML
    public void initialize() {
        // Configuration des colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSport.setCellValueFactory(new PropertyValueFactory<>("sport"));
        colDuree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateActivite"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colUtilisateurId.setCellValueFactory(new PropertyValueFactory<>("utilisateurId"));

        // Charger les données initiales
        loadData();

        // Ajout d'un listener pour la sélection
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selectedActivite = newSel;
                sportField.setText(newSel.getSport());
                dureeField.setText(String.valueOf(newSel.getDuree()));
                datePicker.setValue(newSel.getDateActivite().toLocalDate());
                prixField.setText(String.valueOf(newSel.getPrix()));
                utilisateurIdField.setText(String.valueOf(newSel.getUtilisateurId()));
            }
        });
    }

    /**
     * Charge ou recharge la liste d'activités (à remplacer par récupération depuis la BDD).
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
     * Méthode appelée lors du clic sur le bouton "Modifier".
     */
    @FXML
    private void modifier(ActionEvent event) {
        if (selectedActivite == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner une activité à modifier.");
            return;
        }
        try {
            // Mise à jour des attributs
            selectedActivite.setSport(sportField.getText());
            selectedActivite.setDuree(Integer.parseInt(dureeField.getText()));
            selectedActivite.setDateActivite(Date.valueOf(datePicker.getValue()));
            selectedActivite.setPrix(Double.parseDouble(prixField.getText()));
            selectedActivite.setUtilisateurId(Integer.parseInt(utilisateurIdField.getText()));

            tableView.refresh();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Activité modifiée avec succès.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Vérifiez les champs numériques.");
        }
    }

    /**
     * Affiche un message d'alerte.
     */
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

