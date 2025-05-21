package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.PlayerDetailsController;
import com.nathan.footballsquadmanagerbp2.controller.PositionController;
import com.nathan.footballsquadmanagerbp2.controller.SelectionBuilderController;
import com.nathan.footballsquadmanagerbp2.model.*;
import com.nathan.footballsquadmanagerbp2.service.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.*;

// This class is responsible for creating the view of the selection builder screen.
public class SelectionBuilderView {
    // Controller and service classes
    private SelectionBuilderController controller;
    private SelectionDetailsService selectionDetailsService;
    private PositionController positionController;
    private AlertService alertService;
    // Selection and selection details
    private final Selection selection;
    private final List<SelectionDetail> selectionDetails;
    // Set of selected players, set means that there are no duplicates.
    private Set<Player> selectedPlayers;
    // Map of Vbox and position, so that the clear all button can function.
    private Map<VBox, Position> positionButtonMap;
    // root and grid layout
    private HBox root;
    private GridPane grid;

    // Constructor
    public SelectionBuilderView(Selection selectionPassed, List<SelectionDetail> detailsPassed) {
        this.selectionDetails = detailsPassed;
        this.selection = selectionPassed;
        initVariables();
        initLayouts();
    }

    // This method is used to get the scene of the selection builder.
    public Scene getScene() {
        // Creating the scene with the root layout and setting the size.
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        // Setting the stylesheet for the scene.
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/new-selection-stylesheet.css").toExternalForm());
        // returning the scene.
        return homeScene;
    }

    // This method is used to initialize the variables.
    private void initVariables() {
        // Initializing the controller and service classes.
        controller = new SelectionBuilderController();
        selectionDetailsService = new SelectionDetailsService();
        positionController = new PositionController();
        alertService = new AlertService();
        // Initializing the root and grid layout.
        root = new HBox();
        grid = new GridPane();
        // HashSet is used to store the selected players, so that there are no duplicates. HashSet is faster
        selectedPlayers = new HashSet<>();
        // Initializing the positionButtonMap, which is used to store the Vbox and position.
        positionButtonMap = new HashMap<>();
    }

    // This method is used to initialize the layouts.
    private void initLayouts() {
        root.setId("root-pane");

        // Create the menu bar and set its ID.
        Pane menuBar = new MenuBar().createMenuBar();
        menuBar.setId("menubar");

        // Create the title and formation tags.
        Label titleTag = new Label("Selection name: " + selection.getSelectionName());
        titleTag.setId("title-tag");

        Label formationTag = new Label("Formation: " + selection.getSelectionFormation().getFormationName());
        formationTag.setId("formation-tag");

        // Create the selection details box and set its ID.
        HBox selectionDetailsBox = new HBox(titleTag, formationTag);
        selectionDetailsBox.setId("selection-details");

        // Create the selection details table and set its ID.
        VBox selectionBox = new VBox(selectionDetailsBox, initPitchGrid(), createRemoveButton());
        selectionBox.setAlignment(Pos.CENTER);

        // Adding the selectionBox and menuBar to the root layout.
        root.getChildren().addAll(menuBar, selectionBox);
    }

    // Making the pitch and grid
    private StackPane initPitchGrid() {
        // Background image of the pitch.
        ImageView pitchImageView = new ImageView(new Image(getClass().getResource("/images/background_pitch.png").toExternalForm()));
        pitchImageView.setFitWidth(1300);
        pitchImageView.setFitHeight(1100);
        // Makes sure that the image is not distorted.
        pitchImageView.setPreserveRatio(true);

        grid.setId("grid-pane");

        // Set up all the positions on the pitch.
        setupPositionButtons();

        // StackPane is used to stack the pitch image and the grid on top of each other.
        return new StackPane(pitchImageView, grid);
    }

