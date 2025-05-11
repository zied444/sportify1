package Models;

import java.time.LocalDate;

public class Reservation {
    private int id_reservation;
    private Integer utilisateur_id;
    private Integer evenement_id;
    private String statut;
    private LocalDate date_res;
    private float montant_tot;
    private int nb_places_res;

    public Reservation() {}

    public Reservation(int id_reservation, Integer utilisateur_id, Integer evenement_id,
                       String statut, LocalDate date_res, float montant_tot, int nb_places_res) {
        this.id_reservation = id_reservation;
        this.utilisateur_id = utilisateur_id;
        this.evenement_id = evenement_id;
        setStatut(statut);
        this.date_res = date_res;
        this.montant_tot = montant_tot;
        this.nb_places_res = nb_places_res;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public Integer getUtilisateur_id() {
        return utilisateur_id;
    }

    public void setUtilisateur_id(Integer utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public Integer getEvenement_id() {
        return evenement_id;
    }

    public void setEvenement_id(Integer evenement_id) {
        this.evenement_id = evenement_id;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        if (statut != null && !statut.equals("inscrit") && !statut.equals("annule")) {
            throw new IllegalArgumentException("Le statut doit être 'inscrit' ou 'annule'.");
        }
        this.statut = statut;
    }

    public LocalDate getDate_res() {
        return date_res;
    }

    public void setDate_res(LocalDate date_res) {
        this.date_res = date_res;
    }

    public float getMontant_tot() {
        return montant_tot;
    }

    public void setMontant_tot(float montant_tot) {
        this.montant_tot = montant_tot;
    }

    public int getNb_places_res() {
        return nb_places_res;
    }

    public void setNb_places_res(int nb_places_res) {
        this.nb_places_res = nb_places_res;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id_reservation=" + id_reservation +
                ", utilisateur_id=" + utilisateur_id +
                ", evenement_id=" + evenement_id +
                ", statut='" + statut + '\'' +
                ", date_res=" + date_res +
                ", montant_tot=" + montant_tot +
                ", nb_places_res=" + nb_places_res +
                '}';
    }
}