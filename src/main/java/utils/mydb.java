package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    // Instance unique (singleton)
    private static MyDB instance;

    // Connexion à la base de données
    private Connection connection;

    // Informations de connexion (modifiées pour sportify)
    private final String URL = "jdbc:mysql://localhost:3306/sportify"; // Nom de ta base
    private final String USER = "root"; // Ton utilisateur MySQL
    private final String PASSWORD = ""; // Ton mot de passe MySQL (laisse vide si aucun)

    // Constructeur privé
    private MyDB() {
        try {
            // Charger le driver JDBC (optionnel pour JDBC 4+)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connexion réussie à la base de données sportify !");
        } catch (ClassNotFoundException e) {
            System.err.println("⛔ Driver JDBC introuvable : " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("⛔ Erreur de connexion : " + e.getMessage());
        }
    }

    // Méthode pour obtenir l'instance unique
    public static MyDB getInstance() {
        if (instance == null) {
            instance = new MyDB();
        }
        return instance;
    }

    // Accès à la connexion
    public Connection getConnection() {
        return connection;
    }
}
