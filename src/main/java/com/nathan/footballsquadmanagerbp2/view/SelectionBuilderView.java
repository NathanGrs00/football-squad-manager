package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.model.Position;
import com.nathan.footballsquadmanagerbp2.model.Selection;
import com.nathan.footballsquadmanagerbp2.service.AlertService;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import com.nathan.footballsquadmanagerbp2.service.PositionService;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.*;

public class SelectionBuilderView {
    private final Selection selection;
    private PlayerService playerService;
    private PositionService positionService;
    private Set<Player> selectedPlayers;
    private AlertService alertService;
    private Map<Button, Position> positionButtonMap;
    private HBox root;
    private Pane menuBar;

    public SelectionBuilderView(Selection selectionPassed) {
        selection = selectionPassed;
        initVariables();
        initLayout();
        initGrid();
    }

    public Scene getScene() {
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/new-selection-stylesheet.css").toExternalForm());
        return homeScene;
    }

    public void initVariables() {
        playerService = new PlayerService();
        positionService = new PositionService();
        selectedPlayers = new HashSet<>();
        alertService = new AlertService();
        positionButtonMap = new HashMap<>();
        root = new HBox();
        menuBar = new MenuBar().createMenuBar();
    }

    public void initLayout() {
        root.setId("root-pane");
        menuBar.setId("menubar");
    }

    public void initGrid() {
        Image pitchBackground = new Image(getClass().getResource("/images/background_pitch.png").toExternalForm());
        ImageView pitchImageView = new ImageView(pitchBackground);
        pitchImageView.setFitWidth(1200);
        pitchImageView.setFitHeight(1100);
        pitchImageView.setPreserveRatio(true);
        pitchImageView.setTranslateX(15);
        GridPane grid = new GridPane();
        grid.setId("grid-pane");

        StackPane stackPane = new StackPane(pitchImageView, grid);

        ArrayList<Player> players = playerService.getPlayers();
        ArrayList<Position> positionsFromFormation = positionService.getPositionsFromFormationId(selection.getSelectionFormation().getFormationId());

        Label titleTag = new Label("Selection name: " + selection.getSelectionName());
        titleTag.setId("title-tag");

        Label formationTag = new Label("Formation: " + selection.getSelectionFormation().getFormationName());
        formationTag.setId("formation-tag");

        HBox selectionDetails = new HBox(titleTag, formationTag);
        selectionDetails.setId("selection-details");

        for (Position pos : positionsFromFormation) {
            Button positionButton = new Button(pos.getPositionAbreviation());

            positionButton.setOnAction(_ -> {
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
                    positionButton.setText(selectedPlayer.getPlayerLastName());
                    positionButton.setUserData(selectedPlayer);
                    positionButton.setStyle("-fx-opacity: 1;");
                    selectedPlayers.add(selectedPlayer);
                });
            });

            positionButton.getStyleClass().add("opacity-button");
            grid.add(positionButton, pos.getxPosition(), pos.getyPosition());
            positionButtonMap.put(positionButton, pos);
        }

        Button clearAllButton = new Button("Clear all");
        clearAllButton.setOnAction(_ -> {
            positionButtonMap.forEach((button, pos) -> {
                button.setText(pos.getPositionAbreviation());
                button.setUserData(null);
                button.setStyle("-fx-opacity: 0.7;");
            });
            selectedPlayers.clear();
        });
        clearAllButton.setId("clear-all-button");

        VBox selectionBox = new VBox(selectionDetails, stackPane, clearAllButton);
        selectionBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(menuBar, selectionBox);
    }
}
