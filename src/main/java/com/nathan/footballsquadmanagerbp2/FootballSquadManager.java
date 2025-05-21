package com.nathan.footballsquadmanagerbp2;

import com.nathan.footballsquadmanagerbp2.controller.StageController;
import com.nathan.footballsquadmanagerbp2.model.Selection;
import com.nathan.footballsquadmanagerbp2.model.SelectionDetail;
import com.nathan.footballsquadmanagerbp2.view.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.List;

// Main class for the Football Squad Manager application.
public class FootballSquadManager extends Application {
    // Screen size for the application.
    public static int[] screenSize = {1600, 900};

    // Default constructor.
    @Override
    public void start(Stage primaryStage) {
        // Set the primary stage for the application.
        StageController.setPrimaryStage(primaryStage);
        // getLogin() method is called to display the login screen.
        getLogin();
        // Set the global options for the stage.
        setGlobalOptions();
    }

    public void setGlobalOptions() {
        // Default stage options for all stages.
        // Setting the icon for the application.
        Image clubIcon = new Image(getClass().getResource("/images/logo_fc_club_second.png").toExternalForm(), 500, 500, true, true);
        StageController.getPrimaryStage().getIcons().add(clubIcon);
        // Setting the title for the application.
        StageController.getPrimaryStage().setTitle("Football Squad Manager");
        // The user cannot resize the window.
        StageController.getPrimaryStage().setResizable(false);
        // Making sure the window is not in the top left corner of the screen.
        StageController.getPrimaryStage().setX(100);
        StageController.getPrimaryStage().setY(50);
        // Showing the primary stage.
        StageController.getPrimaryStage().show();
    }

    // Navigating to different sections, with the stage.
    public void getLogin() {
        // LoginView is the first view that is shown when the application starts.
        LoginView loginView = new LoginView();
        // Setting the scene to the login view.
        StageController.getPrimaryStage().setScene(loginView.getScene());
    }

    // Navigating to the home screen.
    public void getHomescreen() {
        // HomeView is the main view that is shown after the login.
        HomeView homeView = new HomeView();
        // Setting the scene to the home view.
        StageController.getPrimaryStage().setScene(homeView.getScene());
    }

    // Navigating to the player screen.
    public void getAllPlayers() {
        // AllPlayersView is the view that shows all players.
        AllPlayersView allPlayersView = new AllPlayersView();
        // Setting the scene to the all players view.
        StageController.getPrimaryStage().setScene(allPlayersView.getScene());
    }

    // New selection screen.
    public void getNewSelection() {
        // NewSelectionView is the view that allows the user to create a new selection.
        NewSelectionView newSelectionView = new NewSelectionView();
        // Setting the scene to the new selection view.
        StageController.getPrimaryStage().setScene(newSelectionView.getScene());
    }

    // Navigating to the selection builder screen.
    public void getBuilder(Selection selection, List<SelectionDetail> details) {
        // SelectionBuilderView is the view that allows the user to build a selection.
        SelectionBuilderView selectionBuilderView = new SelectionBuilderView(selection, details);
        // Setting the scene to the selection builder view.
        StageController.getPrimaryStage().setScene(selectionBuilderView.getScene());
    }

    // All selections screen.
    public void getAllSelections() {
        // AllSelectionsView is the view that shows all selections.
        AllSelectionsView allSelectionsView = new AllSelectionsView();
        // Setting the scene to the all selections view.
        StageController.getPrimaryStage().setScene(allSelectionsView.getScene());
    }

    // launching the application.
    public static void main(String[] args) {
        launch();
    }
}