package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// This class handles the database operations for the SelectionDetail entity.
public class SelectionDetailDAO {
    // Database connection instance.
    private final Connection conn;

    public SelectionDetailDAO() {
        try {
            // Get the database connection.
            conn = DBConnector.getInstance().getConnection();
        } catch (SQLException e){
            // Handle any SQL exceptions that occur during connection.
            throw new RuntimeException(e);
        }
    }

    // Insert query.
    public void insertDetails(SelectionDetail selectionDetail) {
        // query to insert selection details into the database.
        String query = "INSERT INTO selection_details (selection_id, player_id, position_id) VALUES (?, ?, ?)";

        // Execute the query using a helper method.
        executeQuery(selectionDetail, query);
    }

    // Delete query.
    public void removePlayerFromSelection(SelectionDetail selectionDetail) {
        // query to delete selection details from the database.
        String query = "DELETE FROM selection_details WHERE selection_id = ? AND player_id = ? AND position_id = ?";

        // Execute the query using a helper method.
        executeQuery(selectionDetail, query);
    }

    // Helper method to execute the query.
    // for reducing code duplication.
    private void executeQuery(SelectionDetail selectionDetail, String query) {
        // PreparedStatement is being used to prevent SQL injection attacks.
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the parameters for the prepared statement.
            pstmt.setInt(1, selectionDetail.getSelectionId());
            pstmt.setInt(2, selectionDetail.getPlayerId());
            pstmt.setInt(3, selectionDetail.getPositionId());
            // Execute the update.
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Handle any SQL exceptions that occur during the execution of the query.
            throw new RuntimeException(e);
        }
    }

    // Remove all details
    public void removeAllEntries(int selectionId) {
        // query to delete all selection details from the database.
        String query = "DELETE FROM selection_details WHERE selection_id = ?";

        // PreparedStatement is being used to prevent SQL injection attacks.
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the parameters for the prepared statement.
            pstmt.setInt(1, selectionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // Handle any SQL exceptions that occur during the execution of the query.
            throw new RuntimeException(e);
        }
    }

    // Get all details from a selection.
    public List<SelectionDetail> getSelectionDetails(Selection selection) {
        // List to store the selection details.
        List<SelectionDetail> details = new ArrayList<>();
        // query to select all selection details from the database.
        String query = "SELECT * FROM selection_details WHERE selection_id = ?";

        // PreparedStatement is being used to prevent SQL injection attacks.
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the parameters for the prepared statement.
            pstmt.setInt(1, selection.getSelectionId());
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the result set and create SelectionDetail objects.
            while (rs.next()) {
                // Get the player_id and position_id from the result set.
                int playerId = rs.getInt("player_id");
                int positionId = rs.getInt("position_id");
                // Create a new SelectionDetail and add it to the list
                details.add(new SelectionDetail(selection.getSelectionId(), playerId, positionId));
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions that occur during the execution of the query.
            throw new RuntimeException(e);
        }
        // Return the list of selection details.
        return details;
    }
}
