package Controllers;

import Models.Evenement;
import Services.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddeventController {

    @FXML
    private DatePicker txtdate;

    @FXML
    private TextField txtdescription;

    @FXML
    private TextField txtlieu;

    @FXML
    private TextField txtnbplaces;

    @FXML
    private TextField txtnom;

    @FXML
    private ComboBox<String> txttype;

    @FXML
    private ImageView eventImageView;

    @FXML
    private Button uploadImageBtn;

    private EvenementService evenementService;
    private File selectedImageFile;

    public void initialize() {
        evenementService = new EvenementService();
        // Initialiser les types d'événements possibles
        txttype.getItems().addAll("Course", "Marathon", "Tournoi", "Entraînement", "Autre");
    }

    @FXML
    private void handleImageUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File chosenFile = fileChooser.showOpenDialog(uploadImageBtn.getScene().getWindow());
        if (chosenFile != null) {
            try {
                // Dossier externe pour stocker les images
                String imagesDir = System.getProperty("user.dir") + File.separator + "images" + File.separator + "events";
                File dir = new File(imagesDir);
                if (!dir.exists()) dir.mkdirs();

                // Nom unique pour l'image
                String imageName = System.currentTimeMillis() + "_" + chosenFile.getName();
                File dest = new File(dir, imageName);

                // Copier l'image
                Files.copy(chosenFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Afficher l'image dans l'ImageView
                eventImageView.setImage(new Image(dest.toURI().toString()));

                // Stocker le chemin absolu pour la base
                selectedImageFile = dest; // On garde le bon chemin pour l'enregistrement
            } catch (IOException e) {
                showAlert("Erreur", "Impossible de sauvegarder l'image: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    void Ajouter(ActionEvent event) {
        try {
            // Vérifier que tous les champs sont remplis
            if (txtnom.getText().isEmpty() || txtlieu.getText().isEmpty() ||
                txtdate.getValue() == null || txttype.getValue() == null ||
                txtdescription.getText().isEmpty() || txtnbplaces.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs");
                return;
            }

            // Vérifier l'image
            if (selectedImageFile == null) {
                showAlert("Erreur", "Veuillez sélectionner une image pour l'événement", Alert.AlertType.ERROR);
                return;
            }

            // Créer un nouvel événement
            Evenement evenement = new Evenement();
            evenement.setNom(txtnom.getText());
            evenement.setLieu(txtlieu.getText());
            evenement.setDate(Date.valueOf(txtdate.getValue()));
            evenement.setType(txttype.getValue());
            evenement.setDescription(txtdescription.getText());
            evenement.setNbr_places(Integer.parseInt(txtnbplaces.getText()));
            evenement.setUtilisateur_id(1); // À remplacer par l'ID de l'utilisateur connecté
            evenement.setImagePath(selectedImageFile.getAbsolutePath());

            // Ajouter l'événement
            evenementService.create(evenement);

            // Envoi de mail
            sendMail(
                "destinataire@email.com",
                "Nouveau événement ajouté",
                "Bonjour, un nouvel événement a été ajouté : " + evenement.getNom()
            );

            // Afficher un message de succès
            showAlert("Succès", "L'événement a été ajouté avec succès");

            // Fermer la fenêtre d'ajout après succès
            Stage stage = (Stage) txtnom.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le nombre de places doit être un nombre valide");
        } catch (Exception e) {
            showAlert("Erreur", e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        txtnom.clear();
        txtlieu.clear();
        txtdate.setValue(null);
        txttype.setValue(null);
        txtdescription.clear();
        txtnbplaces.clear();
        eventImageView.setImage(null);
        selectedImageFile = null;
    }

    private void sendMail(String to, String subject, String text) {
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
