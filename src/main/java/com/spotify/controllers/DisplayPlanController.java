package com.spotify.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import com.spotify.models.PlanEntrainement;

public class DisplayPlanController {
    @FXML
    private TableView<PlanEntrainement> plansTable;
    
    @FXML
    private TableColumn<PlanEntrainement, String> nomColumn;
    
    @FXML
    private TableColumn<PlanEntrainement, String> utilisateurColumn;
    
    @FXML
    private TableColumn<PlanEntrainement, String> coachColumn;
    
    @FXML
    private TableColumn<PlanEntrainement, LocalDate> dateCreationColumn;
    
    @FXML
    private TableColumn<PlanEntrainement, String> typeActiviteColumn;
    
    @FXML
    private TableColumn<PlanEntrainement, String> contenuColumn;

    @FXML
    public void initialize() {
        // Configurer les colonnes
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        utilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("utilisateurNom"));
        coachColumn.setCellValueFactory(new PropertyValueFactory<>("coachNom"));
        dateCreationColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        typeActiviteColumn.setCellValueFactory(new PropertyValueFactory<>("typeActivite"));
        contenuColumn.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        
        // Charger les données
        loadPlans();
    }

    private void loadPlans() {
        // TODO: Appeler le service pour récupérer les plans
        // Exemple:
        // ObservableList<PlanEntrainement> plans = FXCollections.observableArrayList(planService.getAllPlans());
        // plansTable.setItems(plans);
    }
} 