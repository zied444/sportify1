package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sportify";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            
            // Verify the utilisateur table exists
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'utilisateur'");
            if (!rs.next()) {
                System.err.println("Error: The 'utilisateur' table does not exist in the database");
                return;
            }
            
            System.out.println("Database connection verified successfully");
        } catch (Exception e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 