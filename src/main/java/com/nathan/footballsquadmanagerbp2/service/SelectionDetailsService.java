package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.SelectionDetail;
import com.nathan.footballsquadmanagerbp2.model.SelectionDetailDAO;

// This class is responsible for managing the selection details of players in a football squad.
public class SelectionDetailsService {
    SelectionDetailDAO selectionDetailDAO;

    // Constructor initializes the SelectionDetailDAO object.
    public SelectionDetailsService(){
        selectionDetailDAO = new SelectionDetailDAO();
    }

    // taking Integer values for selectionId, playerId, and positionId
    public void insertDetails(int selectionId, int playerId, int positionId) {
        // Making a new SelectionDetail object and inserting it into the database
        selectionDetailDAO.insertDetails(new SelectionDetail(selectionId, playerId, positionId));
    }

    // taking Integer values for selectionId, playerId, and positionId
    public void removePlayerFromSelection(int selectionId, int playerId, int positionId) {
        // Making a new SelectionDetail object and removing it from the database
        selectionDetailDAO.removePlayerFromSelection(new SelectionDetail(selectionId, playerId, positionId));
    }

    // removing all entries for a specific selectionId
    public void removeAllEntries(int selectionId) {
        selectionDetailDAO.removeAllEntries(selectionId);
    }
}
