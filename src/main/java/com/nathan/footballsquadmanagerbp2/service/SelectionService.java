package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.Selection;
import com.nathan.footballsquadmanagerbp2.model.SelectionDAO;

public class SelectionService {
    SelectionDAO selectionDAO;

    public Selection saveSelection(Selection selection) {
        selectionDAO = new SelectionDAO();
        return selectionDAO.insertNewSelection(selection);
    }

    public int getPlayerProficiencyForPosition(int playerId, int positionId) {
        //TODO: add player filtering based on prof.
        return 5;
    }
}
