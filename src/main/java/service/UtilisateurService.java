package service;

import entities.Utilisateur;
import interfaces.IService;
import utils.DatabaseConnection;
import utils.EmailValidator;
import utils.PasswordHasher;
import constants.Roles;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService implements IService<Utilisateur> {
    private Connection conn;

    public UtilisateurService() {
        conn = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        if (!EmailValidator.validate(utilisateur.getEmail())) {
            System.out.println("Email invalide !");
            return;
        }

        String sql = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe, role, preferences_sportives) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getEmail());
            stmt.setString(4, PasswordHasher.hashPassword(utilisateur.getPassword()));
            stmt.setString(5, utilisateur.getRole());
            stmt.setString(6, utilisateur.getPreferencesSportives());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Utilisateur ajouté avec succès !");
            } else {
                System.out.println("Aucun utilisateur n'a été ajouté !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Utilisateur connecter(String email, String motDePasse) {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hash = rs.getString("mot_de_passe");
                if (PasswordHasher.checkPassword(motDePasse, hash)) {
                    System.out.println("Connexion réussie !");
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setId(rs.getInt("id_utilisateur"));
                    utilisateur.setNom(rs.getString("nom"));
                    utilisateur.setPrenom(rs.getString("prenom"));
                    utilisateur.setEmail(rs.getString("email"));
                    utilisateur.setRole(rs.getString("role"));
                    utilisateur.setPreferencesSportives(rs.getString("preferences_sportives"));
                    return utilisateur;
                } else {
                    System.out.println("Mot de passe incorrect !");
                }
            } else {
                System.out.println("Utilisateur non trouvé !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Utilisateur> listerUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id_utilisateur"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                u.setPreferencesSportives(rs.getString("preferences_sportives"));
                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
        return utilisateurs;
    }

    @Override
    public void modifierUtilisateur(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET nom=?, prenom=?, email=?, role=?, preferences_sportives=? WHERE id_utilisateur=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getEmail());
            stmt.setString(4, utilisateur.getRole());
            stmt.setString(5, utilisateur.getPreferencesSportives());
            stmt.setInt(6, utilisateur.getId());
            stmt.executeUpdate();
            System.out.println("Utilisateur modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'utilisateur : " + e.getMessage());
        }
    }

    @Override
    public void supprimerUtilisateur(int idUtilisateur) {
        String sql = "DELETE FROM utilisateur WHERE id_utilisateur=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUtilisateur);
            stmt.executeUpdate();
            System.out.println("Utilisateur supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }

    public List<Utilisateur> rechercherParNom(String nom) {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur WHERE nom LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nom + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id_utilisateur"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                u.setPreferencesSportives(rs.getString("preferences_sportives"));
                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
        }
        return utilisateurs;
    }

    public List<Utilisateur> filtrerParRole(String role) {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur WHERE role=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id_utilisateur"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                u.setPreferencesSportives(rs.getString("preferences_sportives"));
                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du filtrage par rôle : " + e.getMessage());
        }
        return utilisateurs;
    }
}
