package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class HomeView {
    private HBox root;
    private Pane homeForeground;

    private VBox homeContent;
    private ImageView clubLogo;
    private VBox allPlayersBox;
    private Button homeAllPlayers;
    private VBox newSelectionBox;
    private Button homeNewSelection;
    private VBox allSelectionsBox;
    private Button homeAllSelections;
    private Label footer;

    private Timeline timeline;

    public HomeView() {
        initLayouts();
        applyStyling();
        getButtonHandling();
    }

    private void initLayouts() {
        root = new HBox();
        homeForeground = new Pane();

        homeContent = new VBox();
        Image logoPath = new Image(getClass().getResource("/images/logo_fc_club_main.png").toExternalForm());
        clubLogo = new ImageView(logoPath);
        allPlayersBox = new VBox();
        homeAllPlayers = new Button("PLAYERS");
        newSelectionBox = new VBox();
        homeNewSelection = new Button("NEW SELECTION");
        allSelectionsBox = new VBox();
        homeAllSelections = new Button("ALL SELECTIONS");
        footer = new Label("© 2025 Made by Nathan Geers. All rights reserved.");

        timeline = new Timeline();
    }

    private void applyStyling() {
        root.setId("root-pane");

        homeForeground.setId("home-foreground");
        homeForeground.setPrefWidth(FootballSquadManager.screenSize[0] - 200);
        homeForeground.getChildren().addAll(homeContent);

        clubLogo.setFitWidth(300);
        clubLogo.setFitHeight(300);

        allPlayersBox.getChildren().add(homeAllPlayers);
        newSelectionBox.getChildren().add(homeNewSelection);
        allSelectionsBox.getChildren().add(homeAllSelections);

        homeContent.setId("home-content");
        homeContent.setPrefWidth(500);

        double contentWidthGap = homeForeground.getPrefWidth() - homeContent.getPrefWidth();
        homeContent.setLayoutX(contentWidthGap / 2);

        homeContent.getChildren().addAll(getSpacer(), clubLogo, getSpacer(), allPlayersBox, newSelectionBox, allSelectionsBox, getSpacer(), footer);
        root.getChildren().add(homeForeground);
    }

    public Scene getScene() {
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/home-stylesheet.css").toExternalForm());
        return homeScene;
    }

    private Region getSpacer() {
        Region extraSpacing = new Region();
        extraSpacing.setPrefHeight(30);
        return extraSpacing;
    }

    private void getButtonHandling(){
        homeAllPlayers.setOnAction(e ->{
            getAnimation();
            timeline.play();
        });
        homeNewSelection.setOnAction(e ->{
            getAnimation();
            timeline.play();
        });
        homeAllSelections.setOnAction(e ->{
            getAnimation();
            timeline.play();
        });
    }

    private Timeline getAnimation(){
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(homeForeground.prefWidthProperty(), homeForeground.getPrefWidth()),
                        new KeyValue(homeContent.prefWidthProperty(), homeContent.getPrefWidth())
                ),
                new KeyFrame(Duration.seconds(1.5),
                        new KeyValue(homeForeground.prefWidthProperty(), 300, Interpolator.EASE_BOTH),
                        new KeyValue(clubLogo.fitWidthProperty(), 200, Interpolator.EASE_BOTH),
                        new KeyValue(clubLogo.fitHeightProperty(), 200, Interpolator.EASE_BOTH),
                        new KeyValue(clubLogo.translateYProperty(), 50, Interpolator.EASE_BOTH),
                        new KeyValue(allPlayersBox.translateYProperty(), 80, Interpolator.EASE_BOTH),
                        new KeyValue(newSelectionBox.translateYProperty(), 80, Interpolator.EASE_BOTH),
                        new KeyValue(allSelectionsBox.translateYProperty(), 80, Interpolator.EASE_BOTH),
                        new KeyValue(footer.translateYProperty(), 80, Interpolator.EASE_BOTH),
                        new KeyValue(homeContent.prefWidthProperty(), 200, Interpolator.EASE_BOTH),
                        new KeyValue(homeContent.translateXProperty(), -400, Interpolator.EASE_BOTH)
                )
        );
        return timeline;
    }
}
