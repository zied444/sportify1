package Controllers;

import Models.Evenement;
import Services.EvenementService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.sql.Date;
import java.time.LocalDate;

public class ModifyeventController {

    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtlieu;
    @FXML
    private DatePicker txtdate;
    @FXML
    private TextField txtdescription;
    @FXML
    private ComboBox<String> txttype;
    @FXML
    private TextField txtnbplaces;

    private EvenementService evenementService;
    private Evenement evenement; // Stocker l'événement à modifier

    @FXML
    private void initialize() {
        evenementService = new EvenementService();
        // Remplir les types d'événement (exemple)
        txttype.getItems().addAll("Course", "Marathon", "Tournoi", "Entraînement", "Autre");
    }

    // Méthode pour pré-remplir les champs avec les données de l'événement
    public void setEventData(Evenement evenement) {
        this.evenement = evenement;
        txtnom.setText(evenement.getNom());
        txtlieu.setText(evenement.getLieu());
        txtdate.setValue(evenement.getDate() != null ? evenement.getDate().toLocalDate() : null);
        txtdescription.setText(evenement.getDescription());
        txttype.setValue(evenement.getType());
        txtnbplaces.setText(String.valueOf(evenement.getNbr_places()));
    }

    @FXML
    private void Modifier() {
        // Récupérer les valeurs modifiées
        String nom = txtnom.getText();
        String lieu = txtlieu.getText();
        LocalDate date = txtdate.getValue();
        String description = txtdescription.getText();
        String type = txttype.getValue();
        String nbPlacesStr = txtnbplaces.getText();

        // Validation simple
        if (nom.isEmpty() || lieu.isEmpty() || date == null || description.isEmpty() || type == null || nbPlacesStr.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }

        int nbPlaces;
        try {
            nbPlaces = Integer.parseInt(nbPlacesStr);
            if (nbPlaces < 0) {
                throw new NumberFormatException("Le nombre de places doit être positif.");
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR, "Le nombre de places doit être un nombre entier positif !");
            alert.showAndWait();
            return;
        }

        try {
            // Mettre à jour l'objet Evenement
            evenement.setNom(nom);
            evenement.setLieu(lieu);
            evenement.setDate(Date.valueOf(date));
            evenement.setDescription(description);
            evenement.setType(type);
            evenement.setNbr_places(nbPlaces);

            // Appeler le service pour mettre à jour l'événement
            evenementService.update(evenement);

            // Afficher un message de succès
            Alert alert = new Alert(AlertType.INFORMATION, "Événement modifié avec succès !");
            alert.showAndWait();

            // Fermer la fenêtre
            Stage stage = (Stage) txtnom.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, "Erreur lors de la modification : " + e.getMessage());
            alert.showAndWait();
        }
    }
}