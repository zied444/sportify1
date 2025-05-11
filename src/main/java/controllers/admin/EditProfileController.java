package controllers.admin;

import entities.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UtilisateurService;
import utils.AlertUtils;
import utils.PasswordHasher;

import java.io.IOException;

public class EditProfileController {
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    private UtilisateurService userService;
    private Utilisateur currentAdmin;

    public void initialize() {
        userService = new UtilisateurService();
    }

    public void setCurrentAdmin(Utilisateur admin) {
        this.currentAdmin = admin;
        // Remplir les champs avec les données actuelles
        nomField.setText(admin.getNom());
        prenomField.setText(admin.getPrenom());
        emailField.setText(admin.getEmail());
    }

    @FXML
    private void handleSave() {
        // Vérifier que tous les champs sont remplis
        if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() || 
            emailField.getText().isEmpty()) {
            AlertUtils.showError("Erreur", "Veuillez remplir tous les champs obligatoires");
            return;
        }

        // Vérifier les mots de passe si modifiés
        if (!passwordField.getText().isEmpty()) {
            if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                AlertUtils.showError("Erreur", "Les mots de passe ne correspondent pas");
                return;
            }
            currentAdmin.setPassword(passwordField.getText());
        }

        // Mettre à jour les informations
        currentAdmin.setNom(nomField.getText());
        currentAdmin.setPrenom(prenomField.getText());
        currentAdmin.setEmail(emailField.getText());

        // Sauvegarder les modifications
        userService.modifierUtilisateur(currentAdmin);

        AlertUtils.showInfo("Succès", "Profil modifié avec succès");
        handleCancel();
    }

    @FXML
    private void handleCancel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/adminDashboard.fxml"));
            Parent root = loader.load();
            
            AdminDashboardController adminController = loader.getController();
            adminController.setCurrentAdmin(currentAdmin);
            
            Stage stage = (Stage) nomField.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showError("Erreur", "Impossible de retourner au tableau de bord");
        }
    }
} 