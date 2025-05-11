package controllers.utilisateur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import entities.Utilisateur;
import utils.AlertUtils;
import service.UtilisateurService;
import utils.NavigationUtils;

import java.io.IOException;

public class EditProfileController {
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField preferencesField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Utilisateur currentUser;
    private Stage stage;

    @FXML
    private void initialize() {
        // Activer le bouton de sauvegarde uniquement si tous les champs sont remplis
        saveButton.setDisable(true);
        emailField.textProperty().addListener((obs, oldVal, newVal) -> validateFields());
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> validateFields());
        nomField.textProperty().addListener((obs, oldVal, newVal) -> validateFields());
        prenomField.textProperty().addListener((obs, oldVal, newVal) -> validateFields());
        preferencesField.textProperty().addListener((obs, oldVal, newVal) -> validateFields());

        saveButton.setOnAction(event -> handleSave());
        cancelButton.setOnAction(event -> handleCancel());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void validateFields() {
        if (emailField == null || passwordField == null || nomField == null || 
            prenomField == null || preferencesField == null) {
            return;
        }
        
        String email = emailField.getText();
        String password = passwordField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String preferences = preferencesField.getText();

        boolean isValid = email != null && !email.isEmpty() && 
                         password != null && !password.isEmpty() && 
                         nom != null && !nom.isEmpty() && 
                         prenom != null && !prenom.isEmpty() &&
                         preferences != null && !preferences.isEmpty();
                         
        saveButton.setDisable(!isValid);
    }

    public void setUser(Utilisateur user) {
        if (user == null) {
            AlertUtils.showError("Erreur", "Aucun utilisateur connecté");
            returnToMain();
            return;
        }

        this.currentUser = user;
        if (nomField != null) nomField.setText(user.getNom());
        if (prenomField != null) prenomField.setText(user.getPrenom());
        if (emailField != null) emailField.setText(user.getEmail());
        if (passwordField != null) passwordField.setText(user.getPassword());
        if (preferencesField != null) preferencesField.setText(user.getPreferencesSportives());
    }

    public void handleSave() {
        if (currentUser == null) {
            AlertUtils.showError("Erreur", "Aucun utilisateur connecté");
            returnToMain();
            return;
        }

        String email = emailField.getText();
        String password = passwordField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String preferences = preferencesField.getText();

        if (email == null || email.isEmpty() || 
            password == null || password.isEmpty() || 
            nom == null || nom.isEmpty() || 
            prenom == null || prenom.isEmpty() || 
            preferences == null || preferences.isEmpty()) {
            AlertUtils.showError("Erreur", "Veuillez remplir tous les champs");
            return;
        }

        // Vérifier le format de l'email
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            AlertUtils.showError("Erreur", "Format d'email invalide");
            return;
        }

        // Vérifier la longueur du mot de passe
        if (password.length() < 6) {
            AlertUtils.showError("Erreur", "Le mot de passe doit contenir au moins 6 caractères");
            return;
        }

        currentUser.setEmail(email);
        currentUser.setPassword(password);
        currentUser.setNom(nom);
        currentUser.setPrenom(prenom);
        currentUser.setPreferencesSportives(preferences);

        UtilisateurService utilisateurService = new UtilisateurService();
        utilisateurService.modifierUtilisateur(currentUser);
        AlertUtils.showSuccess("Succès", "Profil mis à jour avec succès");
        returnToMain();
    }

    private void handleCancel() {
        returnToMain();
    }

    @FXML
    private void handleBack() {
        handleCancel();
    }

    private void returnToMain() {
        if (stage == null) {
            // Si le stage n'est pas défini, essayer de le récupérer depuis le bouton
            if (cancelButton != null && cancelButton.getScene() != null) {
                stage = (Stage) cancelButton.getScene().getWindow();
            } else {
                // Si on ne peut toujours pas obtenir le stage, utiliser la fenêtre principale
                stage = (Stage) emailField.getScene().getWindow();
            }
        }

        if (currentUser != null) {
            NavigationUtils.navigateTo("/utilisateur/main.fxml", stage, currentUser);
        } else {
            NavigationUtils.navigateTo("/utilisateur/login.fxml", stage);
        }
    }
} 