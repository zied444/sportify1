package Controllers;

import Service.VisualisationGraphiqueService;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class VisualisationGraphiqueController {

    @FXML
    private LineChart<Number, Number> lineChart;

    // Instance du service pour obtenir les données
    private VisualisationGraphiqueService visualisationService = new VisualisationGraphiqueService();

    public void initialize() {
        // Initialisation du graphique
        visualisationService.getDataForChart().forEach(dataPoint -> {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(dataPoint.getSport());  // Nom de la série (sport)

            // Ajouter les points à la série
            dataPoint.getPoints().forEach(point -> {
                try {
                    double xValue = Double.parseDouble(point.getKey());  // Point X (ex. temps ou nombre d'activités)
                    double yValue = Double.parseDouble(point.getValue());  // Point Y (ex. calories, distance)
                    series.getData().add(new XYChart.Data<>(xValue, yValue));  // Ajouter le point à la série
                } catch (NumberFormatException e) {
                    System.out.println("Erreur de conversion des données : " + point);
                }
            });

            lineChart.getData().add(series);  // Ajouter la série au graphique
        });
    }

    @FXML
    public void retourMenu(ActionEvent event) {
        try {
            // Charger le fichier FXML du menu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
            Parent root = loader.load();

            // Créer une scène avec le contenu du menu
            Scene scene = new Scene(root);

            // Récupérer la fenêtre actuelle et la fermer
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
