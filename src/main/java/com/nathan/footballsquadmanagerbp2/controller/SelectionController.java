package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Formation;
import com.nathan.footballsquadmanagerbp2.model.FormationDAO;

import java.util.List;

public class SelectionController {
    private FormationDAO formationDAO;

    public List<Formation> getFormations() {
        return formationDAO.getAllFormations();
    }
}
