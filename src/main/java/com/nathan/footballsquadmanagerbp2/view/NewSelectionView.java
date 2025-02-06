package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.NewSelectionController;
import com.nathan.footballsquadmanagerbp2.model.Formation;
import com.nathan.footballsquadmanagerbp2.model.Player;
import com.nathan.footballsquadmanagerbp2.model.Position;
import com.nathan.footballsquadmanagerbp2.service.AlertService;
import com.nathan.footballsquadmanagerbp2.service.PlayerService;
import com.nathan.footballsquadmanagerbp2.service.PositionService;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class NewSelectionView {
    PlayerService playerService;
    PositionService positionService;
    AlertService alertService;
    NewSelectionController selectionController;
    HBox root;
    Pane menuBar;
    VBox formationContent;

    Label selectionNameTag;
    TextField selectionNameInput;
    Label formationTag;
    ComboBox<Formation> formationChoice;
    List<Formation> formations;

    Button submitButton;

    public NewSelectionView() {
        initVariables();
        initLayout();
    }

    public void initVariables() {
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
        pitchImageView.setFitWidth(FootballSquadManager.screenSize[0] - 100);
        pitchImageView.setPreserveRatio(true);
        pitchImageView.setTranslateX(17);
        StackPane stackPane = new StackPane(pitchImageView, formationContent);
        GridPane grid = new GridPane();
        grid.setId("grid-pane");
        grid.setHgap(100);
        grid.setVgap(20);

        ArrayList<Player> players = playerService.getPlayers();
        ArrayList<Position> positionsFromFormation = positionService.getPositionsFromFormationId(formationChoice.getValue().getFormationId());

        Label titleTag = new Label("Selection name: " + selectionNameInput.getText());
        titleTag.setId("title-tag");

        for (Position pos : positionsFromFormation) {
            Button positionButton = new Button(pos.getPositionAbreviation());

            positionButton.setOnAction(_ -> {
                //TODO: make buttons clickable to add players from players list.
            });

            positionButton.getStyleClass().add("position-button");
            grid.add(positionButton, pos.getxPosition(), pos.getyPosition());
        }

        formationContent.getChildren().removeAll(selectionNameTag, selectionNameInput, formationTag, formationChoice, submitButton);
        formationContent.getChildren().addAll(titleTag, grid);
        root.getChildren().add(stackPane);
    }
}
