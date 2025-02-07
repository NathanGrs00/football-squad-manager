package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.NewSelectionController;
import com.nathan.footballsquadmanagerbp2.model.Formation;
import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.model.Position;
import com.nathan.footballsquadmanagerbp2.service.AlertService;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import com.nathan.footballsquadmanagerbp2.service.PositionService;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.*;

public class NewSelectionView {
    private Map<Button, Position> positionButtonMap;
    private Set<Player> selectedPlayers;
    private PlayerService playerService;
    private PositionService positionService;
    private AlertService alertService;
    private NewSelectionController selectionController;
    private HBox root;
    private Pane menuBar;
    private VBox formationContent;

    private Label selectionNameTag;
    private TextField selectionNameInput;
    private Label formationTag;
    private ComboBox<Formation> formationChoice;
    private List<Formation> formations;

    private Button submitButton;

    public NewSelectionView() {
        initVariables();
        initLayout();
    }

    public void initVariables() {
        positionButtonMap = new HashMap<>();
        selectedPlayers = new HashSet<>();

        playerService = new PlayerService();
        positionService = new PositionService();
        alertService = new AlertService();
        selectionController = new NewSelectionController();
        root = new HBox();
        formationContent = new VBox();
        menuBar = new MenuBar().createMenuBar();

        selectionNameTag = new Label("Selection name: ");
        selectionNameInput = new TextField();
        formationTag = new Label("Formation: ");
        formationChoice = new ComboBox<>();
        formations = selectionController.getFormations();

        submitButton = new Button("Save new selection");
    }

    public void initLayout() {
        root.setId("root-pane");
        menuBar.setId("menubar");
        formationContent.setId("formation-content");
        formationContent.setPrefWidth(FootballSquadManager.screenSize[0] - 300);
        submitButton.setOnAction(_ -> handleButtonClick());

        for (Formation formation : formations) {
            formationChoice.getItems().add(formation);
        }
        formationContent.getChildren().addAll(selectionNameTag, selectionNameInput, formationTag, formationChoice, submitButton);
        root.getChildren().addAll(menuBar, formationContent);
    }

    public Scene getScene() {
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/new-selection-stylesheet.css").toExternalForm());
        return homeScene;
    }

    public void handleButtonClick() {
        String alertMessage = selectionController.validateFields(selectionNameInput, formationChoice);
        if (!alertMessage.isEmpty()) {
            alertService.getAlert(alertMessage);
        } else {
            initGrid();
        }
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
        ArrayList<Position> positionsFromFormation = positionService.getPositionsFromFormationId(formationChoice.getValue().getFormationId());

        Label titleTag = new Label("Selection name: " + selectionNameInput.getText());
        titleTag.setId("title-tag");

        Label formationTag = new Label("Formation: " + formationChoice.getValue().getFormationName());
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

                List<Player> availablePlayers = players.stream().filter(p-> !selectedPlayers.contains(p)).toList();

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

        root.getChildren().remove(formationContent);
        root.getChildren().add(selectionBox);
    }
}
