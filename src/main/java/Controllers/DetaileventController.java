package controllers;

import Models.Evenement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetaileventController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label nomLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label lieuLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label placesLabel;
    @FXML
    private ImageView eventImageView;

    public void setEvent(Evenement event) {
        titleLabel.setText("Détails de l'Événement");
        nomLabel.setText(event.getNom());
        dateLabel.setText(event.getDate().toString());
        lieuLabel.setText(event.getLieu());
        typeLabel.setText(event.getType());
        placesLabel.setText(String.valueOf(event.getNbr_places()));

        // Charger l'image de l'événement
        if (event.getImagePath() != null && !event.getImagePath().isEmpty()) {
            File imgFile = new File(event.getImagePath());
            if (imgFile.exists()) {
                eventImageView.setImage(new Image(imgFile.toURI().toString()));
            } else {
                eventImageView.setImage(new Image(getClass().getResourceAsStream("/images/default-event.jpg")));
            }
        } else {
            eventImageView.setImage(new Image(getClass().getResourceAsStream("/images/default-event.jpg")));
        }
    }

    @FXML
    private void handleClose() {
        // Fermer la fenêtre
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }

    public void sendMail(String to, String subject, String text) {
        try {
            URL url = new URL("http://localhost:3001/send");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            String jsonInputString = String.format(
                "{\"to\":\"%s\", \"subject\":\"%s\", \"text\":\"%s\"}",
                to, subject, text
            );

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();
            if (code == 200) {
                System.out.println("Mail envoyé !");
            } else {
                System.out.println("Erreur lors de l'envoi du mail, code: " + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}