package com.spotify.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import com.spotify.models.PlanEntrainement;

public class MainController {
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

    @FXML
    public void initialize() {
        // Initialiser les colonnes de la table
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        titreColumn.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        contenuColumn.setCellValueFactory(cellData -> cellData.getValue().contenuProperty());
        dateDebutColumn.setCellValueFactory(cellData -> cellData.getValue().dateDebutProperty());
        dateFinColumn.setCellValueFactory(cellData -> cellData.getValue().dateFinProperty());
    }

    @FXML
    private void handleAjouterPlan() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterPlan.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter un Plan");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdatePlan() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UpdatePlan.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Modifier un Plan");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeletePlan() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DeletePlan.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Supprimer un Plan");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDisplayPlans() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DisplayPlans.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Afficher les Plans");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 