package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.User;
import com.nathan.footballsquadmanagerbp2.model.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

// This class is responsible for handling user login functionality.
public class LoginService {
    // userDAO for database access
    private final UserDAO userDAO = new UserDAO();
    // loggedInUser stores the currently logged-in user
    private User loggedInUser;
    // Singleton instance of LoginService
    private static LoginService instance;

    // Private constructor to prevent instantiation
    private LoginService() {}

    // Method to get the singleton instance of LoginService
    public static LoginService getInstance() {
        // If instance is null, create a new instance
        if (instance == null) {
            instance = new LoginService();
        }
        // Return the singleton instance
        return instance;
    }

    // Method to check user login credentials
    public User checkLogin(String username, String password) {
        // get the user from the database using the username
        loggedInUser = userDAO.getUserByUsername(username);

        // If user exists and password is correct, return the user. Passwords are encrypted in the database.
        // BCrypt.checkpw() is used to compare the plaintext password with the hashed password.
        if (loggedInUser != null & BCrypt.checkpw(password, loggedInUser.getPassword())) {
            return loggedInUser;
        }

        // Reset loggedInUser if login fails.
        loggedInUser = null;
        // Return null if login fails
        return null;
    }

    // Method to check if a user is logged in
    public User getLoggedInUser() {
        return loggedInUser;
    }
}
