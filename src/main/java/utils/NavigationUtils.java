package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import entities.Utilisateur;
import controllers.utilisateur.EditProfileController;

import java.io.IOException;
import java.net.URL;

public class NavigationUtils {
    public static void navigateTo(String fxmlPath, Stage currentStage, Utilisateur utilisateur) {
        try {
            URL resource = NavigationUtils.class.getResource(fxmlPath);
            System.out.println("Chargement FXML depuis : " + fxmlPath + " -> " + resource);
            if (resource == null) {
                throw new IOException("Resource not found: " + fxmlPath);
            }
            
            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();
            
            // Set the utilisateur in the controller if it's a user-related view
            Object controller = loader.getController();
            if (controller instanceof controllers.utilisateur.MainController) {
                ((controllers.utilisateur.MainController) controller).setUser(utilisateur);
            } else if (controller instanceof controllers.utilisateur.ViewProfileController) {
                ((controllers.utilisateur.ViewProfileController) controller).setUser(utilisateur);
            } else if (controller instanceof controllers.utilisateur.EditProfileController) {
                if (utilisateur == null) {
                    throw new IllegalArgumentException("L'utilisateur ne peut pas être null pour la vue d'édition de profil");
                }
                EditProfileController editController = (EditProfileController) controller;
                editController.setUser(utilisateur);
                editController.setStage(currentStage);
            }
            
            currentStage.setScene(new Scene(root));
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showError("Erreur", "Impossible de charger la vue: " + fxmlPath + "\n" + e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            AlertUtils.showError("Erreur", e.getMessage());
        }
    }

    public static void navigateTo(String fxmlPath, Stage currentStage) {
        navigateTo(fxmlPath, currentStage, null);
    }
} 