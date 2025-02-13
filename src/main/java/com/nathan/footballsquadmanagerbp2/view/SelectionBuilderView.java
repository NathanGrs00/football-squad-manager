package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.PositionController;
import com.nathan.footballsquadmanagerbp2.model.*;
import com.nathan.footballsquadmanagerbp2.service.AlertService;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import com.nathan.footballsquadmanagerbp2.service.PositionService;
import com.nathan.footballsquadmanagerbp2.service.SelectionDetailsService;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.*;

public class SelectionBuilderView {
    private final Selection selection;
    private final List<SelectionDetail> selectionDetails;
    private SelectionDetailsService selectionDetailsService;
    private PlayerService playerService;
    private PositionController positionController;
    private AlertService alertService;
    private Set<Player> selectedPlayers;
    private Map<Button, Position> positionButtonMap;
    private HBox root;
    private GridPane grid;

    public SelectionBuilderView(Selection selectionPassed, List<SelectionDetail> detailsPassed) {
        this.selectionDetails = detailsPassed;
        this.selection = selectionPassed;
        initVariables();
        initLayouts();
    }

    public Scene getScene() {
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/new-selection-stylesheet.css").toExternalForm());
        return homeScene;
    }

    private void initVariables() {
        root = new HBox();
        grid = new GridPane();
        selectionDetailsService = new SelectionDetailsService();
        playerService = new PlayerService();
        positionController = new PositionController();
        alertService = new AlertService();
        selectedPlayers = new HashSet<>();
        positionButtonMap = new HashMap<>();
    }

    private void initLayouts() {
        root.setId("root-pane");

        Pane menuBar = new MenuBar().createMenuBar();
        menuBar.setId("menubar");

        Label titleTag = new Label("Selection name: " + selection.getSelectionName());
        titleTag.setId("title-tag");

        Label formationTag = new Label("Formation: " + selection.getSelectionFormation().getFormationName());
        formationTag.setId("formation-tag");

        HBox selectionDetailsBox = new HBox(titleTag, formationTag);
        selectionDetailsBox.setId("selection-details");

        VBox selectionBox = new VBox(selectionDetailsBox, initPitchGrid(), createRemoveButton());
        selectionBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(menuBar, selectionBox);
    }

    private StackPane initPitchGrid() {
        ImageView pitchImageView = new ImageView(new Image(getClass().getResource("/images/background_pitch.png").toExternalForm()));
        pitchImageView.setFitWidth(1200);
        pitchImageView.setFitHeight(1100);
        pitchImageView.setPreserveRatio(true);
        pitchImageView.setTranslateX(15);

        grid.setId("grid-pane");

        setupPositionButtons();

        return new StackPane(pitchImageView, grid);
    }

    private void setupPositionButtons() {
        List<Player> players = playerService.getPlayers();
        List<Position> positionsFromFormation = positionController.getPositionsList(selection.getSelectionFormation().getFormationId());

        Map<Integer, Player> positionPlayerMap = getPlayerPositionMap();

        for (Position pos : positionsFromFormation) {
            Button positionButton = new Button(pos.getPositionAbreviation());

            if (positionPlayerMap.containsKey(pos.getPositionId())) {
                Player preselectedPlayer = positionPlayerMap.get(pos.getPositionId());
                positionButton.setText(preselectedPlayer.getPlayerLastName());
                positionButton.setUserData(preselectedPlayer);
                positionButton.setStyle("-fx-opacity: 1;");
            }

            positionButton.setOnAction(_ -> handlePositionSelection(positionButton, pos, players));
            positionButton.getStyleClass().add("opacity-button");

            grid.add(positionButton, pos.getXPosition(), pos.getYPosition());
            positionButtonMap.put(positionButton, pos);
        }
    }

    private Map<Integer, Player> getPlayerPositionMap() {
        Map<Integer, Player> positionPlayerMap = new HashMap<>();
        for (SelectionDetail detail : selectionDetails) {
            Player player = playerService.getPlayerById(detail.getPlayerId());
            if (player != null) {
                positionPlayerMap.put(detail.getPositionId(), player);
                selectedPlayers.add(player);
            }
        }
        return positionPlayerMap;
    }

    private void handlePositionSelection(Button positionButton, Position pos, List<Player> players) {
        Player previousPlayer = (Player) positionButton.getUserData();
        if (previousPlayer != null) {
            selectedPlayers.remove(previousPlayer);
        }

        List<Player> availablePlayers = new ArrayList<>();
        for (Player player : players) {
            if (!selectedPlayers.contains(player)) {
                availablePlayers.add(player);
            }
        }

        if (availablePlayers.isEmpty()) {
            alertService.getAlert("No player available!");
            return;
        }

        ChoiceDialog<Player> dialogue = new ChoiceDialog<>(availablePlayers.getFirst(), availablePlayers);
        dialogue.setTitle("Select a player");
        dialogue.setHeaderText("Choose a player for " + pos.getPositionAbreviation());
        dialogue.setContentText("Player:");

        Optional<Player> result = dialogue.showAndWait();
        result.ifPresent(selectedPlayer -> {
            if (previousPlayer != null) {
                selectionDetailsService.removePlayerFromSelection(selection.getSelectionId(), previousPlayer.getPlayerId(), pos.getPositionId());
            }

            positionButton.setText(selectedPlayer.getPlayerLastName());
            positionButton.setUserData(selectedPlayer);
            positionButton.setStyle("-fx-opacity: 1;");
            selectedPlayers.add(selectedPlayer);

            selectionDetailsService.insertDetails(selection.getSelectionId(), selectedPlayer.getPlayerId(), pos.getPositionId());
        });
    }

    private Button createRemoveButton() {
        Button clearAllButton = new Button("Clear all");
        clearAllButton.setId("clear-all-button");

        clearAllButton.setOnAction(_ -> {
            positionButtonMap.forEach((button, pos) -> {
                button.setText(pos.getPositionAbreviation());
                button.setUserData(null);
                button.setStyle("-fx-opacity: 0.7;");
            });
            selectedPlayers.clear();
            selectionDetailsService.removeAllEntries(selection.getSelectionId());
        });

        return clearAllButton;
    }
}
