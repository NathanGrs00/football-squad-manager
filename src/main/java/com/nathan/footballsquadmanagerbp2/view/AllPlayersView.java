package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.AllPlayersController;
import com.nathan.footballsquadmanagerbp2.model.Player;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AllPlayersView {
    private AllPlayersController allPlayersController;

    private HBox root;
    private Pane menubar;
    private VBox overviewContents;

    private TableView<Player> allPlayersTable;
    private ObservableList<Player> playerList;

    private HBox buttonBox;
    private Button addPlayerButton;
    private Button editPlayerButton;
    private Button deletePlayerButton;

    public AllPlayersView() {
        initLayout();
        applyStyling();
        initTable();
        handleButtonActions();
    }

    public Scene getScene() {
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/players-stylesheet.css").toExternalForm());
        return homeScene;
    }

    private void initLayout() {
        allPlayersController = new AllPlayersController();

        root = new HBox();
        menubar = new MenuBar().createMenuBar();
        overviewContents = new VBox();

        allPlayersTable = new TableView<>();
        playerList = allPlayersController.getAllPlayers();

        buttonBox = new HBox();
        addPlayerButton = new Button("Add Player");
        editPlayerButton = new Button("Edit Player");
        deletePlayerButton = new Button("Delete Player");
    }

    private void applyStyling() {
        buttonBox.setId("button-box");
        overviewContents.setId("overview-contents");

        overviewContents.setMinWidth(FootballSquadManager.screenSize[0] - 300);
        overviewContents.setPadding(new Insets(0,20,0,20));

        buttonBox.getChildren().addAll(addPlayerButton, editPlayerButton, deletePlayerButton);

        overviewContents.getChildren().addAll(allPlayersTable, buttonBox);
        root.getChildren().addAll(menubar, overviewContents);
    }

    private void initTable() {
        TableColumn<Player, Integer> numberCol = new TableColumn<>("Number");
        numberCol.setCellValueFactory(new PropertyValueFactory<>("playerShirtNumber"));

        TableColumn<Player, String> firstNameCol = new TableColumn<>("Last name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("playerFirstName"));

        TableColumn<Player, String> lastNameCol = new TableColumn<>("Last name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("playerLastName"));

        TableColumn<Player, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("playerAge"));

        TableColumn<Player, String> footCol = new TableColumn<>("Foot");
        footCol.setCellValueFactory(new PropertyValueFactory<>("playerPrefFoot"));

        TableColumn<Player, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("playerStatus"));

        allPlayersTable.getColumns().addAll(numberCol, firstNameCol, lastNameCol, ageCol, footCol, statusCol);
        allPlayersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        allPlayersTable.setFixedCellSize(30);
        allPlayersTable.setPlaceholder(new Label("No players! if you are not seeing your changes, please click 'PLAYERS' in the left menu." ));

        // TODO: ObservableList for changes.
        allPlayersTable.setItems(playerList);
    }

    public void handleButtonActions() {
        addPlayerButton.setOnAction(_ -> allPlayersController.addPlayer(this));

        editPlayerButton.setOnAction(_ -> {
            Player selectedPlayer = allPlayersTable.getSelectionModel().getSelectedItem();
            if (selectedPlayer != null) {
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

    public void refresh() {
        allPlayersTable.getItems().clear();
        ObservableList<Player> updatedPlayers = allPlayersController.getAllPlayers();
        allPlayersTable.setItems(updatedPlayers);
    }
}
