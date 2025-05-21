package com.nathan.footballsquadmanagerbp2.model;

// Class representing a user in the system
public class User {
    // Fields for user ID, username, password, and role
    private final int userId;
    private final String userName;
    private final String password;
    private final String role;

    // Constructor to initialize a User object
    public User(int userId, String userName, String password, String role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    // Getters for user ID, username, password, and role
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    // Role for future role-based permissions
    public String getRole() {
        return role;
    }

    // Ensures userName can be used in a tableview property.
    @Override
    public String toString() {
        return userName;
    }
}
