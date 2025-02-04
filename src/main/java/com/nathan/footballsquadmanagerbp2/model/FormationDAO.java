package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FormationDAO {
    private final Connection conn;

    public FormationDAO() {
        try {
            conn = DBConnector.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Formation> getAllFormations() {
        List<Formation> formations = new ArrayList<>();
        ResultSet resultSet;
        String query = "SELECT * FROM formation";
        try {
            Statement stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                formations.add(new Formation(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return formations;
    }
}
