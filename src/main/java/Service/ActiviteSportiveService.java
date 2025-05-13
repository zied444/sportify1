package Service;

import Models.ActiviteSportive;
import Utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActiviteSportiveService {

    private Connection connection;

    // Connexion via la classe MyDB (singleton)
    public ActiviteSportiveService() {
        this.connection = MyDB.getInstance().getConnection();
    }

    // Ajouter une activité
    public void Create(ActiviteSportive activite) {
        String query = "INSERT INTO activite_sportive (sport, duree, date_activite, prix_par_activite, utilisateur_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, activite.getSport());
            stmt.setInt(2, activite.getDuree());
            stmt.setDate(3, activite.getDate_activite());
            stmt.setDouble(4, activite.getPrix_par_activite());
            stmt.setInt(5, activite.getUtilisateur_id());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mettre à jour une activité existante
    public void updateActivite(ActiviteSportive activite) {
        String query = "UPDATE activite_sportive SET sport = ?, duree = ?, date_activite = ?, prix_par_activite = ?, utilisateur_id = ? WHERE id_activite = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, activite.getSport());
            stmt.setInt(2, activite.getDuree());
            stmt.setDate(3, activite.getDate_activite());
            stmt.setDouble(4, activite.getPrix_par_activite());
            stmt.setInt(5, activite.getUtilisateur_id());
            stmt.setInt(6, activite.getId_activite());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Récupérer toutes les activités
    public List<ActiviteSportive> getAllActivites() throws SQLException {
        List<ActiviteSportive> activites = new ArrayList<>();
        String query = "SELECT * FROM activite_sportive";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id_activite");
                String sport = rs.getString("sport");
                int duree = rs.getInt("duree");
                Date date = rs.getDate("date_activite");
                double prix = rs.getDouble("prix_par_activite");
                int utilisateurId = rs.getInt("utilisateur_id");

                ActiviteSportive activite = new ActiviteSportive(id, sport, duree, date, prix, utilisateurId);
                activites.add(activite);
            }
        } catch (SQLException e) {
            // Propager l'exception pour la capturer dans le contrôleur
            throw new SQLException("Erreur lors de la récupération des activités", e);
        }
        return activites;
    }

    // Supprimer une activité
    public void delete(ActiviteSportive activite) throws SQLException {
        String query = "DELETE FROM activite_sportive WHERE id_activite = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, activite.getId_activite());
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Propager l'exception pour la capturer dans le contrôleur
            throw new SQLException("Erreur lors de la suppression de l'activité", e);
        }
    }

    // Affichage de toutes les activités
    public List<ActiviteSportive> DisplayAll() throws SQLException {
        return getAllActivites();
    }
}

