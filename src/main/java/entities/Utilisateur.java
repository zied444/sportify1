package entities;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String role;
    private String preferencesSportives;

    public Utilisateur() {}

    public Utilisateur(String nom, String prenom, String email, String motDePasse, String role, String preferencesSportives) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = motDePasse;
        this.role = role;
        this.preferencesSportives = preferencesSportives;
    }

    // Getters et Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPreferencesSportives() { return preferencesSportives; }
    public void setPreferencesSportives(String preferencesSportives) { this.preferencesSportives = preferencesSportives; }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", preferencesSportives='" + preferencesSportives + '\'' +
                '}';
    }
}
