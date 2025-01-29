package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.controller.HomeController;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MenuBar {
    private final HomeController homeController;

    private final Pane root;
    private final VBox menubarContents;
    private final ImageView menubarImage;
    private final VBox allPlayersBox;
    private final Button allPlayersButton;
    private final VBox newSelectionBox;
    private final Button newSelectionButton;
    private final VBox allSelectionsBox;
    private final Button allSelectionsButton;
    private final Label footer;

    private final Timeline timeline;

    public MenuBar() {
        homeController = new HomeController();

        root = new Pane();
        menubarContents = new VBox();

        allPlayersBox = new VBox();
        allPlayersButton = new Button("PLAYERS");
        newSelectionBox = new VBox();
        newSelectionButton = new Button("NEW SELECTION");
        allSelectionsBox = new VBox();
        allSelectionsButton = new Button("ALL SELECTIONS");

        timeline = new Timeline();

        allPlayersBox.getChildren().add(allPlayersButton);
        newSelectionBox.getChildren().add(newSelectionButton);
        allSelectionsBox.getChildren().add(allSelectionsButton);

        footer = new Label("© 2025 Made by Nathan Geers. All rights reserved.");

        Image logoPath = new Image(getClass().getResource("/images/logo_fc_club_main.png").toExternalForm());
        menubarImage = new ImageView(logoPath);

        root.setId("menubar");
        menubarContents.setId("menubar-contents");

        root.getChildren().add(menubarContents);
    }

    public Pane createHomescreen(){
        setWidthAndGaps(1400, 300, 300, 500);
        getButtonHandlingHomescreen();

        menubarContents.getChildren().addAll(getSpacer(60), menubarImage, getSpacer(60), allPlayersBox, newSelectionBox, allSelectionsBox, getSpacer(60), footer);
        return root;
    }

    public Pane createMenuBar(){
        setWidthAndGaps(300, 200, 200, 200);
        getButtonHandlingMenuBar();

        root.setMinWidth(300);
        menubarContents.getChildren().addAll(getSpacer(110), menubarImage, getSpacer(91), allPlayersBox, newSelectionBox, allSelectionsBox, getSpacer(58), footer);
        return root;
    }

    public void setWidthAndGaps(int rootWidth, int imageWidth, int imageHeight, int barWidth){
        root.setPrefWidth(rootWidth);

        menubarImage.setFitWidth(imageWidth);
        menubarImage.setFitHeight(imageHeight);

        menubarContents.setPrefWidth(barWidth);

        double contentWidthGap = root.getPrefWidth() - menubarContents.getPrefWidth();
        menubarContents.setLayoutX(contentWidthGap / 2);
    }

    public Region getSpacer(int height){
        Region extraSpacing = new Region();
        extraSpacing.setPrefHeight(height);
        return extraSpacing;
    }

    private void getButtonHandlingMenuBar(){
        allPlayersButton.setOnAction(_ -> homeController.sendToPlayers());

        newSelectionButton.setOnAction(_ ->{

        });

        allSelectionsButton.setOnAction(_ ->{

        });
    }

    private void getButtonHandlingHomescreen(){
        allPlayersButton.setOnAction(_ ->{
            getAnimation();
            timeline.play();
            timeline.setOnFinished(_ -> homeController.sendToPlayers());
        });

        newSelectionButton.setOnAction(_ ->{
            getAnimation();
            timeline.play();
        });

        allSelectionsButton.setOnAction(_ ->{
            getAnimation();
            timeline.play();
        });
    }

    private void getAnimation(){
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(root.prefWidthProperty(), root.getPrefWidth()),
                        new KeyValue(menubarContents.prefWidthProperty(), menubarContents.getPrefWidth())
                ),
                new KeyFrame(Duration.seconds(1.5),
                        new KeyValue(root.prefWidthProperty(), 300, Interpolator.EASE_BOTH),
                        new KeyValue(menubarImage.fitWidthProperty(), 200, Interpolator.EASE_BOTH),
                        new KeyValue(menubarImage.fitHeightProperty(), 200, Interpolator.EASE_BOTH),
                        new KeyValue(menubarImage.translateYProperty(), 50, Interpolator.EASE_BOTH),
                        new KeyValue(allPlayersBox.translateYProperty(), 80, Interpolator.EASE_BOTH),
                        new KeyValue(newSelectionBox.translateYProperty(), 80, Interpolator.EASE_BOTH),
                        new KeyValue(allSelectionsBox.translateYProperty(), 80, Interpolator.EASE_BOTH),
                        new KeyValue(footer.translateYProperty(), 80, Interpolator.EASE_BOTH),
                        new KeyValue(menubarContents.prefWidthProperty(), 200, Interpolator.EASE_BOTH),
                        new KeyValue(menubarContents.translateXProperty(), -400, Interpolator.EASE_BOTH)
                )
        );
    }
}
