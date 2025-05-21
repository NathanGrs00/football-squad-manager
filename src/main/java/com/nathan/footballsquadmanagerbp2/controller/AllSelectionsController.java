package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.model.Selection;
import com.nathan.footballsquadmanagerbp2.model.SelectionDetail;
import com.nathan.footballsquadmanagerbp2.model.SelectionDetailDAO;
import com.nathan.footballsquadmanagerbp2.service.SelectionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;

public class AllSelectionsController {
    // Creating a new instance of SelectionService and SelectionDetailDAO to access the database.
    private final SelectionService selectionService;
    private final SelectionDetailDAO selectionDetailDAO;

    // Instantiating the SelectionService and SelectionDetailDAO.
    public AllSelectionsController() {
        this.selectionService = new SelectionService();
        this.selectionDetailDAO = new SelectionDetailDAO();
    }

    // Navigating to the new stage, with player.
    public void editSelection(Selection selection) {
        // getSelectionDetails(selection) is called to get the selection details for the selection.
        List<SelectionDetail> selectionDetails = selectionDetailDAO.getSelectionDetails(selection);
        // Navigates to the getBuilder function in the FootballSquadManager class.
        FootballSquadManager footballSquadManager = new FootballSquadManager();
        // getBuilder makes a new stage with the selection and selectionDetails.
        footballSquadManager.getBuilder(selection, selectionDetails);
    }

    // Confirming the user wants to delete the selection.
    public void deleteSelection(Selection selection) {
        // Creating an alert to confirm the deletion of the selection.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // Setting the title, header and content text of the alert.
        alert.setTitle("Delete Selection");
        alert.setHeaderText("Are you sure you want to delete this selection?");
        alert.setContentText("This action cannot be undone.");

        // if the user clicks OK, the selection is deleted.
        // If the user clicks Cancel, the alert is closed.
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            selectionService.deleteSelection(selection);
        }
    }

    // Calling selectionService to get all the selections in the database and making an ObservableList of all selections.
    public ObservableList<Selection> getAllSelections() {
        // Calling selectionService to get all the selections in the database.
        // Making an ArrayList of all selections called selections.
        ArrayList<Selection> selections = selectionService.getAllSelections();
        // Creating an ObservableList of all selections called allSelections.
        ObservableList<Selection> allSelections = FXCollections.observableArrayList();
        // Adding all the selections to the ObservableList.
        allSelections.addAll(selections);
        // Returning the ObservableList of all selections.
        return allSelections;
    }
}
