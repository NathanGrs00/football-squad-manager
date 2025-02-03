package com.nathan.footballsquadmanagerbp2;

import com.nathan.footballsquadmanagerbp2.controller.StageController;
import com.nathan.footballsquadmanagerbp2.service.DBConnector;
import com.nathan.footballsquadmanagerbp2.view.AllPlayersView;
import com.nathan.footballsquadmanagerbp2.view.HomeView;
import com.nathan.footballsquadmanagerbp2.view.LoginView;
import com.nathan.footballsquadmanagerbp2.view.NewSelectionView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class FootballSquadManager extends Application {
    public static int[] screenSize = {1600, 900};

    @Override
    public void start(Stage primaryStage) {
        StageController.setPrimaryStage(primaryStage);
        getLogin();
        setGlobalOptions();
    }

    public void setGlobalOptions() {
        Image clubIcon = new Image(getClass().getResource("/images/logo_fc_club_second.png").toExternalForm(), 500, 500, true, true);
        StageController.getPrimaryStage().getIcons().add(clubIcon);
        StageController.getPrimaryStage().setTitle("Football Squad Manager");
        StageController.getPrimaryStage().setResizable(false);
        StageController.getPrimaryStage().setX(100);
        StageController.getPrimaryStage().setY(50);

        StageController.getPrimaryStage().show();
    }

    public void getLogin() {
        LoginView loginView = new LoginView();
        StageController.getPrimaryStage().setScene(loginView.getScene());
    }

    public void getHomescreen() {
        HomeView homeView = new HomeView();
        StageController.getPrimaryStage().setScene(homeView.getScene());
    }

    public void getAllPlayers() {
        AllPlayersView allPlayersView = new AllPlayersView();
        StageController.getPrimaryStage().setScene(allPlayersView.getScene());
    }

    public void getNewSelection() {
        NewSelectionView newSelectionView = new NewSelectionView();
        StageController.getPrimaryStage().setScene(newSelectionView.getScene());
    }

    public static void main(String[] args) {
        launch();
    }
}