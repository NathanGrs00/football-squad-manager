package com.nathan.footballsquadmanagerbp2.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Selection {
    // Storing User and Formation in Selection.
    private final int selectionId;
    private final String selectionName;
    private final Date selectionDate;
    private final User selectionUser;
    private final Formation selectionFormation;

    public Selection(ResultSet rs) throws SQLException {
        this.selectionId = rs.getInt("id");
        this.selectionName = rs.getString("name");
        this.selectionDate = rs.getDate("date");
        UserDAO userDAO = new UserDAO();
        this.selectionUser = userDAO.getUserById(rs.getInt("user_id"));
        FormationDAO formationDAO = new FormationDAO();
        this.selectionFormation = formationDAO.getFormationById(rs.getInt("formation_id"));
    }

    public Selection(int selectionId, String selectionName, Date selectionDate, User selectionUser, Formation selectionFormation) {
        this.selectionId = selectionId;
        this.selectionName = selectionName;
        this.selectionDate = selectionDate;
        this.selectionUser = selectionUser;
        this.selectionFormation = selectionFormation;
    }

    public int getSelectionId() {
        return selectionId;
    }

    public String getSelectionName() {
        return selectionName;
    }

    public Date getSelectionDate() {
        return selectionDate;
    }

    public User getSelectionUser() {
        return selectionUser;
    }

    public Formation getSelectionFormation() {
        return selectionFormation;
    }
}
