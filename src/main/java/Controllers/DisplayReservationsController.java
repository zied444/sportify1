package controllers;

import Models.Reservation;
import Services.ReservationService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.geometry.Pos;
import java.util.List;

public class DisplayReservationsController {

    @FXML
    private VBox reservationsContainer;

    private final ReservationService reservationService = new ReservationService();

    // Simuler l'ID de l'utilisateur connecté (à remplacer par une vraie authentification)
    private final int utilisateurId = 1;

    @FXML
    public void initialize() {
        refreshReservationList();
    }

    private void refreshReservationList() {
        try {
            reservationsContainer.getChildren().clear();
            List<Reservation> reservations = reservationService.display();
            for (Reservation r : reservations) {
                if (r.getUtilisateur_id() == utilisateurId) {
                    HBox card = createReservationCard(r);
                    reservationsContainer.getChildren().add(card);
                }
            }
        } catch (Exception ex) {
            showAlert("Erreur", "Impossible de charger les réservations : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private HBox createReservationCard(Reservation reservation) {
        HBox card = new HBox();
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 15;");
        card.setSpacing(15);
        card.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.2)));
        card.setPrefWidth(760);

        VBox infoContainer = new VBox();
        infoContainer.setSpacing(8);
        infoContainer.setAlignment(Pos.CENTER);
        infoContainer.setPrefWidth(500);

        Label titleLabel = new Label("Réservation pour l'événement ID: " + reservation.getEvenement_id());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #232946; -fx-alignment: center;");
        titleLabel.setPrefWidth(500);
        titleLabel.setAlignment(Pos.CENTER);

        Label dateLabel = new Label("Date de réservation: " + reservation.getDate_res());
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #232946; -fx-alignment: center;");
        dateLabel.setPrefWidth(500);
        dateLabel.setAlignment(Pos.CENTER);

        Label statutLabel = new Label("Statut: " + reservation.getStatut());
        statutLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #232946; -fx-alignment: center;");
        statutLabel.setPrefWidth(500);
        statutLabel.setAlignment(Pos.CENTER);

        Label placesLabel = new Label("Nombre de places réservées: " + reservation.getNb_places_res());
        placesLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #232946; -fx-alignment: center;");
        placesLabel.setPrefWidth(500);
        placesLabel.setAlignment(Pos.CENTER);

        Button annulerButton = new Button("Annuler");
        annulerButton.setStyle("-fx-background-color: #e84545; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 8 15;");

        HBox buttonBox = new HBox(10, annulerButton);
        buttonBox.setAlignment(Pos.CENTER);

        infoContainer.getChildren().addAll(titleLabel, dateLabel, statutLabel, placesLabel, buttonBox);
        card.getChildren().addAll(infoContainer);

        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10; -fx-padding: 15;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 15;"));

        annulerButton.setOnAction(e -> handleAnnulerClick(reservation));

        return card;
    }

    private void handleAnnulerClick(Reservation reservation) {
        try {
            reservationService.annulerReservation(utilisateurId, reservation.getEvenement_id());
            showAlert("Succès", "Réservation annulée avec succès pour l'événement ID: " + reservation.getEvenement_id());
            refreshReservationList();
        } catch (Exception ex) {
            showAlert("Erreur", "Impossible d'annuler la réservation : " + ex.getMessage());
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