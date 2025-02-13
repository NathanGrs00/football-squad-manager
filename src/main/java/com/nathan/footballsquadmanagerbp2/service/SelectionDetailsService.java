package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.SelectionDetail;
import com.nathan.footballsquadmanagerbp2.model.SelectionDetailDAO;

public class SelectionDetailsService {
    SelectionDetailDAO selectionDetailDAO;

    public SelectionDetailsService(){
        selectionDetailDAO = new SelectionDetailDAO();
    }

    public void insertDetails(int selectionId, int playerId, int positionId) {
        selectionDetailDAO.insertDetails(new SelectionDetail(selectionId, playerId, positionId));
    }

    public void removePlayerFromSelection(int selectionId, int playerId, int positionId) {
        selectionDetailDAO.removePlayerFromSelection(new SelectionDetail(selectionId, playerId, positionId));
    }

    public void removeAllEntries(int selectionId) {
        selectionDetailDAO.removeAllEntries(selectionId);
    }
}
