package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import Models.ActiviteSportive;
import Service.ActiviteSportiveService;

import java.sql.Date;
import java.io.IOException;

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
    private ActiviteSportiveService activiteService;

    @FXML
    public void initialize() {
        activiteService = new ActiviteSportiveService();

        colId.setCellValueFactory(new PropertyValueFactory<>("id_activite"));
        colSport.setCellValueFactory(new PropertyValueFactory<>("sport"));
        colDuree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date_activite"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix_par_activite"));
        colUtilisateurId.setCellValueFactory(new PropertyValueFactory<>("utilisateur_id"));

        loadData();

        // Sélection d'une ligne
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedActivite = newSelection;
                sportField.setText(newSelection.getSport());
                dureeField.setText(String.valueOf(newSelection.getDuree()));
                datePicker.setValue(newSelection.getDate_activite().toLocalDate());
                prixField.setText(String.valueOf(newSelection.getPrix_par_activite()));
                utilisateurIdField.setText(String.valueOf(newSelection.getUtilisateur_id()));
            }
        });
    }

    private void loadData() {
        try {
            activiteList = FXCollections.observableArrayList(activiteService.getAllActivites());
            tableView.setItems(activiteList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de récupération des activités.");
        }
    }

    @FXML
    private void modifier(ActionEvent event) {
        if (selectedActivite == null) {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner une activité.");
            return;
        }
        try {
            selectedActivite.setSport(sportField.getText());
            selectedActivite.setDuree(Integer.parseInt(dureeField.getText()));
            selectedActivite.setDate_activite(Date.valueOf(datePicker.getValue()));
            selectedActivite.setPrix_par_activite(Double.parseDouble(prixField.getText()));
            selectedActivite.setUtilisateur_id(Integer.parseInt(utilisateurIdField.getText()));

            activiteService.updateActivite(selectedActivite);
            loadData();

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Activité modifiée avec succès.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Format numérique invalide.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la mise à jour.");
        }
    }

    @FXML
    private void annulerAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
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



