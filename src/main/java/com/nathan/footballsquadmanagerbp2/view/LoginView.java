package com.nathan.footballsquadmanagerbp2.view;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.controller.LoginController;

import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class LoginView {
    // Private variables.
    private LoginController controller;

    private Pane rootPane;
    private VBox loginFields;

    private ImageView clubLogo;
    private ColorAdjust colorAdjust;
    private GaussianBlur blur;
    private FadeTransition fadeTransition;

    private VBox usernameBox;
    private TextField txtUsername;
    private VBox passwordBox;
    private PasswordField txtPassword;

    private Button btnLogin;
    private Label returnLabel;

    private double loginWidth;
    private double loginHeight;

    // Constructor to populate scene.
    public LoginView() {
        initVariables();
        applyStyling();
    }

    // getScene for the stage.
    public Scene getScene() {
        Scene loginScene = new Scene(rootPane, loginWidth, loginHeight);
        loginScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        loginScene.getStylesheets().add(getClass().getResource("/stylesheets/login-stylesheet.css").toExternalForm());
        return loginScene;
    }

    // Initializing the variables.
    private void initVariables() {
        controller = new LoginController();

        rootPane = new Pane();
        loginFields = new VBox();

        loginWidth = FootballSquadManager.screenSize[0] - 350;
        loginHeight = FootballSquadManager.screenSize[1] - 200;

        Image logoPath = new Image(getClass().getResource("/images/logo_fc_club_main.png").toExternalForm());
        clubLogo = new ImageView(logoPath);
        colorAdjust = new ColorAdjust();
        blur = new GaussianBlur(1);
        fadeTransition = new FadeTransition(Duration.seconds(10), clubLogo);

        Label usernameTag = new Label("Username");
        txtUsername = new TextField();
        usernameBox = new VBox(usernameTag, txtUsername);
        Label passwordTag = new Label("Password");
        txtPassword = new PasswordField();
        passwordBox = new VBox(passwordTag, txtPassword);

        Image entranceLogo = new Image(getClass().getResource("/icons/entrance_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView entranceLogoView = new ImageView(entranceLogo);
        btnLogin = new Button("Login");
        btnLogin.setGraphic(entranceLogoView);

        returnLabel = new Label();
    }

    // Layout and styling.
    private void applyStyling(){
        txtUsername.setPromptText("Username");
        txtPassword.setPromptText("Password");

        btnLogin.setOnAction(_ -> handleLoginButton());

        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        blur.setRadius(10);
        colorAdjust.setSaturation(-0.9);
        colorAdjust.setBrightness(-0.6);
        colorAdjust.setInput(blur);

        clubLogo.setEffect(colorAdjust);
        clubLogo.setId("club-logo");
        clubLogo.setFitWidth(400);
        clubLogo.setFitHeight(400);

        double imageGapHeight = loginHeight - clubLogo.getFitHeight();
        double imageGapWidth = loginWidth - clubLogo.getFitWidth();
        clubLogo.setLayoutY(imageGapHeight / 2);
        clubLogo.setLayoutX(imageGapWidth / 2);

        loginFields.setPrefWidth(300);
        loginFields.setPrefHeight(400);

        double loginBoxGapHeight = loginHeight - loginFields.getPrefHeight();
        double loginBoxGapWidth = loginWidth - loginFields.getPrefWidth();
        loginFields.setLayoutY(loginBoxGapHeight / 2);
        loginFields.setLayoutX(loginBoxGapWidth / 2);

        loginFields.setId("login-fields");
        loginFields.getChildren().addAll(usernameBox, passwordBox, btnLogin, returnLabel);

        rootPane.setId("root-pane");
        rootPane.getChildren().addAll(clubLogo, loginFields);
    }

    // Sending to the controller check
    private void handleLoginButton() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        returnLabel.setText(controller.checkLogin(username, password));
    }
}
