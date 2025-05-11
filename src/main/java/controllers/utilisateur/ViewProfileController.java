package controllers.utilisateur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import entities.Utilisateur;
import utils.AlertUtils;

import java.io.IOException;

public class ViewProfileController {
    @FXML
    private Label emailLabel;
    @FXML
    private Label nomLabel;
    @FXML
    private Label prenomLabel;
    @FXML
    private Button backButton;

    private Utilisateur currentUser;

    @FXML
    private void initialize() {
        backButton.setOnAction(event -> handleBack());
    }

    public void setUser(Utilisateur user) {
        this.currentUser = user;
        emailLabel.setText("Email: " + user.getEmail());
        nomLabel.setText("Nom: " + user.getNom());
        prenomLabel.setText("Prénom: " + user.getPrenom());
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/main.fxml"));
            Parent root = loader.load();
            
            MainController controller = loader.getController();
            controller.setUser(currentUser);
            
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            AlertUtils.showError("Erreur", "Impossible de retourner à la page principale");
        }
    }
} 