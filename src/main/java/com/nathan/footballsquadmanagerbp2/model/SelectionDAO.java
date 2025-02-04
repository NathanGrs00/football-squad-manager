package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SelectionDAO {
    private final Connection conn;

    // Constructor that calls an instance of the connection.
    public SelectionDAO() {
        try {
            conn = DBConnector.getInstance().getConnection();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    // Query to insert selection Model in database.
    public void insertNewSelection(Selection selection) {
        String query = "INSERT INTO selection (id, name, date, user_id, formation_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, selection.getSelectionId());
            pstmt.setString(2, selection.getSelectionName());
            pstmt.setDate(3, selection.getSelectionDate());
            pstmt.setInt(4, selection.getSelectionUser().getUserId());
            pstmt.setInt(5, selection.getSelectionFormation().getFormationId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
