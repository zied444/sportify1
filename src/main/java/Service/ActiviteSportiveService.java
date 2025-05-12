package Service;

import models.ActiviteSportive;
import Utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActiviteSportiveService {
    private Connection connection;

    public ActiviteSportiveService() {
        // Utilisation correcte de MyDB
        connection = MyDB.getInstance().getConnection();
    }

    public void Create(ActiviteSportive activite) throws SQLException {
        String sql = "INSERT INTO activite_sportive (sport, duree, date_activite, prix_par_activite, utilisateur_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, activite.getSport());
        ps.setInt(2, activite.getDuree());
        ps.setDate(3, activite.getDateActivite());
        ps.setDouble(4, activite.getPrix());
        ps.setInt(5, activite.getUtilisateurId());
        ps.executeUpdate();
    }

    public void delete(ActiviteSportive activite) throws SQLException {
        String sql = "DELETE FROM activite_sportive WHERE id_activite = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, activite.getId());
        ps.executeUpdate();
    }

    public List<ActiviteSportive> DisplayAll() throws SQLException {
        List<ActiviteSportive> list = new ArrayList<>();
        String sql = "SELECT * FROM activite_sportive";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            ActiviteSportive a = new ActiviteSportive(
                    rs.getInt("id_activite"),
                    rs.getString("sport"),
                    rs.getInt("duree"),
                    rs.getDate("date_activite"),
                    rs.getDouble("prix_par_activite"),
                    rs.getInt("utilisateur_id")
            );
            list.add(a);
        }
        return list;
    }

    public void update(ActiviteSportive activite) throws SQLException {
        String sql = "UPDATE activite_sportive SET sport = ?, duree = ?, date_activite = ?, prix_par_activite = ?, utilisateur_id = ? WHERE id_activite = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, activite.getSport());
        ps.setInt(2, activite.getDuree());
        ps.setDate(3, activite.getDateActivite());
        ps.setDouble(4, activite.getPrix());
        ps.setInt(5, activite.getUtilisateurId());
        ps.setInt(6, activite.getId());
        ps.executeUpdate();
    }
}
