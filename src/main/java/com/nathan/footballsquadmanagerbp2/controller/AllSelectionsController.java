package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.model.Selection;
import com.nathan.footballsquadmanagerbp2.model.SelectionDetail;
import com.nathan.footballsquadmanagerbp2.model.SelectionDetailDAO;
import com.nathan.footballsquadmanagerbp2.service.SelectionService;
import com.nathan.footballsquadmanagerbp2.view.SelectionBuilderView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;

public class AllSelectionsController {

    private final SelectionService selectionService;
    private SelectionDetailDAO selectionDetailDAO;

    public AllSelectionsController() {
        this.selectionService = new SelectionService();
        this.selectionDetailDAO = new SelectionDetailDAO();
    }

    // Navigating to the new stage, with player.
    public void editSelection(Selection selection) {
        List<SelectionDetail> selectionDetails = selectionDetailDAO.getSelectionDetails(selection);
        new SelectionBuilderView(selection, selectionDetails);
    }

    // Confirming the user wants to delete the selection.
    public void deleteSelection(Selection selection) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Selection");
        alert.setHeaderText("Are you sure you want to delete this selection?");
        alert.setContentText("This action cannot be undone.");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            selectionService.deleteSelection(selection);
        }
    }

    // Calling selectionService to get all the selections in the database.
    public ObservableList<Selection> getAllSelections() {
        ArrayList<Selection> selections = selectionService.getAllSelections();
        ObservableList<Selection> allSelections = FXCollections.observableArrayList();
        allSelections.addAll(selections);
        return allSelections;
    }
}
