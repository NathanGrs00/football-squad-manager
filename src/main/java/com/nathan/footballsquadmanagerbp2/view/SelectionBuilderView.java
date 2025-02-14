package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.PlayerDetailsController;
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
    private Map<VBox, Position> positionButtonMap;
    private HBox root;
    private GridPane grid;

    public SelectionBuilderView(Selection selectionPassed, List<SelectionDetail> detailsPassed) {
        this.selectionDetails = detailsPassed;
        this.selection = selectionPassed;
        initVariables();
        for (SelectionDetail detail : selectionDetails) {
            Player player = playerService.getPlayerById(detail.getPlayerId());
            if (player != null) {
                selectedPlayers.add(player);
            }
        }
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
        pitchImageView.setFitWidth(1300);
        pitchImageView.setFitHeight(1100);
        pitchImageView.setPreserveRatio(true);

        grid.setId("grid-pane");

        setupPositionButtons();

        return new StackPane(pitchImageView, grid);
    }

    private void setupPositionButtons() {
        List<Player> players = playerService.getPlayers();
        players.removeIf(player -> player.getPlayerStatus().equals("Not available"));

        List<Position> positionsFromFormation = positionController.getPositionsList(selection.getSelectionFormation().getFormationId());

        Map<Integer, Player> positionPlayerMap = getPlayerPositionMap();

        for (Position pos : positionsFromFormation) {
            Label placeholder = new Label(pos.getPositionAbreviation());
            placeholder.setId("placeholder");
            VBox playerCard = new VBox(placeholder);

            if (positionPlayerMap.containsKey(pos.getPositionId())) {
                Player preselectedPlayer = positionPlayerMap.get(pos.getPositionId());
                playerCard.getChildren().remove(placeholder);
                playerCard = createPlayerCard(preselectedPlayer);
                playerCard.setMinWidth(115);
                playerCard.setUserData(preselectedPlayer);
                playerCard.setStyle("-fx-opacity: 1;");
            }

            VBox finalPlayerCard = playerCard;
            playerCard.setOnMouseClicked(_ -> handlePositionSelection(finalPlayerCard, pos, players));
            playerCard.getStyleClass().add("opacity-button");

            grid.add(playerCard, pos.getXPosition(), pos.getYPosition());
            positionButtonMap.put(playerCard, pos);
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

    private void handlePositionSelection(VBox playerInfo, Position pos, List<Player> players) {
        Player previousPlayer = (Player) playerInfo.getUserData();
        if (previousPlayer != null) {
            selectedPlayers.remove(previousPlayer);
        }

        List<Player> availablePlayers = new ArrayList<>();
        for (Player player : players) {
            boolean isAlreadySelected = selectedPlayers.contains(player);
            if (!isAlreadySelected) {
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
            if (selectedPlayers.contains(selectedPlayer)) {
                alertService.getAlert("Player already selected!");
                return;
            }

            if (previousPlayer != null) {
                selectionDetailsService.removePlayerFromSelection(selection.getSelectionId(), previousPlayer.getPlayerId(), pos.getPositionId());
                selectedPlayers.remove(previousPlayer);
            }
            playerInfo.getChildren().clear();
            playerInfo.getChildren().add(createPlayerCard(selectedPlayer));
            playerInfo.setUserData(selectedPlayer);
            playerInfo.setStyle("-fx-opacity: 1;");
            selectedPlayers.add(selectedPlayer);

            selectionDetailsService.insertDetails(selection.getSelectionId(), selectedPlayer.getPlayerId(), pos.getPositionId());
        });
    }

    private Button createRemoveButton() {
        Button clearAllButton = new Button("Clear all");
        clearAllButton.setId("clear-all-button");

        clearAllButton.setOnAction(_ -> {
            positionButtonMap.forEach((playerCard, pos) -> {
                playerCard.getChildren().clear();
                Label placeholder = new Label(pos.getPositionAbreviation());
                placeholder.setId("placeholder");
                playerCard.getChildren().add(placeholder);
                playerCard.setMinWidth(115);
                playerCard.setStyle("-fx-opacity: .7;");
                playerCard.setUserData(null);
            });
            selectedPlayers.clear();
            selectionDetailsService.removeAllEntries(selection.getSelectionId());
        });

        return clearAllButton;
    }

    private VBox createPlayerCard(Player player) {
        Image playerIconPath = new Image(getClass().getResource("/icons/user_icon.png").toExternalForm());
        ImageView playerIcon = new ImageView(playerIconPath);
        playerIcon.setFitWidth(30);
        playerIcon.setFitHeight(30);
        Label playerNumber = new Label(String.valueOf(player.getPlayerShirtNumber()));
        playerNumber.setId("player-number");
        HBox playerInfoTop = new HBox(playerIcon, playerNumber);
        playerInfoTop.setSpacing(50);
        playerInfoTop.setAlignment(Pos.CENTER);

        String firstLetter = player.getPlayerFirstName().substring(0, 1);
        Label playerInfoName = new Label(firstLetter + ". " + player.getPlayerLastName());
        playerInfoName.setId("player-info-name");

        PlayerDetailsController playerDetailsController = new PlayerDetailsController();

        Label otherPosList = new Label("  " + playerDetailsController.getOtherPosColumn(player.getPlayerId()));
        otherPosList.setId("other-pos-list");
        Label ageTag = new Label(" Age: " + player.getPlayerAge());
        ageTag.setId("age-tag");
        ageTag.setMinWidth(52);
        HBox playerInfoAge = new HBox(otherPosList, ageTag);
        playerInfoAge.setAlignment(Pos.CENTER);

        Label bestPosition = new Label(playerDetailsController.getFavPosColumn(player.getPlayerId()));
        bestPosition.setId("best-position");
        Label prefFoot = new Label(" " +player.getPlayerPrefFoot());
        prefFoot.setId("pref-foot");
        HBox playerInfoPos = new HBox(bestPosition, prefFoot);
        playerInfoPos.setAlignment(Pos.CENTER);

        VBox playerCard = new VBox(playerInfoTop, playerInfoName, playerInfoAge, playerInfoPos);
        playerCard.setId("player-card");

        return playerCard;
    }
}
