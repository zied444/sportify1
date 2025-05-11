package controllers.utilisateur;

import constants.Roles;
import entities.Utilisateur;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import interfaces.IService;
import service.UtilisateurService;
import utils.AlertUtils;
import utils.EmailUtils;
import utils.NavigationUtils;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField preferencesField;
    @FXML
    private ChoiceBox<String> roleChoiceBox;
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;

    private IService<Utilisateur> utilisateurService;
    private Stage stage;

    public RegisterController() {
        this.utilisateurService = new UtilisateurService();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        try {
            // Initialiser le ChoiceBox avec les rôles disponibles
            roleChoiceBox.setItems(FXCollections.observableArrayList(Roles.ATHLETE, Roles.COACH , Roles.ADMIN));
            roleChoiceBox.setValue(Roles.ATHLETE);

            // Ajouter les gestionnaires d'événements pour les boutons
            if (registerButton != null) {
                registerButton.setOnAction(event -> handleRegister());
            }
            if (cancelButton != null) {
                cancelButton.setOnAction(event -> handleBack());
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.showError("Erreur d'initialisation", "Une erreur est survenue lors de l'initialisation du formulaire : " + e.getMessage());
        }
    }

    @FXML
    private void handleRegister() {
        try {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String preferences = preferencesField.getText();
            String role = roleChoiceBox.getValue();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || preferences.isEmpty()) {
                AlertUtils.showError("Erreur", "Veuillez remplir tous les champs");
                return;
            }

            // Créer l'utilisateur
            Utilisateur user = new Utilisateur();
            user.setNom(nom);
            user.setPrenom(prenom);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(role);
            user.setPreferencesSportives(preferences);

            // Enregistrer l'utilisateur dans la base de données
            utilisateurService.ajouterUtilisateur(user);
            System.out.println("Utilisateur enregistré avec succès dans la base de données");

            // Envoyer l'email de confirmation
            try {
                System.out.println("Tentative d'envoi de l'email de confirmation...");
                EmailUtils.sendConfirmationEmail(email, "Bienvenue sur Sportify !");
                AlertUtils.showInfo("Succès", "Inscription réussie ! Un email de confirmation a été envoyé à votre adresse.");
            } catch (Exception e) {
                System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
                e.printStackTrace();
                // On continue même si l'email échoue
                AlertUtils.showInfo("Succès", "Inscription réussie ! (Note : L'envoi de l'email a échoué)");
            }

            // Retourner à la page de connexion
            handleBack();

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.showError("Erreur", "Une erreur est survenue lors de l'inscription : " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        try {
            // Utiliser le stage stocké s'il est disponible
            if (stage != null) {
                NavigationUtils.navigateTo("/utilisateur/login.fxml", stage);
                return;
            }

            // Sinon, essayer de récupérer le stage depuis le bouton
            if (cancelButton != null && cancelButton.getScene() != null) {
                Stage currentStage = (Stage) cancelButton.getScene().getWindow();
                NavigationUtils.navigateTo("/utilisateur/login.fxml", currentStage);
                return;
            }

            // En dernier recours, essayer de récupérer le stage depuis n'importe quel nœud
            if (emailField != null && emailField.getScene() != null) {
                Stage currentStage = (Stage) emailField.getScene().getWindow();
                NavigationUtils.navigateTo("/utilisateur/login.fxml", currentStage);
                return;
            }

            throw new IllegalStateException("Impossible de trouver le stage pour la navigation");
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.showError("Erreur", "Impossible de retourner à la page de connexion : " + e.getMessage());
        }
    }
} 