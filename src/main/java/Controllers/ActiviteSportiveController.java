package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.ActiviteSportive;
import service.ActiviteSportiveService;


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





    private final ActiviteSportiveService service = new ActiviteSportiveService();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSport.setCellValueFactory(new PropertyValueFactory<>("sport"));
        colDuree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateActivite"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colUtilisateurId.setCellValueFactory(new PropertyValueFactory<>("utilisateurId"));
        updateTable();
    }

    @FXML
    private void ajouter() {
        try {
            ActiviteSportive activite = new ActiviteSportive(
                    0,
                    sportField.getText(),
                    Integer.parseInt(dureeField.getText()),
                    Date.valueOf(datePicker.getValue()),
                    Double.parseDouble(prixField.getText()),
                    Integer.parseInt(utilisateurIdField.getText())
            );
            service.Create(activite);
            updateTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimer() {
        ActiviteSportive activite = tableView.getSelectionModel().getSelectedItem();
        if (activite != null) {
            try {
                service.delete(activite);
                updateTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateTable() {
        try {
            List<ActiviteSportive> list = service.DisplayAll();
            tableView.getItems().setAll(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


