package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;

public class HomeView {
    private HBox hBox;

    public HomeView() {
        initLayouts();
    }

    private void initLayouts(){
        hBox = new HBox();
    }

    public Scene getScene() {
        return new Scene(hBox, FootballSquadManager.screenSize[0], FootballSquadManager.screenSize[1]);
    }
}
