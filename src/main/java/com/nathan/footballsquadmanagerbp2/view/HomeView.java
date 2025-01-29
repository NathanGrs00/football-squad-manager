package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
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
import javafx.scene.paint.Color;

import java.awt.*;

public class HomeView {
    private HBox root;
    private Pane homeForeground;
    private Pane homeBackground;

    private VBox homeContent;
    private ImageView clubLogo;
    private VBox allPlayersBox;
    private Button homeAllPlayers;
    private VBox newSelectionBox;
    private Button homeNewSelection;
    private VBox allSelectionsBox;
    private Button homeAllSelections;
    private Label footer;


    public HomeView() {
        initLayouts();
        applyStyling();
    }

    private void initLayouts() {
        root = new HBox();
        homeForeground = new Pane();
        homeBackground = new Pane();

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
    }

    private void applyStyling() {
        homeBackground.setId("home-background");
        homeBackground.setPrefWidth(200);
        homeForeground.setId("home-foreground");
        homeForeground.setPrefWidth(FootballSquadManager.screenSize[0] - homeBackground.getPrefWidth());
        homeForeground.getChildren().addAll(homeContent);

        clubLogo.setFitWidth(300);
        clubLogo.setPreserveRatio(true);

        allPlayersBox.getChildren().add(homeAllPlayers);
        allPlayersBox.setPrefHeight(55);
        allPlayersBox.setAlignment(Pos.BOTTOM_CENTER);
        newSelectionBox.getChildren().add(homeNewSelection);
        newSelectionBox.setPrefHeight(55);
        newSelectionBox.setAlignment(Pos.BOTTOM_CENTER);
        allSelectionsBox.getChildren().add(homeAllSelections);
        allSelectionsBox.setPrefHeight(55);
        allSelectionsBox.setAlignment(Pos.BOTTOM_CENTER);

        homeContent.setId("home-content");
        homeContent.setPrefWidth(500);
        homeContent.getChildren().addAll(getSpacer(), clubLogo, getSpacer(), allPlayersBox, newSelectionBox, allSelectionsBox, getSpacer(), footer);

        double contentWidthGap = homeForeground.getPrefWidth() - homeContent.getPrefWidth();

        homeContent.setLayoutX(contentWidthGap / 2);

        root.getChildren().addAll(homeForeground, homeBackground);
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
}
