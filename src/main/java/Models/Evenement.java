package Models;

import java.sql.Date;

public class Evenement {
    private int id;
    private String nom;
    private String lieu;
    private Date date;
    private String type;
    private String description;
    private int utilisateur_id;
    private int Nbr_places;
    private int nbr_personne_inscrit;
    private String imagePath;

    public Evenement() {

    }
    public Evenement(String nom , String lieu ,Date date , String type , String description  , int Nbr_places ) {
        this.nom = nom;
        this.lieu = lieu;
        this.date = date;
        this.type = type;
        this.description = description;
        this.Nbr_places = Nbr_places;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(int utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public int getNbr_places() {
        return Nbr_places;
    }

    public void setNbr_places(int nbr_places) {
        this.Nbr_places = nbr_places;
    }

    public int getNbr_personne_inscrit() {
        return nbr_personne_inscrit;
    }

    public void setNbr_personne_inscrit(int nbr_personne_inscrit) {
        this.nbr_personne_inscrit = nbr_personne_inscrit;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "nom='" + nom + '\'' +
                ", lieu='" + lieu + '\'' +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", nbr_places=" + Nbr_places +
                ", nbr_personne_inscrit=" + nbr_personne_inscrit +
                '}';
    }
}

