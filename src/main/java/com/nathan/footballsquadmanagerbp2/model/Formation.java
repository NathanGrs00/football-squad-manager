package com.nathan.footballsquadmanagerbp2.model;

public class Formation {
    private int formationId;
    private String formationName;

    public Formation(int formationId, String formationName) {
        this.formationId = formationId;
        this.formationName = formationName;
    }

    public int getFormationId() {
        return formationId;
    }

    public String getFormationName() {
        return formationName;
    }

    public void setFormationId(int formationId) {
        this.formationId = formationId;
    }

    public void setFormationName(String formationName) {
        this.formationName = formationName;
    }
}
