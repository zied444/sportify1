package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDb {
    private static final String URL = "jdbc:mysql://localhost:3306/isportify";
    private static final String USER = "root";
    private static final String PWD = "";

    private Connection conn;
    private static MyDb instance;

    // Constructeur privé (Singleton)
    private MyDb() {
        try {
            conn = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Connexion établie avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion : " + e.getMessage());
        }
    }

    // Récupération de l'instance unique
    public static MyDb getInstance() {
        if (instance == null) {
            instance = new MyDb();
        }
        return instance;
    }

    // Récupérer la connexion actuelle
    public Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                System.out.println("Reconnexion en cours...");
                conn = DriverManager.getConnection(URL, USER, PWD);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la reconnexion : " + e.getMessage());
        }
        return conn;
    }
}



