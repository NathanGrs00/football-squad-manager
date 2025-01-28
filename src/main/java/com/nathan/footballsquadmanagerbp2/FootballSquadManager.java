package com.nathan.footballsquadmanagerbp2;

import com.nathan.footballsquadmanagerbp2.view.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class FootballSquadManager extends Application {
    public static int[] screenSize = {800, 600};

    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView();

        stage.setResizable(false);
        stage.setTitle("Football Squad Manager");
        stage.setScene(loginView.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}