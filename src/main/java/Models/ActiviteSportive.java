package models;

import java.sql.Date;

public class ActiviteSportive {
    private int id;
    private String sport;
    private int duree;
    private Date dateActivite;
    private double prix;
    private int utilisateurId;

    public ActiviteSportive(int id, String sport, int duree, Date dateActivite, double prix, int utilisateurId) {
        this.id = id;
        this.sport = sport;
        this.duree = duree;
        this.dateActivite = dateActivite;
        this.prix = prix;
        this.utilisateurId = utilisateurId;
    }

    public int getId() { return id; }
    public String getSport() { return sport; }
    public int getDuree() { return duree; }
    public Date getDateActivite() { return dateActivite; }
    public double getPrix() { return prix; }
    public int getUtilisateurId() { return utilisateurId; }

    public void setId(int id) { this.id = id; }
    public void setSport(String sport) { this.sport = sport; }
    public void setDuree(int duree) { this.duree = duree; }
    public void setDateActivite(Date dateActivite) { this.dateActivite = dateActivite; }
    public void setPrix(double prix) { this.prix = prix; }
    public void setUtilisateurId(int utilisateurId) { this.utilisateurId = utilisateurId; }
}

