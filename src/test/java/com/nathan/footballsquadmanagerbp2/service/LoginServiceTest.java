package com.nathan.footballsquadmanagerbp2.service;

import com.nathan.footballsquadmanagerbp2.model.User;
import com.nathan.footballsquadmanagerbp2.model.UserDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    @Test
    void checkLoginCorrectPassword() {
        String validUsername = "manager";
        String validPassword = "admin213";

        LoginService loginService = LoginService.getInstance();

        User resultUser = loginService.checkLogin(validUsername, validPassword);

        assertEquals(validUsername, resultUser.getUserName(), "Username should match");
    }

    @Test
    void checkLoginIncorrectPassword() {
        String validUsername = "manager";
        String validPassword = "banana";

        LoginService loginService = LoginService.getInstance();

        User resultUser = loginService.checkLogin(validUsername, validPassword);

        assertNull(resultUser, "Should return null");
    }
}