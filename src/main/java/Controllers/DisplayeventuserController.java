package Controllers;

import Models.Evenement;
import Services.EvenementService;
import Services.ReservationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class DisplayeventuserController {

    @FXML
    private VBox eventsContainer;

    private final EvenementService evenementService = new EvenementService();
    private final ReservationService reservationService = new ReservationService();

    private final int utilisateurId = 1;

    @FXML
    public void initialize() {
        refreshEventList();
        addMesReservationsButton();
    }

    private void refreshEventList() {
        try {
            eventsContainer.getChildren().clear();
            List<Evenement> evenements = evenementService.display();
            for (Evenement e : evenements) {
                HBox card = createEventCard(e);
                eventsContainer.getChildren().add(card);
            }
        } catch (Exception ex) {
            showAlert("Erreur", "Impossible de charger les événements : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private HBox createEventCard(Evenement event) {
        HBox card = new HBox();
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 15;");
        card.setSpacing(15);
        card.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.2)));
        card.setPrefWidth(760);

        ImageView imageView = new ImageView();
        imageView.setFitHeight(120);
        imageView.setFitWidth(120);
        imageView.setPreserveRatio(true);

        if (event.getImagePath() != null && !event.getImagePath().isEmpty()) {
            File imgFile = new File(event.getImagePath());
            if (imgFile.exists()) {
                imageView.setImage(new Image(imgFile.toURI().toString()));
            } else {
                imageView.setImage(new Image(getClass().getResourceAsStream("/images/default-event.jpg")));
            }
        } else {
            imageView.setImage(new Image(getClass().getResourceAsStream("/images/default-event.jpg")));
        }

        VBox infoContainer = new VBox();
        infoContainer.setSpacing(8);
        infoContainer.setAlignment(Pos.CENTER);
        infoContainer.setPrefWidth(500);

        Label titleLabel = new Label(event.getNom());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #232946; -fx-alignment: center;");
        titleLabel.setPrefWidth(500);
        titleLabel.setAlignment(Pos.CENTER);

        Label dateLabel = new Label("Date: " + event.getDate());
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #232946; -fx-alignment: center;");
        dateLabel.setPrefWidth(500);
        dateLabel.setAlignment(Pos.CENTER);

        Label locationLabel = new Label("Lieu: " + event.getLieu());
        locationLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #232946; -fx-alignment: center;");
        locationLabel.setPrefWidth(500);
        locationLabel.setAlignment(Pos.CENTER);

        Label typeLabel = new Label("Type: " + event.getType());
        typeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #232946; -fx-alignment: center;");
        typeLabel.setPrefWidth(500);
        typeLabel.setAlignment(Pos.CENTER);

        Label placesLabel = new Label("Places disponibles: " + event.getNbr_places());
        placesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #232946; -fx-alignment: center;");
        placesLabel.setPrefWidth(500);
        placesLabel.setAlignment(Pos.CENTER);

        boolean hasReserved = false;
        try {
            hasReserved = reservationService.hasReservation(utilisateurId, event.getId());
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la vérification des réservations : " + e.getMessage());
            e.printStackTrace();
        }

        TextField placesField = new TextField();
        placesField.setPromptText("Nombre de places");
        placesField.setPrefWidth(150);
        placesField.setDisable(hasReserved);

        Button reserverButton = new Button("Réserver");
        reserverButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 8 15;");
        reserverButton.setDisable(hasReserved);

        HBox reservationBox = new HBox(10, placesField, reserverButton);
        reservationBox.setAlignment(Pos.CENTER);

        infoContainer.getChildren().addAll(titleLabel, dateLabel, locationLabel, typeLabel, placesLabel, reservationBox);
        card.getChildren().addAll(imageView, infoContainer);

        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10; -fx-padding: 15;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 15;"));

        reserverButton.setOnAction(e -> handleReserverClick(event, placesField));

        return card;
    }

    private void handleReserverClick(Evenement event, TextField placesField) {
        try {
            String placesText = placesField.getText();
            if (placesText.isEmpty()) {
                showAlert("Erreur", "Veuillez entrer le nombre de places à réserver.");
                return;
            }

            int nbPlaces;
            try {
                nbPlaces = Integer.parseInt(placesText);
                if (nbPlaces <= 0) {
                    throw new NumberFormatException("Le nombre de places doit être positif.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Erreur", "Le nombre de places doit être un nombre entier positif.");
                return;
            }

            reservationService.reserverEvenement(utilisateurId, event.getId(), nbPlaces);
            showAlert("Succès", "Réservation effectuée avec succès pour l'événement \"" + event.getNom() + "\" !");
            refreshEventList();
        } catch (Exception ex) {
            showAlert("Erreur", "Impossible de réserver l'événement : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void addMesReservationsButton() {
        Button mesReservationsButton = new Button("Mes Réservations");
        mesReservationsButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 8 15;");
        mesReservationsButton.setOnAction(e -> openReservationsWindow());
        eventsContainer.getChildren().add(0, mesReservationsButton);
    }

    private void openReservationsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayReservations.fxml"));
            javafx.scene.Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Mes Réservations");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            showAlert("Erreur", "Impossible de charger la fenêtre des réservations : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}