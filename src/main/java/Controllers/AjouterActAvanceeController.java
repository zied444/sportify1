package Controllers;

import Service.ActiviteSportiveAvanceeService;
import Models.ActiviteSportiveAvancee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class AjouterActAvanceeController {

    @FXML
    private TextField sportField;

    @FXML
    private TextField dureeField;

    @FXML
    private TextField poidsField;

    @FXML
    private TextField caloriesField;

    private ActiviteSportiveAvanceeService activiteService = new ActiviteSportiveAvanceeService();

    @FXML
    private void ajouterActAvancee(ActionEvent event) {
        String sport = sportField.getText();
        int duree = Integer.parseInt(dureeField.getText());
        double poids = Double.parseDouble(poidsField.getText());

        double caloriesBrulees = duree * 8 * 0.0175 * poids;
        caloriesField.setText(String.format("%.2f", caloriesBrulees));

        ActiviteSportiveAvancee activite = new ActiviteSportiveAvancee(sport, duree, poids, caloriesBrulees);
        activiteService.ajouterActiviteSportiveAvancee(activite);
    }

    // Méthode pour retourner au menu
    @FXML
    private void retourMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

