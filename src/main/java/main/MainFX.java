package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceDialog;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Créer une liste de choix pour le rôle
            List<String> choices = Arrays.asList("Admin", "User");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("User", choices);
            dialog.setTitle("Sélection du rôle");
            dialog.setHeaderText("Choisissez votre rôle pour cette session");
            dialog.setContentText("Rôle :");

            // Afficher le dialogue et attendre la sélection
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String role = result.get();
                String fxmlFile;
                String title;
                if (role.equals("Admin")) {
                    fxmlFile = "/Displayevent.fxml";
                    title = "Liste des Événements (Admin)";
                } else {
                    fxmlFile = "/Displayeventuser.fxml";
                    title = "Liste des Événements (Utilisateur)";
                }

                // Charger le fichier FXML correspondant
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                // Créer la scène
                Scene scene = new Scene(root);

                // Configurer la fenêtre principale
                primaryStage.setTitle(title);
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);

                // Afficher la fenêtre
                primaryStage.show();
            } else {
                // Si l'utilisateur ferme le dialogue sans choisir, quitter l'application
                System.exit(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        // Fermer la connexion à la base de données à la fermeture de l'application
        utils.mydb.getInstance().close();
    }
}