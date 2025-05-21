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

// This class creates the menu bar for the application.
public class MenuBar {
    // Controller for the logic.
    private final HomeController homeController;

    // UI components for the menu bar.
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

    // Timeline for animation.
    private final Timeline timeline;

    // Constructor initializes the variables.
    public MenuBar() {
        homeController = new HomeController();

        // Initialize UI components.
        root = new Pane();
        menubarContents = new VBox();

        // Buttons and their containers.
        allPlayersBox = new VBox();
        allPlayersButton = new Button("PLAYERS");
        allPlayersButton.getStyleClass().add("button-enlarge");
        newSelectionBox = new VBox();
        newSelectionButton = new Button("NEW SELECTION");
        newSelectionButton.getStyleClass().add("button-enlarge");
        allSelectionsBox = new VBox();
        allSelectionsButton = new Button("ALL SELECTIONS");
        allSelectionsButton.getStyleClass().add("button-enlarge");

        // Initialize the timeline for animation.
        timeline = new Timeline();

        // Add the buttons to their respective boxes.
        allPlayersBox.getChildren().add(allPlayersButton);
        newSelectionBox.getChildren().add(newSelectionButton);
        allSelectionsBox.getChildren().add(allSelectionsButton);

        // Footer label.
        footer = new Label("© 2025 Made by Nathan Geers. All rights reserved.");

        // Adding a logo image to the menu bar.
        Image logoPath = new Image(getClass().getResource("/images/logo_fc_club_main.png").toExternalForm());
        menubarImage = new ImageView(logoPath);

        root.setId("menubar");
        menubarContents.setId("menubar-contents");

        // Add the contents to the root pane.
        root.getChildren().add(menubarContents);
    }

    // Menubar for the homescreen.
    // This is the first screen the user sees when they open the application.
    // It is stretched to the full width of the screen.
    public Pane createHomescreen(){
        // Set the width and gaps for the menu bar.
        setWidthAndGaps(1400, 300, 300, 500);
        // Handling the button actions for the homescreen.
        getButtonHandlingHomescreen();

        // Adding the components to the menu bar, plus spacers for spacing.
        menubarContents.getChildren().addAll(getSpacer(60), menubarImage, getSpacer(60), allPlayersBox, newSelectionBox, allSelectionsBox, getSpacer(60), footer);
        // return the root pane.
        return root;
    }

    // Normal Menubar for the rest of the application.
    public Pane createMenuBar(){
        // Set the width and gaps for the menu bar.
        setWidthAndGaps(300, 200, 200, 200);
        // Handling the button actions for the menu bar.
        getButtonHandlingMenuBar();

        root.setMinWidth(300);
        // Set the minimum width of the root pane to 300.
        // adding the components to the menu bar, plus spacers for spacing.
        menubarContents.getChildren().addAll(getSpacer(110), menubarImage, getSpacer(91), allPlayersBox, newSelectionBox, allSelectionsBox, getSpacer(58), footer);
        // return the root pane.
        return root;
    }

    // Dynamically changing the layout based on values.
    public void setWidthAndGaps(int rootWidth, int imageWidth, int imageHeight, int barWidth){
        root.setPrefWidth(rootWidth);

        menubarImage.setFitWidth(imageWidth);
        menubarImage.setFitHeight(imageHeight);

        menubarContents.setPrefWidth(barWidth);

        // this makes sure the content is centered in the menu bar.
        double contentWidthGap = root.getPrefWidth() - menubarContents.getPrefWidth();
        menubarContents.setLayoutX(contentWidthGap / 2);
    }

    // Spacer to make space.
    public Region getSpacer(int height){
        // Create a region.
        Region extraSpacing = new Region();
        // Set the height of the region.
        extraSpacing.setPrefHeight(height);
        // return the region.
        return extraSpacing;
    }

    // Menubar handling for the homescreen (with animation)
    private void getButtonHandlingHomescreen(){
        // Players button action.
        allPlayersButton.setOnAction(_ ->{
            // load the animation.
            getAnimation();
            // play the animation.
            timeline.play();
            // when the animation is finished, send to players.
            timeline.setOnFinished(_ -> homeController.sendToPlayers());
        });

        // New selection button action.
        newSelectionButton.setOnAction(_ ->{
            // load the animation.
            getAnimation();
            // play the animation.
            timeline.play();
            // when the animation is finished, send to new selection.
            timeline.setOnFinished(_ -> homeController.sendToNewSelection());
        });

        // All selections button action.
        allSelectionsButton.setOnAction(_ ->{
            // load the animation.
            getAnimation();
            // play the animation.
            timeline.play();
            // when the animation is finished, send to all selections.
            timeline.setOnFinished(_ -> homeController.sendToAllSelections());
        });
    }

    // MenuBar navigation for rest of the application. (without animation)
    private void getButtonHandlingMenuBar(){
        // Players button action.
        allPlayersButton.setOnAction(_ -> homeController.sendToPlayers());

        // New selection button action.
        newSelectionButton.setOnAction(_ -> homeController.sendToNewSelection());

        // All selections button action.
        allSelectionsButton.setOnAction(_ -> homeController.sendToAllSelections());
    }

    // Animation for the sliding to the left.
    private void getAnimation(){
        // getKeyFrames() is used to create a timeline of keyframes.
        timeline.getKeyFrames().addAll(
                // Starting frame at 0 seconds, set the properties to their initial values.
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(root.prefWidthProperty(), root.getPrefWidth()),
                        new KeyValue(menubarContents.prefWidthProperty(), menubarContents.getPrefWidth())
                ),
                // 1.5 seconds in, set the properties to different values.
                // Ease in and out for smooth animation.
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
