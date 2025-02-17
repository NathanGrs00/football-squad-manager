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

public class AllPlayersView {
    // Private variables so they can be used in all functions.
    private AllPlayersController allPlayersController;
    private PlayerDetailsController playerDetailsController;

    private HBox root;
    private Pane menubar;
    private VBox overviewContents;

    private TableView<Player> allPlayersTable;
    private ObservableList<Player> playerList;

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
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/players-stylesheet.css").toExternalForm());
        return homeScene;
    }

    // Initializing the variables
    private void initVariables() {
        allPlayersController = new AllPlayersController();
        playerDetailsController = new PlayerDetailsController();

        root = new HBox();
        menubar = new MenuBar().createMenuBar();
        overviewContents = new VBox();

        allPlayersTable = new TableView<>();
        playerList = allPlayersController.getAllPlayers();

        buttonBox = new HBox();
        Image addLogo = new Image(getClass().getResource("/icons/add_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView addLogoImageView = new ImageView(addLogo);
        addPlayerButton = new Button("Add Player");
        addPlayerButton.setGraphic(addLogoImageView);
        Image editLogo = new Image(getClass().getResource("/icons/edit_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView editLogoView = new ImageView(editLogo);
        editPlayerButton = new Button("Edit Player");
        editPlayerButton.setGraphic(editLogoView);
        Image deleteLogo = new Image(getClass().getResource("/icons/delete_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView deleteLogoView = new ImageView(deleteLogo);
        deletePlayerButton = new Button("Delete Player");
        deletePlayerButton.setGraphic(deleteLogoView);
    }

    // Layout and Styling
    private void initLayouts() {
        buttonBox.setId("button-box");
        overviewContents.setId("overview-contents");

        overviewContents.setMinWidth(FootballSquadManager.screenSize[0] - 300);
        overviewContents.setPadding(new Insets(0,20,0,20));

        buttonBox.getChildren().addAll(addPlayerButton, editPlayerButton, deletePlayerButton);

        overviewContents.getChildren().addAll(allPlayersTable, buttonBox);
        root.getChildren().addAll(menubar, overviewContents);
    }

    // Initializing the table.
    private void initTable() {
        // Creates a new column, takes an integer from the Player model.
        TableColumn<Player, Integer> numberCol = new TableColumn<>("Number");
        // Sets the value for the column as the getter from the Player Model.
        numberCol.setCellValueFactory(new PropertyValueFactory<>("playerShirtNumber"));

        // Repeat for other columns.
        TableColumn<Player, String> firstNameCol = new TableColumn<>("First name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("playerFirstName"));

        TableColumn<Player, String> lastNameCol = new TableColumn<>("Last name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("playerLastName"));

        TableColumn<Player, String> favPosCol = new TableColumn<>("Favourite Position");
        favPosCol.setCellValueFactory(cellData -> {
            // Retrieves the player object from the row.
            Player player = cellData.getValue();
            // Retrieve the favorite position from the database.
            String favPos = playerDetailsController.getFavPosColumn(player.getPlayerId());
            // Wrapping the string in a SimpleStringProperty so that it is observable.
            return new SimpleStringProperty(favPos);
        });

        TableColumn<Player, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("playerAge"));

        TableColumn<Player, String> footCol = new TableColumn<>("Foot");
        footCol.setCellValueFactory(new PropertyValueFactory<>("playerPrefFoot"));

        TableColumn<Player, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("playerStatus"));

        allPlayersTable.getColumns().addAll(numberCol, firstNameCol, lastNameCol, favPosCol, ageCol, footCol, statusCol);
        // No resizing, evenly distributed.
        allPlayersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        allPlayersTable.setFixedCellSize(30);
        allPlayersTable.setPlaceholder(new Label("No players added yet!" ));

        allPlayersTable.setItems(playerList);
    }

    public void handleButtonActions() {
        // Button handling functions. Passing 'this' so that the PlayerDetailsView can update the table when mutating.
        addPlayerButton.setOnAction(_ -> allPlayersController.addPlayer(this));

        editPlayerButton.setOnAction(_ -> {
            Player selectedPlayer = allPlayersTable.getSelectionModel().getSelectedItem();
            // Making sure the user selected a player
            if (selectedPlayer != null) {
                // Passing the selected player for filled in values in the PlayerDetailsView.
                allPlayersController.editPlayer(selectedPlayer, this);
            }
        });

        deletePlayerButton.setOnAction(_ -> {
            Player selectedPlayer = allPlayersTable.getSelectionModel().getSelectedItem();
            if (selectedPlayer != null) {
                allPlayersController.deletePlayer(selectedPlayer);
                refresh();
            }
        });
    }

    // Refreshing the table function.
    public void refresh() {
        allPlayersTable.getItems().clear();
        ObservableList<Player> updatedPlayers = allPlayersController.getAllPlayers();
        allPlayersTable.setItems(updatedPlayers);
    }
}
