package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:8889/todolist"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "root"; 

    public static Connection getConnection() throws SQLException {
        System.out.println("Tentative de connexion à la base de données...");
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connexion réussie.");
        return conn;
    }
}