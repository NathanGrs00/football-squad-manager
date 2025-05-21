package com.nathan.footballsquadmanagerbp2.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class is used to connect to the database.
public class DBConnector {
    // The connection to the database.
    private Connection conn;

    //Singleton pattern to ensure only one instance.
    private static DBConnector instance;

    // Private constructor so that it does not get overwritten in other files.
    private DBConnector() {}

    public void init() {
        try {
            // Load the MySQL JDBC driver
            String url = "jdbc:mysql://localhost:3306/f_squad_manager?user=root&password=";
            // DriverManager is used to create a connection to the database.
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            // Handle any errors that may have occurred.
            throw new RuntimeException(e);
        }
    }

    // Returns the connection to the database.
    public Connection getConnection() {
        return conn;
    }

    // Returns the same instance.
    public static DBConnector getInstance() throws SQLException {
        // If the instance is not null and the connection is not closed, return the instance.
        if (instance != null && !instance.getConnection().isClosed()) {
            return instance;
        } else {
            // Make a new instance if the old one is closed.
            instance = new DBConnector();
            instance.init();
        }
        // Return the instance.
        return instance;
    }
}
