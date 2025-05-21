package com.nathan.footballsquadmanagerbp2.controller;

import com.nathan.footballsquadmanagerbp2.FootballSquadManager;
import com.nathan.footballsquadmanagerbp2.model.User;
import com.nathan.footballsquadmanagerbp2.service.LoginService;

// Controller class for handling login functionality.
public class LoginController {
    // Checking if inputs are empty, and if they are the correct password.
    public String checkLogin(String username, String password) {
        // Checking if username or password is empty.
        if (username.isEmpty() || password.isEmpty()) {
            // Returning error message if either field is empty.
            return "Username or password cannot be empty.";
        }

        // Checking if login corresponds to a User.
        User loggedInUser = LoginService.getInstance().checkLogin(username, password);

        // If there is a user,
        if (loggedInUser != null) {
            // Sends the user to the homescreen.
            FootballSquadManager footballSquadManager = new FootballSquadManager();
            footballSquadManager.getHomescreen();
            // Returning success message.
            return "Login Successful";
        }

        // If the user is null, it means the login was unsuccessful.
        // Returning error message.
        return "Invalid username or password.";
    }
}
