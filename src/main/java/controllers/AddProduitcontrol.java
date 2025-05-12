package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddProduitcontrol implements Initializable {

    @FXML private ComboBox<Integer> cbCoachId;
    @FXML private ComboBox<Integer> cbUtilisateurid;
    @FXML private ComboBox<String> cbTypeActivite;
    @FXML private DatePicker dpDateCreation;
    @FXML private TextArea taContenu;
    @FXML private TextField txtnom;

    private final String DB_URL = "jdbc:mysql://localhost:3306/ton_schema"; // 🔁 À adapter
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "ton_mot_de_passe"; // 🔁 À adapter

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbTypeActivite.setItems(FXCollections.observableArrayList("Musculation", "Cardio", "HIIT", "Yoga"));
        loadUtilisateurs();
        loadCoachs();
    }

    private void loadUtilisateurs() {
        ObservableList<Integer> utilisateurs = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id FROM utilisateur")) {

            while (rs.next()) {
                utilisateurs.add(rs.getInt("id"));
            }

            cbUtilisateurid.setItems(utilisateurs);

        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des utilisateurs :");
            e.printStackTrace();
        }
    }

    private void loadCoachs() {
        ObservableList<Integer> coachs = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM utilisateur WHERE role = 'coach'");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                coachs.add(rs.getInt("id"));
            }

            cbCoachId.setItems(coachs);

        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des coachs :");
            e.printStackTrace();
        }
    }

    @FXML
    void Ajouter(ActionEvent event) {
        String nom = txtnom.getText();
        LocalDate dateCreation = dpDateCreation.getValue();
        String typeActivite = cbTypeActivite.getValue();
        String contenu = taContenu.getText();
        Integer utilisateurId = cbUtilisateurid.getValue();
        Integer coachId = cbCoachId.getValue();

        //  Vérification de champs obligatoires
        if (nom == null || nom.isEmpty() || dateCreation == null || typeActivite == null ||
                contenu == null || contenu.isEmpty() || utilisateurId == null || coachId == null) {
            System.err.println("⚠Veuillez remplir tous les champs !");
            return;
        }

        String sql = "INSERT INTO plan_entrainement (nom, date_creation, type_activite, contenu, utilisateur_id, coach_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nom);
            stmt.setDate(2, java.sql.Date.valueOf(dateCreation));
            stmt.setString(3, typeActivite);
            stmt.setString(4, contenu);
            stmt.setInt(5, utilisateurId);
            stmt.setInt(6, coachId);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" Plan d'entraînement ajouté avec succès !");
            }

        } catch (SQLException e) {
            System.err.println(" Erreur lors de l'ajout du plan :");
            e.printStackTrace();
        }
    }
}



