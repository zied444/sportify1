package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Models.ActiviteSportive;
import Service.ActiviteSportiveService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ActiviteSportiveController {

    @FXML private TextField sportField;
    @FXML private TextField dureeField;
    @FXML private DatePicker datePicker;
    @FXML private TextField prixField;
    @FXML private TextField utilisateurIdField;
    @FXML private TableView<ActiviteSportive> tableView;
    @FXML private TableColumn<ActiviteSportive, Integer> colId;
    @FXML private TableColumn<ActiviteSportive, String> colSport;
    @FXML private TableColumn<ActiviteSportive, Integer> colDuree;
    @FXML private TableColumn<ActiviteSportive, Date> colDate;
    @FXML private TableColumn<ActiviteSportive, Double> colPrix;
    @FXML private TableColumn<ActiviteSportive, Integer> colUtilisateurId;

    // Création du service pour les opérations sur la base de données
    private final ActiviteSportiveService service = new ActiviteSportiveService();

    @FXML
    private void initialize() {
        // Associe les colonnes de la table avec les propriétés de l'objet ActiviteSportive
        colId.setCellValueFactory(new PropertyValueFactory<>("idActivite"));  // id_activite dans la base de données
        colSport.setCellValueFactory(new PropertyValueFactory<>("sport"));  // sport
        colDuree.setCellValueFactory(new PropertyValueFactory<>("duree"));  // duree
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateActivite"));  // date_activite
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));  // prix_par_activite
        colUtilisateurId.setCellValueFactory(new PropertyValueFactory<>("utilisateurId"));  // utilisateur_id
        updateTable();  // Met à jour la table après l'initialisation
    }

    @FXML
    private void ajouter() {
        try {
            // Crée une nouvelle instance d'ActiviteSportive à partir des champs de texte
            ActiviteSportive activite = new ActiviteSportive(
                    0,  // L'ID est auto-généré, donc on passe 0 ici
                    sportField.getText(),
                    Integer.parseInt(dureeField.getText()),
                    Date.valueOf(datePicker.getValue()),
                    Double.parseDouble(prixField.getText()),
                    Integer.parseInt(utilisateurIdField.getText())
            );

            // Appel du service pour créer la nouvelle activité sportive
            service.Create(activite);

            // Met à jour la table après l'ajout
            updateTable();

        } catch (Exception e) {
            // Gestion des erreurs
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimer() {
        ActiviteSportive activite = tableView.getSelectionModel().getSelectedItem();
        if (activite != null) {
            try {
                // Appel du service pour supprimer l'activité sélectionnée
                service.delete(activite);
                updateTable();
            } catch (SQLException e) {
                // Gestion des erreurs
                e.printStackTrace();
            }
        }
    }

    private void updateTable() {
        try {
            // Récupère la liste des activités sportives depuis la base de données
            List<ActiviteSportive> list = service.DisplayAll();
            // Met à jour la vue de la table
            tableView.getItems().setAll(list);
        } catch (SQLException e) {
            // Gestion des erreurs
            e.printStackTrace();
        }
    }
}

