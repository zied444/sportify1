package Models;

import java.sql.Date;

public class ActiviteSportive {

    private int id_activite;  // Correspond à id_activite dans la base de données
    private String sport;     // Correspond à sport
    private int duree;        // Correspond à duree
    private Date date_activite;  // Correspond à date_activite
    private double prix_par_activite;  // Correspond à prix_par_activite
    private int utilisateur_id;  // Correspond à utilisateur_id

    // Constructeur sans id_activite pour l'insertion
    public ActiviteSportive(String sport, int duree, Date dateActivite, double prix, int utilisateurId) {
        this.sport = sport;
        this.duree = duree;
        this.date_activite = dateActivite;
        this.prix_par_activite = prix;
        this.utilisateur_id = utilisateurId;
    }

    // Constructeur avec id_activite pour récupérer depuis la base de données
    public ActiviteSportive(int id_activite, String sport, int duree, Date dateActivite, double prix, int utilisateurId) {
        this.id_activite = id_activite;
        this.sport = sport;
        this.duree = duree;
        this.date_activite = dateActivite;
        this.prix_par_activite = prix;
        this.utilisateur_id = utilisateurId;
    }

    // Getters et setters
    public int getId_activite() {
        return id_activite;
    }

    public void setId_activite(int id_activite) {
        this.id_activite = id_activite;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Date getDate_activite() {
        return date_activite;
    }

    public void setDate_activite(Date date_activite) {
        this.date_activite = date_activite;
    }

    public double getPrix_par_activite() {
        return prix_par_activite;
    }

    public void setPrix_par_activite(double prix_par_activite) {
        this.prix_par_activite = prix_par_activite;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }
}




