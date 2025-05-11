package constants;

import javafx.beans.property.SimpleStringProperty;

public class Programme {
    private final SimpleStringProperty nom;
    private final SimpleStringProperty description;
    private final SimpleStringProperty niveau;

    public Programme(String nom, String description, String niveau) {
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
        this.niveau = new SimpleStringProperty(niveau);
    }

    public String getNom() { return nom.get(); }
    public String getDescription() { return description.get(); }
    public String getNiveau() { return niveau.get(); }

    public SimpleStringProperty nomProperty() { return nom; }
    public SimpleStringProperty descriptionProperty() { return description; }
    public SimpleStringProperty niveauProperty() { return niveau; }
}
