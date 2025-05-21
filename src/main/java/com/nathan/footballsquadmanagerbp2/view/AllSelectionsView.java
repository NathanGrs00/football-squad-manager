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

// AllSelectionsView class is responsible for displaying all selections in a table format.
public class AllSelectionsView {
    // Private variables so they can be used in all functions.
    // AllSelectionsController is used to handle the logic of the selections.
    private AllSelectionsController allSelectionsController;

    // The root layout of the view.
    private HBox root;
    private Pane menubar;
    private VBox overviewContents;

    // TableView to display the selections.
    private TableView<Selection> allSelectionsTable;
    // ObservableList to hold the selections.
    private ObservableList<Selection> selectionList;

    // Buttons for adding, editing, and deleting selections.
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
        // Setting the scene with the root layout and the size of the screen.
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        // Getting the font and stylesheet.
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/tableview-stylesheet.css").toExternalForm());
        // Returning the scene.
        return homeScene;
    }

    // Initializing the variables
    private void initVariables() {
        // Initializing the AllSelectionsController
        allSelectionsController = new AllSelectionsController();

        // Initializing the root layout and other components.
        root = new HBox();
        menubar = new MenuBar().createMenuBar();
        overviewContents = new VBox();

        // Initializing the TableView and ObservableList.
        allSelectionsTable = new TableView<>();
        selectionList = allSelectionsController.getAllSelections();

        // Initializing the buttons and their icons.
        buttonBox = new HBox();
        // Add Selection button
        Image addLogo = new Image(getClass().getResource("/icons/add_selection.png").toExternalForm(), 25, 25, true, true);
        ImageView addLogoView = new ImageView(addLogo);
        addSelectionButton = new Button("Add Selection");
        addSelectionButton.setGraphic(addLogoView);
        // Edit Selection button
        Image editLogo = new Image(getClass().getResource("/icons/edit_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView editLogoView = new ImageView(editLogo);
        editSelectionButton = new Button("Edit Selection");
        editSelectionButton.setGraphic(editLogoView);
        // Delete Selection button
        Image deleteLogo = new Image(getClass().getResource("/icons/delete_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView deleteLogoView = new ImageView(deleteLogo);
        deleteSelectionButton = new Button("Delete Selection");
        deleteSelectionButton.setGraphic(deleteLogoView);
    }

    // Layout and Styling
    private void initLayouts() {
        buttonBox.setId("button-box");
        overviewContents.setId("overview-contents");

        // Setting the minimum width of the overview contents to be the screen size minus 300 pixels.
        overviewContents.setMinWidth(FootballSquadManager.screenSize[0] - 300);
        // Also adding padding to the overview contents.
        overviewContents.setPadding(new Insets(0,20,0,20));

        // Adding the buttons to the button box.
        buttonBox.getChildren().addAll(addSelectionButton, editSelectionButton, deleteSelectionButton);

        // Adding the buttonbox and the table to the overview contents.
        overviewContents.getChildren().addAll(allSelectionsTable, buttonBox);
        // Adding the menubar and the overview contents to the root layout.
        root.getChildren().addAll(menubar, overviewContents);
    }

    // Initializing the table.
    private void initTable() {
        // Creates a new column, takes a string from the Selection model.
        TableColumn<Selection, String> nameCol = new TableColumn<>("Name");
        // Sets the value for the column as the getter from the Selection Model.
        nameCol.setCellValueFactory(new PropertyValueFactory<>("selectionName"));

        // Column for the date of the selection.
        TableColumn<Selection, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("selectionDate"));

        // Column for the user who made the selection.
        TableColumn<Selection, String> userCol = new TableColumn<>("User");
        userCol.setCellValueFactory(new PropertyValueFactory<>("selectionUser"));

        // Column for the formation of the selection.
        TableColumn<Selection, String> formationCol = new TableColumn<>("Formation");
        formationCol.setCellValueFactory(new PropertyValueFactory<>("selectionFormation"));

        // Adding the columns to the table.
        allSelectionsTable.getColumns().addAll(nameCol, dateCol, userCol, formationCol);
        // No resizing, evenly distributed.
        allSelectionsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        allSelectionsTable.setFixedCellSize(30);
        // setPlaceholder is used to set a message when the table is empty.
        allSelectionsTable.setPlaceholder(new Label("No selections made yet!" ));

        // setItems is used to set the items in the table.
        allSelectionsTable.setItems(selectionList);
    }

    // Function to handle button actions.
    public void handleButtonActions() {
        // If the add selection button is clicked,
        addSelectionButton.setOnAction(_ -> {
            // Creating a new instance of FootballSquadManager to get a new selection.
            FootballSquadManager footballSquadManager = new FootballSquadManager();
            // Send the user to the SelectionBuilderView.
            footballSquadManager.getNewSelection();
        });

        // If the edit selection button is clicked,
        editSelectionButton.setOnAction(_ -> {
            // Getting the selected selection from the table.
            Selection selectedSelection = allSelectionsTable.getSelectionModel().getSelectedItem();
            // Making sure the user selected a selection.
            if (selectedSelection != null) {
                // Passing the selected selection for filled in values into SelectionBuilderView.
                allSelectionsController.editSelection(selectedSelection);
            }
        });

        // Deleting selected selection.
        deleteSelectionButton.setOnAction(_ -> {
            // Getting the selected selection from the table.
            Selection selectedSelection = allSelectionsTable.getSelectionModel().getSelectedItem();
            // Making sure the user selected a selection.
            if (selectedSelection != null) {
                // Deleting the selected selection.
                allSelectionsController.deleteSelection(selectedSelection);
                // Refreshing the table.
                allSelectionsTable.getItems().clear();
                // Getting the updated list of selections from the controller.
                ObservableList<Selection> updatedPlayers = allSelectionsController.getAllSelections();
                // Setting the updated list of selections to the table.
                allSelectionsTable.setItems(updatedPlayers);
            }
        });
    }
}
