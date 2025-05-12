package com.spotify.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.spotify.models.PlanEntrainement;

public class DeletePlanController {
    @FXML
    private TextField idField;

    @FXML
    private void handleSupprimer() {
        try {
            int id = Integer.parseInt(idField.getText());
            
            // TODO: Ajouter la logique pour supprimer le plan de la base de données

            closeWindow();
        } catch (NumberFormatException e) {
            // TODO: Afficher un message d'erreur pour l'ID invalide
        } catch (Exception e) {
            e.printStackTrace();
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