package com.nathan.footballsquadmanagerbp2.models;

import java.sql.Date;

public class Selection {
    private int selectionId;
    private String selectionName;
    private Date selectionDate;
    private User selectionUser;
    private Formation selectionFormation;

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

    public void setSelectionId(int selectionId) {
        this.selectionId = selectionId;
    }

    public void setSelectionName(String selectionName) {
        this.selectionName = selectionName;
    }

    public void setSelectionDate(Date selectionDate) {
        this.selectionDate = selectionDate;
    }

    public void setSelectionUser(User selectionUser) {
        this.selectionUser = selectionUser;
    }

    public void setSelectionFormation(Formation selectionFormation) {
        this.selectionFormation = selectionFormation;
    }
}
