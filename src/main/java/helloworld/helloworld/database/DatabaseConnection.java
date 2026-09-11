package helloworld.helloworld.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class responsible for managing the database connection.
 * Ensures only one connection exists during the application lifecycle.
 * Supports MySQL depending on setup.
 *
 * @author Rama Houreh
 */
public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;
    private final String URL = "jdbc:mysql://localhost:3306/ping_pong_game";
    private final String USER = "root";
    private final String PASSWORD = "YOUR_PASSWORD_HERE";


    /**
     * Private constructor to enforce Singleton pattern.
     */
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully");
        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }
    }

    /**
     * Returns the single instance of DatabaseConnection.
     *
     * @return singleton instance
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Returns the active database connection.
     *
     * @return JDBC connection
     */
    public Connection getConnection() {
        return connection;
    }
}
