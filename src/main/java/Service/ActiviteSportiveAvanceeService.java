package Service;

import Models.ActiviteSportiveAvancee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;

public class ActiviteSportiveAvanceeService {

    // Méthode pour ajouter une activité sportive avancée
    public void ajouterActiviteSportiveAvancee(ActiviteSportiveAvancee activite) {
        String url = "jdbc:mysql://localhost:3306/sportify"; // Base de données
        String user = "root"; // Utilisateur
        String password = ""; // Mot de passe

        // SQL pour insérer une nouvelle activité
        String query = "INSERT INTO activite_sportive_avancee (sport, duree, poids, calories_brulees) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, activite.getSport());
            statement.setInt(2, activite.getDuree());
            statement.setDouble(3, activite.getPoids());
            statement.setDouble(4, activite.getCaloriesBrulees());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Activité avancée ajoutée avec succès !");
            } else {
                System.out.println("Erreur lors de l'ajout de l'activité.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
