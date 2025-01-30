package com.nathan.footballsquadmanagerbp2.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    Connection conn;
    private String url = "jdbc:mysql://localhost:3306/f_squad_manager?user=root&password=";

    public Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
