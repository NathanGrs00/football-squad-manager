package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PositionDAO {
    private Connection conn;

    public PositionDAO() {
        try {
            conn = DBConnector.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllPositions() {
        ResultSet positions;
        String query = "SELECT * FROM position";
        try {
            Statement stmt = conn.createStatement();
            positions = stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return positions;
    }
}
