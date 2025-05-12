package models;

import java.sql.Date;
import java.util.Objects;

public class Plan_Entrainement {
    private int idPlan;
    private int utilisateurId;
    private int coachId;
    private String nom;
    private Date dateCreation;
    private String typeActivite;
    private String contenu;

    // Constructeurs
    public Plan_Entrainement() {}

    public Plan_Entrainement(int utilisateurId, int coachId, String nom,
                             Date dateCreation, String typeActivite, String contenu) {
        this.utilisateurId = utilisateurId;
        this.coachId = coachId;
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.typeActivite = typeActivite;
        this.contenu = contenu;
    }

    public Plan_Entrainement(int idPlan, int utilisateurId, int coachId, String nom,
                             Date dateCreation, String typeActivite, String contenu) {
        this(utilisateurId, coachId, nom, dateCreation, typeActivite, contenu);
        this.idPlan = idPlan;
    }

    // Getters & Setters
    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getTypeActivite() {
        return typeActivite;
    }

    public void setTypeActivite(String typeActivite) {
        this.typeActivite = typeActivite;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    // equals() et hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan_Entrainement that = (Plan_Entrainement) o;
        return idPlan == that.idPlan &&
                utilisateurId == that.utilisateurId &&
                coachId == that.coachId &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(dateCreation, that.dateCreation) &&
                Objects.equals(typeActivite, that.typeActivite) &&
                Objects.equals(contenu, that.contenu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlan, utilisateurId, coachId, nom, dateCreation, typeActivite, contenu);
    }

    // toString() amélioré
    @Override
    public String toString() {
        return "PlanEntrainement{" +
                "idPlan=" + idPlan +
                ", utilisateurId=" + utilisateurId +
                ", coachId=" + coachId +
                ", nom='" + nom + '\'' +
                ", dateCreation=" + dateCreation +
                ", typeActivite='" + typeActivite + '\'' +
                ", contenu='" + contenu + '\'' +
                '}';
    }
}