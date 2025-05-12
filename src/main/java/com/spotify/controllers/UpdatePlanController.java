package com.spotify.controllers;

import com.spotify.models.PlanEntrainement;
import com.spotify.services.PlanEntrainementService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UpdatePlanController {
    @FXML private TextField idField;
    @FXML private TextField titreField;
    @FXML private TextArea contenuField;
    @FXML private DatePicker dateDebutPicker;
    @FXML private DatePicker dateFinPicker;
    @FXML private Button modifierButton;
    @FXML private Button annulerButton;
    @FXML private Label messageLabel;

    private PlanEntrainementService planService;
    private PlanEntrainement planToUpdate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    public void initialize() {
        planService = new PlanEntrainementService();
    }

    public void setPlanToUpdate(PlanEntrainement plan) {
        this.planToUpdate = plan;
        idField.setText(String.valueOf(plan.getId()));
        titreField.setText(plan.getTitre());
        contenuField.setText(plan.getContenu());
        dateDebutPicker.setValue(LocalDate.parse(plan.getDateDebut(), formatter));
        dateFinPicker.setValue(LocalDate.parse(plan.getDateFin(), formatter));
    }

    @FXML
    private void handleModifier() {
        try {
            int id = Integer.parseInt(idField.getText());
            PlanEntrainement plan = new PlanEntrainement();
            plan.setId(id);
            plan.setTitre(titreField.getText());
            plan.setContenu(contenuField.getText());
            plan.setDateDebut(dateDebutPicker.getValue().format(formatter));
            plan.setDateFin(dateFinPicker.getValue().format(formatter));

            planService.modifierPlan(plan);
            closeWindow();
        } catch (NumberFormatException e) {
            messageLabel.setText("ID invalide");
        } catch (Exception e) {
            messageLabel.setText("Erreur lors de la modification du plan: " + e.getMessage());
        }
    }

    @FXML
    private void handleAnnuler() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }
} 