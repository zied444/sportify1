package com.spotify.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import com.spotify.models.PlanEntrainement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DisplayPlansController {
    @FXML
    private TableView<PlanEntrainement> plansTable;
    @FXML
    private TableColumn<PlanEntrainement, Integer> idColumn;
    @FXML
    private TableColumn<PlanEntrainement, String> titreColumn;
    @FXML
    private TableColumn<PlanEntrainement, String> contenuColumn;
    @FXML
    private TableColumn<PlanEntrainement, String> dateDebutColumn;
    @FXML
    private TableColumn<PlanEntrainement, String> dateFinColumn;

    private ObservableList<PlanEntrainement> plansList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialiser les colonnes de la table
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        titreColumn.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        contenuColumn.setCellValueFactory(cellData -> cellData.getValue().contenuProperty());
        dateDebutColumn.setCellValueFactory(cellData -> cellData.getValue().dateDebutProperty());
        dateFinColumn.setCellValueFactory(cellData -> cellData.getValue().dateFinProperty());

        // TODO: Charger les plans depuis la base de données
        plansTable.setItems(plansList);
    }

    @FXML
    private void handleFermer() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) plansTable.getScene().getWindow();
        stage.close();
    }
} 