    // This method is used to set up the position buttons on the pitch.
    private void setupPositionButtons() {
        // All players that are available for selection.
        List<Player> players = controller.getAvailablePlayers();

        // Getting the positions from the selected formation.
        List<Position> positionsFromFormation = positionController.getPositionsList(selection.getSelectionFormation().getFormationId());

        // Getting a map of existing players in selection.
        Map<Integer, Player> positionPlayerMap = controller.getPlayerPositionMap(selectionDetails);

        // For each position in the selection, make an empty playerCard.
        for (Position pos : positionsFromFormation) {
            // Creating a placeholder label for the position.
            Label placeholder = new Label(pos.getPositionAbreviation());
            placeholder.setId("placeholder");
            // Creating a VBox for the playerCard.
            VBox playerCard = new VBox(placeholder);

            // If the position is in the Map of existing players, create the playerCard with the player.
            if (positionPlayerMap.containsKey(pos.getPositionId())) {
                // Get the player from the map.
                Player preselectedPlayer = positionPlayerMap.get(pos.getPositionId());
                // Remove the placeholder label and add the playerCard.
                playerCard.getChildren().remove(placeholder);
                playerCard = createPlayerCard(preselectedPlayer);
                playerCard.setMinWidth(115);
                // UserData is used to store the player in the Vbox.
                playerCard.setUserData(preselectedPlayer);
                // Opacity 1 means that the playerCard is visible.
                playerCard.setStyle("-fx-opacity: 1;");
                // Change the background color if it's a captain.
                if (preselectedPlayer instanceof Captain) {
                    playerCard.setStyle("-fx-background-color: #796612; -fx-opacity: 1;");
                }
            }

            //Making the playerCard final, because lambda notation needs the card to be final.
            VBox finalPlayerCard = playerCard;

            // Clicking on the Vbox, triggers a method.
            playerCard.setOnMouseClicked(_ -> handlePositionSelection(finalPlayerCard, pos, players));
            // Add a class to the playerCard for styling.
            playerCard.getStyleClass().add("opacity-button");

            // Adding the playerCard to the grid based on the coordinates.
            grid.add(playerCard, pos.getXPosition(), pos.getYPosition());
            // Adding the VBox and position to a Map, so that the clear all button can function.
            positionButtonMap.put(playerCard, pos);
        }
    }

    // Handling when the user clicks a Vbox.
    private void handlePositionSelection(VBox playerInfo, Position pos, List<Player> players) {
        // Making a previousPlayer so that you can select the old player after overwriting the spot.
        Player previousPlayer = (Player) playerInfo.getUserData();

        // Gets the players that are not selected yet.
        Map<Player, Integer> availablePlayers = controller.getProficientPlayers(pos.getPositionId(), players, selectedPlayers);

        // Give an alert if there are no suitable players.
        if (availablePlayers.isEmpty()) {
            alertService.getAlert("No players available!");
            return;
        }

        // Maps the display message in the dialogue to the player.
        Map<String, Player> displayToPlayerMap = new HashMap<>();
        // Maps the display message to the proficiency. (this is for sorting)
        Map<String, Integer> displayToProficiencyMap = new HashMap<>();
        // List of all player options for a position.
        List<String> playerOptions = new ArrayList<>();

        // For all available players, make a displayText with the playerInfo.
        for (Map.Entry<Player, Integer> entry : availablePlayers.entrySet()) {
            Player player = entry.getKey();
            int proficiency = entry.getValue();
            String displayText = player.getPlayerFirstName() + " " + player.getPlayerLastName() + " (Proficiency: " + proficiency + ")";
            // Store the displayText with the player.
            displayToPlayerMap.put(displayText, player);
            // Store the displayText with the proficiency.
            displayToProficiencyMap.put(displayText, proficiency);
            // Add the displayText as a pickable option.
            playerOptions.add(displayText);
        }

        // Sorting by proficiency, highest first. It compares all the elements of playerOptions and sorts them based on a and b values.
        playerOptions.sort((a, b) -> {
            // Lambda expression is comparing 2 values.
            int proficiencyA = displayToProficiencyMap.get(a);
            int proficiencyB = displayToProficiencyMap.get(b);
            // Makes sure that the highest integer comes first.
            return Integer.compare(proficiencyB, proficiencyA);
        });

        // If there are no available player options, stop execution.
        if (playerOptions.isEmpty()) {
            return;
        }

        // Making the dialogueBox. First parameter is the default preselected value, second the choices.
        ChoiceDialog<String> dialogue = new ChoiceDialog<>(playerOptions.getFirst(), playerOptions);
        dialogue.setTitle("Select a player");
        dialogue.setHeaderText("Choose a player for " + pos.getPositionAbreviation());
        dialogue.setContentText("Player:");

        // Result is the option that gets selected.
        Optional<String> result = dialogue.showAndWait();
        result.ifPresent(selectedText -> {
            // Gets the player from the selectedText string.
            Player selectedPlayer = displayToPlayerMap.get(selectedText);

            // Ask controller to update selection.
            boolean updated = controller.updateSelection(selection.getSelectionId(), previousPlayer, selectedPlayer, pos.getPositionId(), selectedPlayers);

            if (updated) {
                playerInfo.getChildren().clear();
                playerInfo.getChildren().add(createPlayerCard(selectedPlayer));
                playerInfo.setUserData(selectedPlayer);
                playerInfo.setStyle("-fx-opacity: 1;");
            } else {
                alertService.getAlert("Player already selected!");
            }
        });
    }

