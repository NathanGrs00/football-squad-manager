package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.AllPlayersController;
import com.nathan.footballsquadmanagerbp2.controller.PlayerDetailsController;
import com.nathan.footballsquadmanagerbp2.model.Player;
import javafx.beans.property.SimpleStringProperty;
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

// This class is responsible for displaying the overview of all players in the squad.
public class AllPlayersView {
    // Controller classes, which handle the logic of the view.
    // Private variables, so that they are not accessible outside of this class.
    private AllPlayersController allPlayersController;
    private PlayerDetailsController playerDetailsController;

    // Layout variables.
    // HBox is a horizontal box, VBox is a vertical box.
    private HBox root;
    // Pane is a generic layout container.
    private Pane menubar;
    private VBox overviewContents;

    // TableView is the component that displays the players in a table format.
    private TableView<Player> allPlayersTable;
    // The table is populated with an ObservableList of Player objects.
    private ObservableList<Player> playerList;

    // The buttons for adding, editing and deleting players.
    private HBox buttonBox;
    private Button addPlayerButton;
    private Button editPlayerButton;
    private Button deletePlayerButton;

    // Constructor to populate the scene.
    public AllPlayersView() {
        initVariables();
        initLayouts();
        initTable();
        handleButtonActions();
    }

    // Returns the scene for the view.
    public Scene getScene() {
        // Creating the scene with the root layout and setting the size.
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        // Getting the font
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        // Adding the stylesheet for the table.
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/tableview-stylesheet.css").toExternalForm());
        // returning the scene.
        return homeScene;
    }

    // Initializing the variables
    private void initVariables() {
        // Initializing the controller classes.
        allPlayersController = new AllPlayersController();
        playerDetailsController = new PlayerDetailsController();

        // Initializing the layout variables.
        root = new HBox();
        // Getting the menu bar from the MenuBar class.
        menubar = new MenuBar().createMenuBar();
        overviewContents = new VBox();

        // Initializing the table and the player list.
        allPlayersTable = new TableView<>();
        // Getting all players from the controller.
        playerList = allPlayersController.getAllPlayers();

        // Initializing the buttons.
        buttonBox = new HBox();
        // Each button has an icon, as well as text.
        // Add Player button
        Image addLogo = new Image(getClass().getResource("/icons/add_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView addLogoImageView = new ImageView(addLogo);
        addPlayerButton = new Button("Add Player");
        addPlayerButton.setGraphic(addLogoImageView);
        // Edit Player button
        Image editLogo = new Image(getClass().getResource("/icons/edit_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView editLogoView = new ImageView(editLogo);
        editPlayerButton = new Button("Edit Player");
        editPlayerButton.setGraphic(editLogoView);
        // Delete Player button
        Image deleteLogo = new Image(getClass().getResource("/icons/delete_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView deleteLogoView = new ImageView(deleteLogo);
        deletePlayerButton = new Button("Delete Player");
        deletePlayerButton.setGraphic(deleteLogoView);
    }

    // Layout and Styling
    private void initLayouts() {
        // Adding an id to the buttonBox and overviewContents layout for styling.
        buttonBox.setId("button-box");
        overviewContents.setId("overview-contents");

        // Setting the minimum width of the overview contents to be the screen size minus 300 pixels.
        overviewContents.setMinWidth(FootballSquadManager.screenSize[0] - 300);
        // Also add padding to the overview contents.
        overviewContents.setPadding(new Insets(0,20,0,20));

        // Adding the buttons to the button box.
        buttonBox.getChildren().addAll(addPlayerButton, editPlayerButton, deletePlayerButton);

        // Adding the table and the button box to the overview contents.
        overviewContents.getChildren().addAll(allPlayersTable, buttonBox);
        // Adding the menu bar and the overview contents to the root layout.
        root.getChildren().addAll(menubar, overviewContents);
    }

    // Initializing the table.
    private void initTable() {
        // Creates a new column, takes an integer from the Player model.
        TableColumn<Player, Integer> numberCol = new TableColumn<>("Number");
        // Sets the value for the column as the getter from the Player Model.
        numberCol.setCellValueFactory(new PropertyValueFactory<>("playerShirtNumber"));

        // Another column, this time for the first name.
        TableColumn<Player, String> firstNameCol = new TableColumn<>("First name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("playerFirstName"));

        // Another column, this time for the last name.
        TableColumn<Player, String> lastNameCol = new TableColumn<>("Last name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("playerLastName"));

        // Another column, this time for the favourite position.
        TableColumn<Player, String> favPosCol = new TableColumn<>("Favourite Position");
        favPosCol.setCellValueFactory(cellData -> {
            // Retrieves the player object from the row.
            Player player = cellData.getValue();
            // Retrieve the favorite position from the database.
            String favPos = playerDetailsController.getFavPosColumn(player.getPlayerId());
            // Wrapping the string in a SimpleStringProperty so that it is observable.
            return new SimpleStringProperty(favPos);
        });

        // Another column, this time for the age.
        TableColumn<Player, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("playerAge"));

        // Another column, this time for the preferred foot.
        TableColumn<Player, String> footCol = new TableColumn<>("Foot");
        footCol.setCellValueFactory(new PropertyValueFactory<>("playerPrefFoot"));

        // Another column, this time for the status.
        TableColumn<Player, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("playerStatus"));

        // Adding the columns to the table.
        allPlayersTable.getColumns().addAll(numberCol, firstNameCol, lastNameCol, favPosCol, ageCol, footCol, statusCol);
        // No resizing, evenly distributed.
        allPlayersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        allPlayersTable.setFixedCellSize(30);
        // setPlaceholder is used to set a message when the table is empty.
        allPlayersTable.setPlaceholder(new Label("No players added yet!" ));

        // Set the list of players to the table.
        allPlayersTable.setItems(playerList);
    }

    // This function handles the button actions.
    public void handleButtonActions() {
        //Passing 'this' so that the PlayerDetailsView can update the table when mutating.
        addPlayerButton.setOnAction(_ -> allPlayersController.addPlayer(this));

        // If the user clicks on the edit button,
        editPlayerButton.setOnAction(_ -> {
            // Get the selected player from the table.
            Player selectedPlayer = allPlayersTable.getSelectionModel().getSelectedItem();
            // Making sure the user selected a player
            if (selectedPlayer != null) {
                // Passing the selected player for filled in values in the PlayerDetailsView.
                allPlayersController.editPlayer(selectedPlayer, this);
            }
        });

        // If the user clicks on the delete button,
        deletePlayerButton.setOnAction(_ -> {
            // Get the selected player from the table.
            Player selectedPlayer = allPlayersTable.getSelectionModel().getSelectedItem();
            // Making sure the user selected a player
            if (selectedPlayer != null) {
                // Call the deletePlayer method from the controller.
                allPlayersController.deletePlayer(selectedPlayer);
                // Refresh the table to show the updated list of players.
                refresh();
            }
        });
    }

    // Refreshing the table function.
    public void refresh() {
        // Clear the table first
        allPlayersTable.getItems().clear();
        // Get the updated list of players from the controller.
        ObservableList<Player> updatedPlayers = allPlayersController.getAllPlayers();
        // Set the updated list of players to the table.
        allPlayersTable.setItems(updatedPlayers);
    }
}
