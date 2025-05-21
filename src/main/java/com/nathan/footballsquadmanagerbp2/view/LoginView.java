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

// UI class for the login screen.
public class LoginView {
    // Controller for the login screen.
    private LoginController controller;

    // UI components for the login screen.
    private Pane rootPane;
    private VBox loginFields;

    // Logo and effects.
    private ImageView clubLogo;
    private ColorAdjust colorAdjust;
    private GaussianBlur blur;
    private FadeTransition fadeTransition;

    // Username and password fields.
    private VBox usernameBox;
    private TextField txtUsername;
    private VBox passwordBox;
    private PasswordField txtPassword;

    // Login button and return label for error message displaying.
    private Button btnLogin;
    private Label returnLabel;

    // Dimensions for the login screen.
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
        // Setting the stylesheet and font for the scene.
        loginScene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap");
        loginScene.getStylesheets().add(getClass().getResource("/stylesheets/login-stylesheet.css").toExternalForm());
        // returning the scene.
        return loginScene;
    }

    // Initializing the variables.
    private void initVariables() {
        // Initializing the controller.
        controller = new LoginController();

        // Initializing the UI components.
        rootPane = new Pane();
        loginFields = new VBox();

        // Setting the dimensions for the login screen.
        loginWidth = FootballSquadManager.screenSize[0] - 350;
        loginHeight = FootballSquadManager.screenSize[1] - 200;

        // Setting the logo and effects.
        Image logoPath = new Image(getClass().getResource("/images/logo_fc_club_main.png").toExternalForm());
        // Setting the logo size.
        clubLogo = new ImageView(logoPath);
        // Setting the logo effects.
        colorAdjust = new ColorAdjust();
        blur = new GaussianBlur(1);
        // Setting the fade transition for the logo.
        fadeTransition = new FadeTransition(Duration.seconds(10), clubLogo);

        // Initializing the username and password fields.
        Label usernameTag = new Label("Username");
        txtUsername = new TextField();
        usernameBox = new VBox(usernameTag, txtUsername);
        Label passwordTag = new Label("Password");
        txtPassword = new PasswordField();
        passwordBox = new VBox(passwordTag, txtPassword);

        // The logo for the login button.
        Image entranceLogo = new Image(getClass().getResource("/icons/entrance_icon.png").toExternalForm(), 25, 25, true, true);
        ImageView entranceLogoView = new ImageView(entranceLogo);
        btnLogin = new Button("Login");
        // Setting the logo for the button.
        btnLogin.setGraphic(entranceLogoView);

        // New label for the return message.
        returnLabel = new Label();
    }

    // Layout and styling.
    private void applyStyling(){
        // Hints for the username and password fields.
        txtUsername.setPromptText("Username");
        txtPassword.setPromptText("Password");

        // handle login button action called when the button is clicked.
        btnLogin.setOnAction(_ -> handleLoginButton());

        // fade transition for the logo.
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        // Blur, brightness and saturation effects for the logo.
        blur.setRadius(10);
        colorAdjust.setSaturation(-0.9);
        colorAdjust.setBrightness(-0.6);
        colorAdjust.setInput(blur);

        clubLogo.setEffect(colorAdjust);
        clubLogo.setId("club-logo");
        // Setting the logo size.
        clubLogo.setFitWidth(400);
        clubLogo.setFitHeight(400);

        // Making sure the logo is in the middle
        double imageGapHeight = loginHeight - clubLogo.getFitHeight();
        double imageGapWidth = loginWidth - clubLogo.getFitWidth();
        clubLogo.setLayoutY(imageGapHeight / 2);
        clubLogo.setLayoutX(imageGapWidth / 2);

        // The pref width and height for the login fields.
        loginFields.setPrefWidth(300);
        loginFields.setPrefHeight(400);

        // Making sure the login fields are in the middle.
        double loginBoxGapHeight = loginHeight - loginFields.getPrefHeight();
        double loginBoxGapWidth = loginWidth - loginFields.getPrefWidth();
        loginFields.setLayoutY(loginBoxGapHeight / 2);
        loginFields.setLayoutX(loginBoxGapWidth / 2);

        // Adding all the fields to the login fields.
        loginFields.setId("login-fields");
        loginFields.getChildren().addAll(usernameBox, passwordBox, btnLogin, returnLabel);

        // Adding the logo and login fields to the root pane.
        rootPane.setId("root-pane");
        rootPane.getChildren().addAll(clubLogo, loginFields);
    }

    // Sending to the controller check
    private void handleLoginButton() {
        // getting the username and password from the text fields.
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // checking if the username and password are empty.
        // Setting the returnLabel to the error message.
        returnLabel.setText(controller.checkLogin(username, password));
    }
}
