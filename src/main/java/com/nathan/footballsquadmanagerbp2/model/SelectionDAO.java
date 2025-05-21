package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.*;

// This class is used to manage the selection table in the database.
public class SelectionDAO {
    // Connection to the database.
    private final Connection conn;

    // Constructor that calls an instance of the connection.
    public SelectionDAO() {
        try {
            // Get the connection to the database.
            conn = DBConnector.getInstance().getConnection();
        } catch (SQLException e) {
            // If the connection fails, throw a runtime exception.
            throw new RuntimeException(e);
        }
    }

    // Query to insert selection Model in database.
    public Selection insertNewSelection(Selection selection) {
        // query to insert a new selection in the database.
        String query = "INSERT INTO selection (id, name, date, user_id, formation_id) VALUES (?, ?, ?, ?, ?)";
        // pstmt is a prepared statement that is used to execute the query.
        // RETURN_GENERATED_KEYS is used to get the generated id of the selection.
        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Set the values of the selection.
            pstmt.setInt(1, selection.getSelectionId());
            pstmt.setString(2, selection.getSelectionName());
            pstmt.setDate(3, selection.getSelectionDate());
            pstmt.setInt(4, selection.getSelectionUser().getUserId());
            pstmt.setInt(5, selection.getSelectionFormation().getFormationId());
            // Execute the query.
            pstmt.executeUpdate();

            // Get the generated id in selection table.
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                // If the generated id is not null, get the selection.
                if (rs.next()) {
                    // Get the generated id.
                    int generatedId = rs.getInt(1);
                    // Set the generated id in the selection.
                    return getSelection(generatedId);
                }
            }
        } catch (SQLException e) {
            // If the query fails, throw a runtime exception.
            throw new RuntimeException(e);
        }
        // If the query fails, return null.
        return null;
    }

    // Get specific selection query.
    public Selection getSelection(int selectionId) {
        // query to get a selection from the database.
        String query = "SELECT * FROM selection WHERE id = ?";

        // pstmt is a prepared statement that is used to execute the query.
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the value of the selection id.
            pstmt.setInt(1, selectionId);

            // Execute the query and get the result set.
            try (ResultSet rs = pstmt.executeQuery()) {
                // If the result set is not empty, get the selection.
                if (rs.next()) {
                    // return a new selection object with the values from the result set.
                    return new Selection(rs);
                }
            }
        } catch (SQLException e) {
            // If the query fails, throw a runtime exception.
            throw new RuntimeException(e);
        }
        // If the query fails, return null.
        return null;
    }

    // Delete query.
    public void deleteSelection(Selection selection) {
        // query to delete a selection from the database.
        // The selection is deleted from the selection_details table first, then from the selection table.
        // This is done to avoid foreign key constraint violations.
        String query1 = "DELETE FROM selection_details WHERE selection_id = ?";
        String query2 = "DELETE FROM selection WHERE id = ?";

        // pstmt is a prepared statement that is used to execute the query.
        try (PreparedStatement pstmt = conn.prepareStatement(query1);
                // pstmt2 is a prepared statement that is used to execute the second query.
             PreparedStatement pstmt2 = conn.prepareStatement(query2)) {
            // excute the first query to delete the selection from the selection_details table.
            pstmt.setInt(1, selection.getSelectionId());
            pstmt.executeUpdate();
            // excute the second query to delete the selection from the selection table.
            pstmt2.setInt(1, selection.getSelectionId());
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            // If the query fails, throw a runtime exception.
            throw new RuntimeException(e);
        }
    }

    // Get all selections query.
    public ResultSet getAllSelections() {
        // the ResultSet is used to get the result of the query.
        ResultSet selections;
        // query to get all selections from the database.
        String query = "SELECT * FROM selection";
        try {
            // Create a statement to execute the query.
            Statement stmt = conn.createStatement();
            // Execute the query and get the result set.
            selections = stmt.executeQuery(query);
        } catch (SQLException e) {
            // If the query fails, throw a runtime exception.
            throw new RuntimeException(e);
        }
        // Return the result set.
        return selections;
    }
}
