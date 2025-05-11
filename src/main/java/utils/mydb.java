package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mydb {
    private String url = "jdbc:mysql://localhost:3306/sportify"; // Your database URL
    private String user = "root";                               // Your database username
    private String password = "";                               // Your database password
    private Connection conn;                                    // The database connection
    private static mydb instance;                               // Singleton instance

    // Singleton pattern to get the instance
    public static mydb getInstance() {
        if (instance == null) {
            instance = new mydb();
        }
        return instance;
    }

    // Getter for the connection
    public Connection getConn() {
        return conn;
    }

    // Constructor to establish the connection
    public mydb() {
        try {
            this.conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    // Add this method to close the connection
    public void close() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}