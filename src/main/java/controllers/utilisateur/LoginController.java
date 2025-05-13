package controllers.utilisateur;

import controllers.admin.AdminDashboardController;
import entities.Utilisateur;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UtilisateurService;
import utils.AlertUtils;
import utils.PasswordHasher;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import javax.mail.*;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Hyperlink forgotPasswordLink; // Lien pour restaurer le mot de passe

    private UtilisateurService utilisateurService;
    private Stage stage;

    public LoginController() {
        this.utilisateurService = new UtilisateurService();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        loginButton.setDisable(true);
        emailField.textProperty().addListener((obs, oldVal, newVal) -> validateFields());
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> validateFields());

        loginButton.setOnAction(event -> handleLogin());
        registerButton.setOnAction(event -> handleRegister());
        forgotPasswordLink.setOnAction(event -> handleForgotPassword()); // Gestion de l'événement sur le lien
    }

    private void validateFields() {
        boolean isValid = !emailField.getText().isEmpty() && !passwordField.getText().isEmpty();
        loginButton.setDisable(!isValid);
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            Utilisateur user = utilisateurService.connecter(email, password);
            if (user != null) {
                System.out.println("Authentification réussie pour : " + user.getEmail());

                // Vérifier le rôle et rediriger
                if ("Admin".equalsIgnoreCase(user.getRole())) {
                    navigateToAdminDashboard(user);
                } else {
                    navigateToUserDashboard(user);
                }
            } else {
                AlertUtils.showError("Erreur", "Email ou mot de passe incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.showError("Erreur", "Une erreur est survenue lors de la connexion : " + e.getMessage());
        }
    }

    private void navigateToAdminDashboard(Utilisateur user) throws IOException {
        URL resource = getClass().getResource("/admin/adminDashboard.fxml");
        FXMLLoader loader = new FXMLLoader(resource);

        Parent root = loader.load();
        AdminDashboardController controller = loader.getController();
        controller.setCurrentAdmin(user);

        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private void navigateToUserDashboard(Utilisateur user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/main.fxml"));

        Parent root = loader.load();
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/Register.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) registerButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.showError("Erreur", "Impossible de charger la vue d'inscription : " + e.getMessage());
        }
    }

    /**
     * Méthode pour gérer la restauration de mot de passe.
     */
    @FXML
    private void handleForgotPassword() {
        String email = emailField.getText();

        if (email.isBlank()) {
            AlertUtils.showError("Erreur", "Veuillez saisir une adresse email.");
            return;
        }

        try {
            // Vérifier si l'utilisateur existe
            Utilisateur user = utilisateurService.findByEmail(email);
            if (user == null) {
                AlertUtils.showError("Erreur", "L'adresse email est introuvable.");
                return;
            }

            // Générer un mot de passe temporaire
            String temporaryPassword = generateRandomPassword();

            // Mettre à jour le mot de passe de l'utilisateur dans la base de données
            String hashedPassword = PasswordHasher.hashPassword(temporaryPassword);
            utilisateurService.updatePassword(user.getId(), hashedPassword);

            // Envoyer un email avec le mot de passe temporaire
            sendEmail(email, temporaryPassword);

            AlertUtils.showSuccess("Succès", "Un nouveau mot de passe temporaire a été envoyé à votre boîte mail.");
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.showError("Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    /**
     * Génère un mot de passe temporaire aléatoire.
     */
    private String generateRandomPassword() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            password.append(characters.charAt(randomIndex));
        }
        return password.toString();
    }

    /**
     * Envoie un email contenant le mot de passe temporaire.
     */
    private void sendEmail(String recipientEmail, String newPassword) {
        final String senderEmail = "zied.wakkel00@gmail.com";
        final String senderPassword = "sknd dxxr qzmb tzqq";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Réinitialisation de votre mot de passe");
            message.setText("Bonjour,\n\nVotre nouveau mot de passe est : " + newPassword +
                    "\n\nVeuillez vous connecter et le changer dès que possible.\n\nCordialement,\nSportify Support");

            Transport.send(message);

            System.out.println("Email envoyé à : " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
