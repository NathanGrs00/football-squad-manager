package com.nathan.footballsquadmanagerbp2.model;

import com.nathan.footballsquadmanagerbp2.service.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectionDetailDAO {
    private final Connection conn;

    public SelectionDetailDAO() {
        try {
            conn = DBConnector.getInstance().getConnection();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertDetails(SelectionDetail selectionDetail) {
        String query = "INSERT INTO selection_details (selection_id, player_id, position_id) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, selectionDetail.getSelectionId());
            pstmt.setInt(2, selectionDetail.getPlayerId());
            pstmt.setInt(3, selectionDetail.getPositionId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeAllEntries(int selectionId) {
        String query = "DELETE FROM selection_details WHERE selection_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, selectionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removePlayerFromSelection(SelectionDetail selectionDetail) {
        String query = "DELETE FROM selection_details WHERE selection_id = ? AND player_id = ? AND position_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, selectionDetail.getSelectionId());
            pstmt.setInt(2, selectionDetail.getPlayerId());
            pstmt.setInt(3, selectionDetail.getPositionId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SelectionDetail> getSelectionDetails(Selection selection) {
        List<SelectionDetail> details = new ArrayList<>();
        String query = "SELECT * FROM selection_details WHERE selection_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, selection.getSelectionId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int playerId = rs.getInt("player_id");
                int positionId = rs.getInt("position_id");
                // Create a new SelectionDetail and add it to the list
                details.add(new SelectionDetail(selection.getSelectionId(), playerId, positionId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return details;
    }
}
