package controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import entities.Utilisateur;
import service.UtilisateurService;

import java.io.IOException;
import java.util.List;

public class AdminDashboardController {
    @FXML private Label welcomeLabel;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> roleFilter;
    @FXML private TableView<Utilisateur> userTable;
    @FXML private TableColumn<Utilisateur, Integer> colId;
    @FXML private TableColumn<Utilisateur, String> colNom;
    @FXML private TableColumn<Utilisateur, String> colPrenom;
    @FXML private TableColumn<Utilisateur, String> colEmail;
    @FXML private TableColumn<Utilisateur, String> colRole;
    @FXML private TableColumn<Utilisateur, Void> colActions;

    private UtilisateurService userService;
    private Utilisateur currentAdmin;

    @FXML
    public void initialize() {
        userService = new UtilisateurService();
        
        // Initialize role filter
        roleFilter.setItems(FXCollections.observableArrayList("Tous", "Athlète", "Coach"));
        roleFilter.setValue("Tous");

        // Initialize table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Add delete button to actions column
        colActions.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");
            {
                deleteButton.getStyleClass().add("danger-button");
                deleteButton.setOnAction(event -> {
                    Utilisateur user = getTableView().getItems().get(getIndex());
                    handleDeleteUser(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });

        // Load initial data
        loadUsers();
    }

    public void setCurrentAdmin(Utilisateur admin) {
        this.currentAdmin = admin;
        welcomeLabel.setText("Hello, " + admin.getNom() + " " + admin.getPrenom());
    }

    private void loadUsers() {
        List<Utilisateur> users = userService.listerUtilisateurs();
        userTable.setItems(FXCollections.observableArrayList(users));
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText();
        String selectedRole = roleFilter.getValue();
        
        List<Utilisateur> filteredUsers;
        if (selectedRole.equals("Tous")) {
            filteredUsers = userService.rechercherParNom(searchTerm);
        } else {
            filteredUsers = userService.filtrerParRole(selectedRole);
        }
        
        userTable.setItems(FXCollections.observableArrayList(filteredUsers));
    }

    private void handleDeleteUser(Utilisateur user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer l'utilisateur");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer " + user.getNom() + " " + user.getPrenom() + " ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                userService.supprimerUtilisateur(user.getId());
                loadUsers();
            }
        });
    }

    @FXML
    private void handleShowUsers() {
        loadUsers();
    }

    @FXML
    private void handleEditProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/editProfile.fxml"));
            Parent root = loader.load();
            
            EditProfileController editController = loader.getController();
            editController.setCurrentAdmin(currentAdmin);
            
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de modification");
            alert.setContentText("Impossible d'ouvrir la page de modification : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteAccount() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer le compte");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer votre compte ? Cette action est irréversible.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                userService.supprimerUtilisateur(currentAdmin.getId());
                handleLogout();
            }
        });
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de déconnexion");
            alert.setContentText("Impossible de charger la page de connexion : " + e.getMessage());
            alert.showAndWait();
        }
    }
} 