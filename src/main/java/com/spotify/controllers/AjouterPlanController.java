package com.spotify.controllers;

import com.spotify.models.PlanEntrainement;
import com.spotify.services.PlanEntrainementService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.format.DateTimeFormatter;

public class AjouterPlanController {
    @FXML private TextField titreField;
    @FXML private TextArea contenuField;
    @FXML private DatePicker dateDebutPicker;
    @FXML private DatePicker dateFinPicker;
    @FXML private Button ajouterButton;
    @FXML private Button annulerButton;
    @FXML private Label messageLabel;

    private PlanEntrainementService planService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    public void initialize() {
        planService = new PlanEntrainementService();
    }

    @FXML
    private void handleAjouter() {
        try {
            PlanEntrainement plan = new PlanEntrainement();
            plan.setTitre(titreField.getText());
            plan.setContenu(contenuField.getText());
            plan.setDateDebut(dateDebutPicker.getValue().format(formatter));
            plan.setDateFin(dateFinPicker.getValue().format(formatter));

            planService.ajouterPlan(plan);
            closeWindow();
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de l'ajout du plan: " + e.getMessage());
        }
    }

    @FXML
    private void handleAnnuler() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) titreField.getScene().getWindow();
        stage.close();
    }
} 