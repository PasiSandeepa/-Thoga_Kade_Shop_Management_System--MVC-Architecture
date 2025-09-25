package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;

    private Connection connection;

    private DBConnection() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");

    }

    //Singleton instance ලබාගන්න
    public static synchronized DBConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }
    public  Connection getConnection() throws SQLException {

        if (connection.isClosed()){
            DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "1234");
        }
        return connection;
    }
}