    // Button to remove all position player links.
    private Button createRemoveButton() {
        // Create a button to remove all players from the selection.
        Button clearAllButton = new Button("Clear all");
        clearAllButton.setId("clear-all-button");

        // Clearing all positions.
        clearAllButton.setOnAction(_ -> {
            // For each playerCard in the positionButtonMap, clear the children and add the placeholder back.
            positionButtonMap.forEach((playerCard, pos) -> {
                playerCard.getChildren().clear();
                Label placeholder = new Label(pos.getPositionAbreviation());
                placeholder.setId("placeholder");
                playerCard.getChildren().add(placeholder);
                playerCard.setMinWidth(115);
                // Set the opacity to 0.7, so that it looks like an empty slot.
                playerCard.setStyle("-fx-opacity: .7;");
                playerCard.setUserData(null);
            });
            // Clear the selected players and remove all entries from the selection details.
            selectedPlayers.clear();
            selectionDetailsService.removeAllEntries(selection.getSelectionId());
        });
        // Return the button.
        return clearAllButton;
    }

    // Individual playerCard.
    private VBox createPlayerCard(Player player) {
        // Icon.
        Image playerIconPath = new Image(getClass().getResource("/icons/user_icon.png").toExternalForm());
        ImageView playerIcon = new ImageView(playerIconPath);
        // Setting the size of the icon.
        playerIcon.setFitWidth(30);
        playerIcon.setFitHeight(30);

        // Player number.
        Label playerNumber = new Label(String.valueOf(player.getPlayerShirtNumber()));
        playerNumber.setId("player-number");

        // HBox for the icon and number.
        HBox playerInfoTop = new HBox(playerIcon, playerNumber);
        playerInfoTop.setSpacing(50);
        playerInfoTop.setAlignment(Pos.CENTER);

        // Player Name.
        String firstLetter = player.getPlayerFirstName().substring(0, 1);
        Label playerInfoName = new Label(firstLetter + ". " + player.getPlayerLastName());
        playerInfoName.setId("player-info-name");

        // Other viable positions list.
        PlayerDetailsController playerDetailsController = new PlayerDetailsController();
        Label otherPosList = new Label("  " + playerDetailsController.getOtherPosColumn(player.getPlayerId()));
        otherPosList.setId("other-pos-list");

        // Player Age.
        Label ageTag = new Label(" Age: " + player.getPlayerAge());
        ageTag.setId("age-tag");
        HBox playerInfoAge = new HBox(otherPosList, ageTag);
        playerInfoAge.setAlignment(Pos.CENTER);

        // Best position.
        Label bestPosition = new Label(playerDetailsController.getFavPosColumn(player.getPlayerId()));
        bestPosition.setId("best-position");

        // Preferred foot.
        Label prefFoot = new Label(" " +player.getPlayerPrefFoot());
        prefFoot.setId("pref-foot");
        HBox playerInfoPos = new HBox(bestPosition, prefFoot);
        playerInfoPos.setAlignment(Pos.CENTER);

        // Overall card.
        VBox playerCard = new VBox(playerInfoTop, playerInfoName, playerInfoAge, playerInfoPos);

        playerCard.setId("player-card");

        // If the player is a captain, change the background color.
        if (player instanceof Captain) {
            playerCard.setStyle("-fx-background-color: #796612;");
        }

        return playerCard;
    }
}
