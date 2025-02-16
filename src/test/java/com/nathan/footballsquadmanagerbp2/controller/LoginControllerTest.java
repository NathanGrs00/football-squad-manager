package com.nathan.footballsquadmanagerbp2.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {
    LoginController loginController = new LoginController();

    @Test
    void checkLoginWrongPassword() {
        String result = loginController.checkLogin("manager", "password");
        assertEquals("Invalid username or password.", result);
    }

    @Test
    void checkLoginEmptyPassword() {
        String result = loginController.checkLogin("manager", "");
        assertEquals("Username or password cannot be empty.", result);
    }
}