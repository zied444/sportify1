package Services;

import Models.Evenement;
import utils.MyDB;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements IServices<Evenement> {
    private Connection connection;

    public EvenementService() {
        connection =    MyDB.getInstance().getConnection();
    }

    @Override
    public void create(Evenement evenement) throws SQLException {
        String query = "INSERT INTO evenement (nom, lieu, date, type, description, nbr_places, utilisateur_id, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, evenement.getNom());
            statement.setString(2, evenement.getLieu());
            statement.setDate(3, evenement.getDate());
            statement.setString(4, evenement.getType());
            statement.setString(5, evenement.getDescription());
            statement.setInt(6, evenement.getNbr_places());
            statement.setInt(7, evenement.getUtilisateur_id());
            statement.setString(8, evenement.getImagePath());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    evenement.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Evenement evenement) throws SQLException {
        String req = "UPDATE evenement SET nom=?, lieu=?, date=?, type=?, description=?, nbr_places=?, image_path=? WHERE id_evenement=?";
        try (PreparedStatement pstmt = connection.prepareStatement(req)) {
            pstmt.setString(1, evenement.getNom());
            pstmt.setString(2, evenement.getLieu());
            pstmt.setDate(3, evenement.getDate());
            pstmt.setString(4, evenement.getType());
            pstmt.setString(5, evenement.getDescription());
            pstmt.setInt(6, evenement.getNbr_places());
            pstmt.setString(7, evenement.getImagePath());
            pstmt.setInt(8, evenement.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Événement modifié !");
            } else {
                throw new SQLException("Aucun événement trouvé avec ID : " + evenement.getId());
            }
        }
    }

    @Override
    public void delete(Evenement evenement) throws SQLException {
        String req = "DELETE FROM evenement WHERE id_evenement=?";
        try (PreparedStatement pstmt = connection.prepareStatement(req)) {
            pstmt.setInt(1, evenement.getId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Événement supprimé !");
            } else {
                throw new SQLException("Aucun événement trouvé avec ID : " + evenement.getId());
            }
        }
    }

    @Override
    public List<Evenement> display() throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        String query = "SELECT * FROM evenement";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Evenement evenement = new Evenement();
                evenement.setId(resultSet.getInt("id_evenement"));
                evenement.setNom(resultSet.getString("nom"));
                evenement.setLieu(resultSet.getString("lieu"));
                evenement.setDate(resultSet.getDate("date"));
                evenement.setType(resultSet.getString("type"));
                evenement.setDescription(resultSet.getString("description"));
                evenement.setNbr_places(resultSet.getInt("nbr_places"));
                evenement.setUtilisateur_id(resultSet.getInt("utilisateur_id"));
                evenement.setImagePath(resultSet.getString("image_path"));
                evenements.add(evenement);
            }
        }
        return evenements;
    }
}