package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class HomeView {
    // HomeView uses a stretched out version of the menubar.
    private final HBox root;

    public HomeView() {
        root = new HBox();
        root.setId("root-pane");

        Pane menuBar = new MenuBar().createHomescreen();
        menuBar.setId("menubar");

        root.getChildren().add(menuBar);
    }

    public Scene getScene() {
        Scene homeScene = new Scene(root, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
        homeScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        homeScene.getStylesheets().add(getClass().getResource("/stylesheets/menubar-stylesheet.css").toExternalForm());
        return homeScene;
    }
}
