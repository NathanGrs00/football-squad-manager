package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.controller.PlayerDetailsController;
import com.nathan.footballsquadmanagerbp2.controller.PositionController;
import com.nathan.footballsquadmanagerbp2.model.Captain;
import com.nathan.footballsquadmanagerbp2.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// This class is responsible for displaying the player details view, where users can add or edit player information.
public class PlayerDetailsView {
    // Private variables.
    // The AllPlayersView is used to refresh the table after adding or editing a player.
    private final AllPlayersView allPlayersView;
    // The PlayerDetailsController is used to handle the logic of adding or editing a player.
    private PlayerDetailsController playerController;
    // The stage is used to display the player details view in a different screen/stage.
    private Stage popupStage;
    // The player object is used to pass the player information to be edited.
    private final Player player;

    // The root pane is a GridPane that contains all the input fields and buttons.
    private GridPane root;
    // ColumnConstraints are used to set the width of the columns in the GridPane.
    private ColumnConstraints columnConstraints;

    // Input fields and labels for player details.
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
    // Combobox for preferred foot.
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

    // Checkbox for setting the player as captain.
    private CheckBox captainCheckBox;

    // Buttons for saving and going back.
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
        // Initializing the PlayerDetailsController.
        playerController = new PlayerDetailsController();
        // positionController is used to get the list of all positions.
        PositionController positionController = new PositionController();
        // Initializing the stage and root pane.
        popupStage = new Stage();
        root = new GridPane();
        // Setting the column constraints for the GridPane.
        columnConstraints = new ColumnConstraints();

        // First name and last name fields.
        firstNameBox = new VBox();
        firstNameTag = new Label("First Name: ");
        firstNameField = new TextField();
        lastNameTag = new Label("Last Name: ");
        lastNameBox = new VBox();
        lastNameField = new TextField();

        // Age field.
        ageBox = new VBox();
        ageTag = new Label("Age: ");
        ageField = new TextField();

        // Preferred foot field.
        prefFootBox = new VBox();
        prefFootTag = new Label("Preferred foot: ");
        // Comboboxes are filled with observableLists.
        prefFootField = new ComboBox<>();
        ObservableList<String> feet = FXCollections.observableArrayList(
                // Strings are added to the observableList.
                "Left", "Right", "Both"
        );

        // Setting the items of the combobox.
        prefFootField.setItems(feet);

        // Shirt number field.
        numberBox = new VBox();
        numberTag = new Label("Shirt number: ");
        shirtNumberField = new TextField();

        // Status field.
        statusBox = new VBox();
        statusTag = new Label("Status: ");
        statusField = new ComboBox<>();
        ObservableList<String> statuses = FXCollections.observableArrayList(
                "Available", "Not available", "Injured", "Suspended"
        );

        // Setting the items of the combobox.
        statusField.setItems(statuses);

        // Favourite position field.
        favPosBox = new VBox();
        favPositionTag = new Label("Favourite position: ");
        favPositionField = new ComboBox<>();
        ObservableList<String> favPositions = positionController.getAllPositionNames();

        // Setting the items of the combobox.
        favPositionField.setItems(favPositions);

        // Other positions field.
        otherPosBox = new VBox();
        otherPositionsTag = new Label("Other viable positions (seperated by comma): ");
        otherPositionsField = new TextField();

        // Checkbox for setting the player as captain.
        captainCheckBox = new CheckBox("Set as Captain");

        // Back button with a logo.
        Image backLogo = new Image(getClass().getResource("/icons/return_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView backLogoView = new ImageView(backLogo);
        goBackButton = new Button("Go Back");
        goBackButton.setGraphic(backLogoView);
        // Save button with a logo.
        Image saveIcon = new Image(getClass().getResource("/icons/save_icon.png").toExternalForm(),25, 25, true, true);
        ImageView saveIconView = new ImageView(saveIcon);
        saveButton = new Button("Save");
        saveButton.setGraphic(saveIconView);

        // If there is a player passed, then fill in the values of the fields.
        if (player != null) {
            // Set the values of the fields based on the player object.
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
            // Check if allPlayersView is not null before calling refresh.
            if (allPlayersView != null) {
                allPlayersView.refresh();
            }
        });
    }

