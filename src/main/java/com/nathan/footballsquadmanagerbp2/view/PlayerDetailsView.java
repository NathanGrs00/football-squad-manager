package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.controller.PlayerDetailsController;
import com.nathan.footballsquadmanagerbp2.controller.PositionController;
import com.nathan.footballsquadmanagerbp2.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayerDetailsView {
    // Private variables.
    private final AllPlayersView allPlayersView;
    private PlayerDetailsController playerController;
    private Stage popupStage;
    private final Player player;

    private GridPane root;
    private ColumnConstraints columnConstraints;

    private VBox firstNameBox;
    private Label firstNameTag;
    private TextField firstNameField;
    private VBox lastNameBox;
    private Label lastNameTag;
    private TextField lastNameField;

    private VBox ageBox;
    private Label ageTag;
    private TextField ageField;

    private VBox prefFootBox;
    private Label prefFootTag;
    private ComboBox<String> prefFootField;

    private VBox numberBox;
    private Label numberTag;
    private TextField shirtNumberField;

    private VBox statusBox;
    private Label statusTag;
    private ComboBox<String> statusField;

    private VBox favPosBox;
    private Label favPositionTag;
    private ComboBox<String> favPositionField;

    private VBox otherPosBox;
    private Label otherPositionsTag;
    private TextField otherPositionsField;

    private Button goBackButton;
    private Button saveButton;

    // Constructor to populate scene and pass the Player to fill data and allPlayersView for changing the table.
    public PlayerDetailsView(Player player, AllPlayersView allPlayersView) {
        this.allPlayersView = allPlayersView;
        this.player = player;
        initLayouts();
        applyStyling();
        handleButtonClicks();
    }

    // Initializing the variables.
    private void initLayouts(){
        playerController = new PlayerDetailsController();

        PositionController positionController = new PositionController();
        popupStage = new Stage();
        root = new GridPane();
        columnConstraints = new ColumnConstraints();

        firstNameBox = new VBox();
        firstNameTag = new Label("First Name: ");
        firstNameField = new TextField();
        lastNameTag = new Label("Last Name: ");
        lastNameBox = new VBox();
        lastNameField = new TextField();

        ageBox = new VBox();
        ageTag = new Label("Age: ");
        ageField = new TextField();

        prefFootBox = new VBox();
        prefFootTag = new Label("Preferred foot: ");
        // Comboboxes are filled with observableLists.
        prefFootField = new ComboBox<>();
        ObservableList<String> feet = FXCollections.observableArrayList(
                "Left", "Right", "Both"
        );

        // TODO: handle unchecked calls.
        prefFootField.setItems(feet);

        numberBox = new VBox();
        numberTag = new Label("Shirt number: ");
        shirtNumberField = new TextField();

        statusBox = new VBox();
        statusTag = new Label("Status: ");
        statusField = new ComboBox<>();
        ObservableList<String> statuses = FXCollections.observableArrayList(
                "Available", "Not available", "Injured", "Suspended"
        );

        statusField.setItems(statuses);

        favPosBox = new VBox();
        favPositionTag = new Label("Favourite position: ");
        favPositionField = new ComboBox<>();
        ObservableList<String> favPositions = positionController.getAllPositionNames();

        favPositionField.setItems(favPositions);

        otherPosBox = new VBox();
        otherPositionsTag = new Label("Other viable positions (seperated by comma): ");
        otherPositionsField = new TextField();

        goBackButton = new Button("Go Back");
        saveButton = new Button("Save");

        // If there is a player passed, then fill in the values of the fields.
        if (player != null) {
            firstNameField.setText(player.getPlayerFirstName());
            lastNameField.setText(player.getPlayerLastName());
            prefFootField.setValue(player.getPlayerPrefFoot());
            ageField.setText(String.valueOf(player.getPlayerAge()));
            favPositionField.setValue(playerController.getFavPosColumn(player.getPlayerId()));
            otherPositionsField.setText(playerController.getOtherPosColumn(player.getPlayerId()));
            shirtNumberField.setText(String.valueOf(player.getPlayerShirtNumber()));
            statusField.setValue(player.getPlayerStatus());
        }

        // When window closes, refresh the table.
        popupStage.setOnHidden(_ -> {
            if (allPlayersView != null) {
                allPlayersView.refresh();
            }
        });
    }

    // Styling and layout.
    private void applyStyling(){
        root.setId("root-pane");
        columnConstraints.setMaxWidth(250);
        root.getColumnConstraints().addAll(columnConstraints, columnConstraints);

        Image clubIcon = new Image(getClass().getResource("/images/logo_fc_club_second.png").toExternalForm(), 500, 500, true, true);
        popupStage.getIcons().add(clubIcon);
        // Changing the title, based on add or edit.
        if (player == null) {
            popupStage.setTitle("Add new player");
        } else {
            popupStage.setTitle("Edit player: " + player.getPlayerFirstName() + " " + player.getPlayerLastName());
        }

        popupStage.setResizable(false);
        popupStage.setScene(getScene());
        popupStage.show();

        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        ageField.setPromptText("Age");
        prefFootField.setPromptText("L/R/B");
        shirtNumberField.setPromptText("Select number");
        statusField.setPromptText("Select status");
        favPositionField.setPromptText("Select position");
        otherPositionsField.setPromptText("ST, LW, CAM");
        otherPositionsTag.setWrapText(true);

        firstNameBox.getChildren().addAll(firstNameTag, firstNameField);
        lastNameBox.getChildren().addAll(lastNameTag, lastNameField);
        ageBox.getChildren().addAll(ageTag, ageField);
        prefFootBox.getChildren().addAll(prefFootTag, prefFootField);
        numberBox.getChildren().addAll(numberTag, shirtNumberField);
        statusBox.getChildren().addAll(statusTag, statusField);
        favPosBox.getChildren().addAll(favPositionTag, favPositionField);
        otherPosBox.getChildren().addAll(otherPositionsTag, otherPositionsField);

        // Grid Layout.
        root.add(firstNameBox, 0, 0);
        root.add(lastNameBox, 1, 0);
        root.add(ageBox, 0, 1);
        root.add(prefFootBox, 1, 1);
        root.add(numberBox, 0, 2);
        root.add(statusBox, 1, 2);
        root.add(favPosBox, 0, 3);
        root.add(otherPosBox, 1, 3);
        root.add(goBackButton, 0, 5);
        root.add(saveButton, 1, 5);
    }

    public Scene getScene() {
        Scene homeScene = new Scene(root, 650, 700);
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/playerdetails-stylesheet.css").toExternalForm());
        return homeScene;
    }

    // Handling the button clicks.
    private void handleButtonClicks(){
        goBackButton.setOnAction(_ -> popupStage.close());
        saveButton.setOnAction(_ -> {
            int id;
            // Passing the player, used in the player_position table.
            if (player == null) {
                id = 0;
            } else {
                id = player.getPlayerId();
            }
            // If changes have been made successfully, close the stage.
            boolean isValid = playerController.ValidateAndSave(id, firstNameField, lastNameField, ageField, prefFootField, shirtNumberField, statusField, favPositionField, otherPositionsField);
            if (isValid) {
                popupStage.close();
            }
        });
    }
}
