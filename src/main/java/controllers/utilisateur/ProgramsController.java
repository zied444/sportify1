package controllers.utilisateur;

import constants.Programme;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import entities.Utilisateur;
import utils.AlertUtils;

import java.io.IOException;

public class ProgramsController {
    @FXML
    private VBox programsContainer;

    @FXML
    private Button backButton;

    @FXML
    private Button subscribeButton;

    @FXML
    private TableView<Programme> programTable;

    @FXML
    private TableColumn<Programme, String> colProgramme;

    @FXML
    private TableColumn<Programme, String> colDescription;

    @FXML
    private TableColumn<Programme, String> colNiveau;

    private Utilisateur currentUser;

    @FXML
    public void initialize() {
        colProgramme.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNiveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));

        // Set image in description column (still named "description")
        colDescription.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));

        colDescription.setCellFactory(column -> new TableCell<Programme, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null) {
                    setGraphic(null);
                } else {
                    try {
                        Image image = new Image(imagePath, 60, 60, true, true);
                        imageView.setImage(image);
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setText("Invalid image");
                    }
                }
            }
        });

        loadPrograms();
    }

    public void setUser(Utilisateur user) {
        this.currentUser = user;
        if (user != null) {
            System.out.println("ProgramsController: User set successfully - " + user.getEmail());
        } else {
            System.out.println("ProgramsController: Warning - User set to null");
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/main.fxml"));
            Parent root = loader.load();

            MainController mainController = loader.getController();
            if (currentUser != null) {
                System.out.println("ProgramsController: Returning to main with user - " + currentUser.getEmail());
                mainController.setUser(currentUser);
            } else {
                System.out.println("ProgramsController: Warning - currentUser is null when returning to main");
            }

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            AlertUtils.showError("Erreur", "Impossible de charger la vue principale");
        }
    }

    private void loadPrograms() {
        ObservableList<Programme> programmes = FXCollections.observableArrayList(
                new Programme("Programme Débutant", "file:../imgs/debutant.jpg", "Débutant"),
                new Programme("Programme Intermédiaire", "file:images/intermediaire.png", "Intermédiaire"),
                new Programme("Programme Avancé", "file:images/avance.png", "Avancé"),
                new Programme("Programme Perte de Poids", "file:images/perte_poids.png", "Tous niveaux"),
                new Programme("Programme Prise de Masse", "file:images/prise_masse.png", "Avancé")
        );

        programTable.setItems(programmes);
    }

    @FXML
    public void onBackHover() {
        backButton.setStyle("-fx-background-color: #3367d6; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 6; -fx-padding: 10 20; -fx-cursor: hand;");
    }

    @FXML
    public void onBackExit() {
        backButton.setStyle("-fx-background-color: #4285f4; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 6; -fx-padding: 10 20; -fx-cursor: hand;");
    }
}