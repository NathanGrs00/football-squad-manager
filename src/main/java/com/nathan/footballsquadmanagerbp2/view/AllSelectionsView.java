package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.AllSelectionsController;
import com.nathan.footballsquadmanagerbp2.model.Selection;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AllSelectionsView {
    // Private variables so they can be used in all functions.
    private AllSelectionsController allSelectionsController;

    private HBox root;
    private Pane menubar;
    private VBox overviewContents;

    private TableView<Selection> allSelectionsTable;
    private ObservableList<Selection> selectionList;

    private HBox buttonBox;
    private Button addSelectionButton;
    private Button editSelectionButton;
    private Button deleteSelectionButton;

    // Constructor to populate the scene.
    public AllSelectionsView() {
        initVariables();
        initLayouts();
        initTable();
        handleButtonActions();
    }

    // Returns the scene for the view.
    public Scene getScene() {
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/tableview-stylesheet.css").toExternalForm());
        return homeScene;
    }

    // Initializing the variables
    private void initVariables() {
        allSelectionsController = new AllSelectionsController();

        root = new HBox();
        menubar = new MenuBar().createMenuBar();
        overviewContents = new VBox();

        allSelectionsTable = new TableView<>();
        selectionList = allSelectionsController.getAllSelections();

        buttonBox = new HBox();
        Image addLogo = new Image(getClass().getResource("/icons/add_selection.png").toExternalForm(), 25, 25, true, true);
        ImageView addLogoView = new ImageView(addLogo);
        addSelectionButton = new Button("Add Selection");
        addSelectionButton.setGraphic(addLogoView);
        Image editLogo = new Image(getClass().getResource("/icons/edit_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView editLogoView = new ImageView(editLogo);
        editSelectionButton = new Button("Edit Selection");
        editSelectionButton.setGraphic(editLogoView);
        Image deleteLogo = new Image(getClass().getResource("/icons/delete_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView deleteLogoView = new ImageView(deleteLogo);
        deleteSelectionButton = new Button("Delete Selection");
        deleteSelectionButton.setGraphic(deleteLogoView);
    }

    // Layout and Styling
    private void initLayouts() {
        buttonBox.setId("button-box");
        overviewContents.setId("overview-contents");

        overviewContents.setMinWidth(FootballSquadManager.screenSize[0] - 300);
        overviewContents.setPadding(new Insets(0,20,0,20));

        buttonBox.getChildren().addAll(addSelectionButton, editSelectionButton, deleteSelectionButton);

        overviewContents.getChildren().addAll(allSelectionsTable, buttonBox);
        root.getChildren().addAll(menubar, overviewContents);
    }

    // Initializing the table.
    private void initTable() {
        // Creates a new column, takes a string from the Selection model.
        TableColumn<Selection, String> nameCol = new TableColumn<>("Name");
        // Sets the value for the column as the getter from the Selection Model.
        nameCol.setCellValueFactory(new PropertyValueFactory<>("selectionName"));

        // Repeat for other columns.
        TableColumn<Selection, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("selectionDate"));

        TableColumn<Selection, String> userCol = new TableColumn<>("User");
        userCol.setCellValueFactory(new PropertyValueFactory<>("selectionUser"));

        TableColumn<Selection, String> formationCol = new TableColumn<>("Formation");
        formationCol.setCellValueFactory(new PropertyValueFactory<>("selectionFormation"));

        allSelectionsTable.getColumns().addAll(nameCol, dateCol, userCol, formationCol);
        // No resizing, evenly distributed.
        allSelectionsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        allSelectionsTable.setFixedCellSize(30);
        allSelectionsTable.setPlaceholder(new Label("No selections made yet!" ));

        allSelectionsTable.setItems(selectionList);
    }

    public void handleButtonActions() {
        // Button handling functions.
        addSelectionButton.setOnAction(_ -> {
            FootballSquadManager footballSquadManager = new FootballSquadManager();
            footballSquadManager.getNewSelection();
        });

        editSelectionButton.setOnAction(_ -> {
            Selection selectedSelection = allSelectionsTable.getSelectionModel().getSelectedItem();
            // Making sure the user selected a selection.
            if (selectedSelection != null) {
                // Passing the selected selection for filled in values into SelectionBuilderView. Passing 'this' so that the controller can update the table when mutating.
                allSelectionsController.editSelection(selectedSelection);
            }
        });

        // Deleting selected selection.
        deleteSelectionButton.setOnAction(_ -> {
            Selection selectedSelection = allSelectionsTable.getSelectionModel().getSelectedItem();
            if (selectedSelection != null) {
                allSelectionsController.deleteSelection(selectedSelection);
                // Refreshing the table.
                allSelectionsTable.getItems().clear();
                ObservableList<Selection> updatedPlayers = allSelectionsController.getAllSelections();
                allSelectionsTable.setItems(updatedPlayers);
            }
        });
    }
}
