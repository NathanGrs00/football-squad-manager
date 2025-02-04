package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.User;
import com.nathan.footballsquadmanagerbp2.model.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {
    private final UserDAO userDAO = new UserDAO();
    private User loggedInUser;
    private static LoginService instance;

    private LoginService() {}

    public static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    public User checkLogin(String username, String password) {
        loggedInUser = userDAO.getUser(username);

        // If user exists and password is correct, return the user.
        if (loggedInUser != null & BCrypt.checkpw(password, loggedInUser.getPassword())) {
            return loggedInUser;
        }

        // Reset loggedInUser if login fails.
        loggedInUser = null;
        return null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
