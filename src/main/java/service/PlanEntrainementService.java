package service;

import models.Plan_Entrainement;
import utils.MyDb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlanEntrainementService implements Iservice<Plan_Entrainement> {
    private Connection conn;

    public PlanEntrainementService() {
        this.conn = MyDb.getInstance().getConnection();
    }

    @Override
    public void Create(Plan_Entrainement plan) throws Exception {
        String sql = "INSERT INTO plan_entrainement (utilisateur_id, coach_id, nom, date_creation, type_activite, contenu) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, plan.getUtilisateurId());
        stmt.setInt(2, plan.getCoachId());
        stmt.setString(3, plan.getNom());
        stmt.setDate(4, plan.getDateCreation());
        stmt.setString(5, plan.getTypeActivite());
        stmt.setString(6, plan.getContenu());
        stmt.executeUpdate();
    }

    @Override
    public void update(Plan_Entrainement plan) throws Exception {
        String sql = "UPDATE plan_entrainement SET utilisateur_id = ?, coach_id = ?, nom = ?, date_creation = ?, type_activite = ?, contenu = ? WHERE id_plan = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, plan.getUtilisateurId());
        stmt.setInt(2, plan.getCoachId());
        stmt.setString(3, plan.getNom());
        stmt.setDate(4, plan.getDateCreation());
        stmt.setString(5, plan.getTypeActivite());
        stmt.setString(6, plan.getContenu());
        stmt.setInt(7, plan.getIdPlan()); // ! important
        stmt.executeUpdate();
    }

    @Override
    public void delete(Plan_Entrainement plan) throws Exception {
        String sql = "DELETE FROM plan_entrainement WHERE id_plan = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, plan.getIdPlan()); // ! important
        stmt.executeUpdate();
    }

    @Override
    public List<Plan_Entrainement> DisplayAll() throws Exception {
        String sql = "SELECT * FROM plan_entrainement";
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
        List<Plan_Entrainement> planList = new ArrayList<>();

        while (rs.next()) {
            Plan_Entrainement p = new Plan_Entrainement();
            p.setIdPlan(rs.getInt("id_plan"));
            p.setUtilisateurId(rs.getInt("utilisateur_id"));
            p.setCoachId(rs.getInt("coach_id"));
            p.setNom(rs.getString("nom"));
            p.setDateCreation(rs.getDate("date_creation"));
            p.setTypeActivite(rs.getString("type_activite"));
            p.setContenu(rs.getString("contenu"));
            planList.add(p);
        }
        return planList;
    }
}