    // Styling and layout.
    private void applyStyling(){
        root.setId("root-pane");
        // Max width of the columns in the GridPane.
        columnConstraints.setMaxWidth(250);
        // Adding the column constraints to the GridPane.
        root.getColumnConstraints().addAll(columnConstraints, columnConstraints);

        // Adding an image to the stage icon.
        Image clubIcon = new Image(getClass().getResource("/images/logo_fc_club_second.png").toExternalForm(), 500, 500, true, true);
        popupStage.getIcons().add(clubIcon);
        // Changing the title, based on add or edit.
        if (player == null) {
            popupStage.setTitle("Add new player");
        } else {
            popupStage.setTitle("Edit player: " + player.getPlayerFirstName() + " " + player.getPlayerLastName());
        }

        // If the player is a captain, set the checkbox to selected.
        if (player instanceof Captain) {
            captainCheckBox.setSelected(true);
        }

        // You cant resize the window.
        popupStage.setResizable(false);
        // Adding the scene to the stage.
        popupStage.setScene(getScene());
        // Showing the stage.
        popupStage.show();

        // Setting the prompt text for the input fields. PromptTexts are hints for the user.
        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        ageField.setPromptText("Age");
        prefFootField.setPromptText("L/R/B");
        shirtNumberField.setPromptText("Select number");
        statusField.setPromptText("Select status");
        favPositionField.setPromptText("Select position");
        otherPositionsField.setPromptText("ST, LW, AMC...");
        // Making sure the other positions field is not too wide.
        otherPositionsTag.setWrapText(true);

        // Add the labels and input fields to their respective VBox containers.
        firstNameBox.getChildren().addAll(firstNameTag, firstNameField);
        lastNameBox.getChildren().addAll(lastNameTag, lastNameField);
        ageBox.getChildren().addAll(ageTag, ageField);
        prefFootBox.getChildren().addAll(prefFootTag, prefFootField);
        numberBox.getChildren().addAll(numberTag, shirtNumberField);
        statusBox.getChildren().addAll(statusTag, statusField);
        favPosBox.getChildren().addAll(favPositionTag, favPositionField);
        otherPosBox.getChildren().addAll(otherPositionsTag, otherPositionsField);

        // Grid Layout.
        // First is the X coordinate, second is the Y coordinate.
        root.add(firstNameBox, 0, 0);
        root.add(lastNameBox, 1, 0);
        root.add(ageBox, 0, 1);
        root.add(prefFootBox, 1, 1);
        root.add(numberBox, 0, 2);
        root.add(statusBox, 1, 2);
        root.add(favPosBox, 0, 3);
        root.add(otherPosBox, 1, 3);
        root.add(captainCheckBox, 0, 4);
        root.add(goBackButton, 0, 5);
        root.add(saveButton, 1, 5);
    }

    // Get the scene for the stage.
    public Scene getScene() {
        // Create a new scene with the root pane and set its size.
        Scene homeScene = new Scene(root, 700, 700);
        // Set the stylesheet and the font for the scene.
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/playerdetails-stylesheet.css").toExternalForm());
        // Return the scene.
        return homeScene;
    }

    // Handling the button clicks.
    private void handleButtonClicks() {
        // Back button closes the stage.
        goBackButton.setOnAction(_ -> popupStage.close());

        // Save button validates the inputs and either inserts a new player or updates an existing player.
        saveButton.setOnAction(_ -> {
            // If the player is null, then set the id to 0, else get the playerId.
            int id = (player == null) ? 0 : player.getPlayerId();

            // Get the player object after validating inputs
            Player updatedPlayer = playerController.ValidateInputs(
                    id,
                    firstNameField,
                    lastNameField,
                    ageField,
                    prefFootField,
                    shirtNumberField,
                    statusField,
                    favPositionField,
                    otherPositionsField,
                    captainCheckBox
            );

            // If the updatedPlayer is not null, then update the player.
            if (updatedPlayer != null) {
                // Insert new player or update existing player based on playerId
                if (updatedPlayer.getPlayerId() == 0) {
                    // Insert new player
                    playerController.insertPlayer(updatedPlayer, favPositionField, otherPositionsField);
                } else {
                    // Update existing player
                    playerController.updatePlayer(updatedPlayer, favPositionField, otherPositionsField);
                }
                // Close the stage after saving.
                popupStage.close();
            }
        });
    }
}
