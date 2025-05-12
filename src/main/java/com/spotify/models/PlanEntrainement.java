package com.spotify.models;

import javafx.beans.property.*;

public class PlanEntrainement {
    private final IntegerProperty id;
    private final StringProperty titre;
    private final StringProperty contenu;
    private final StringProperty dateDebut;
    private final StringProperty dateFin;

    public PlanEntrainement() {
        this.id = new SimpleIntegerProperty();
        this.titre = new SimpleStringProperty();
        this.contenu = new SimpleStringProperty();
        this.dateDebut = new SimpleStringProperty();
        this.dateFin = new SimpleStringProperty();
    }

    // Getters et Setters pour id
    public int getId() { return id.get(); }
    public void setId(int value) { id.set(value); }
    public IntegerProperty idProperty() { return id; }

    // Getters et Setters pour titre
    public String getTitre() { return titre.get(); }
    public void setTitre(String value) { titre.set(value); }
    public StringProperty titreProperty() { return titre; }

    // Getters et Setters pour contenu
    public String getContenu() { return contenu.get(); }
    public void setContenu(String value) { contenu.set(value); }
    public StringProperty contenuProperty() { return contenu; }

    // Getters et Setters pour dateDebut
    public String getDateDebut() { return dateDebut.get(); }
    public void setDateDebut(String value) { dateDebut.set(value); }
    public StringProperty dateDebutProperty() { return dateDebut; }

    // Getters et Setters pour dateFin
    public String getDateFin() { return dateFin.get(); }
    public void setDateFin(String value) { dateFin.set(value); }
    public StringProperty dateFinProperty() { return dateFin; }
} 