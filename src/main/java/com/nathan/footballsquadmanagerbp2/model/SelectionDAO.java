package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.*;

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
    public Selection insertNewSelection(Selection selection) {
        String query = "INSERT INTO selection (id, name, date, user_id, formation_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, selection.getSelectionId());
            pstmt.setString(2, selection.getSelectionName());
            pstmt.setDate(3, selection.getSelectionDate());
            pstmt.setInt(4, selection.getSelectionUser().getUserId());
            pstmt.setInt(5, selection.getSelectionFormation().getFormationId());
            pstmt.executeUpdate();

            // Get the generated id in selection table.
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if(rs.next()) {
                    return getSelection(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Selection getSelection(int selectionId) {
        String query = "SELECT * FROM selection WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, selectionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Selection(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
