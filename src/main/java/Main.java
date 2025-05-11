import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.DatabaseInitializer;
import controllers.utilisateur.LoginController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            DatabaseInitializer.initialize();
            

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/login.fxml"));
            Parent root = loader.load();


            LoginController loginController = loader.getController();
            loginController.setStage(primaryStage);


            Scene scene = new Scene(root, 900, 700);


            primaryStage.setTitle("Sportify");
            
            try {
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/imgs/logo.png")));
            } catch (Exception e) {
                System.out.println("Logo not found: " + e.getMessage());
            }

            primaryStage.setMaximized(true);
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(700);

            primaryStage.setScene(scene);
            
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error starting application: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
} 