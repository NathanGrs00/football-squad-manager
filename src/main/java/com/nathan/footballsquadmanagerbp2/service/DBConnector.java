package com.nathan.footballsquadmanagerbp2.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private Connection conn;
    private String url = "jdbc:mysql://localhost:3306/f_squad_manager?user=root&password=";

    private static DBConnector instance;

    private DBConnector() {}

    public void init() {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public static DBConnector getInstance() throws SQLException {
        if (instance != null && !instance.getConnection().isClosed()) {
            return instance;
        } else {
            instance = new DBConnector();
            instance.init();
        }
        return instance;
    }
}
