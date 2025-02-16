package com.nathan.footballsquadmanagerbp2.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerServiceTest {

    @Test
    void validatePlayerFormAgeTooBig() {
        PlayerService playerService = new PlayerService();
        List<String> positions = new ArrayList<>();
        positions.add("LW");
        positions.add("AMC");

        String alert = playerService.ValidatePlayerForm("Nathan", "Geers", 99, 28, positions);

        assertEquals("Age must be between 15 and 65", alert);
    }

    @Test
    void validatePlayerFormAgeTooSmall() {
        PlayerService playerService = new PlayerService();
        List<String> positions = new ArrayList<>();
        positions.add("LW");
        positions.add("AMC");

        String alert = playerService.ValidatePlayerForm("Nathan", "Geers", 5, 28, positions);

        assertEquals("Age must be between 15 and 65", alert);
    }

    @Test
    void validatePlayerFormPositionsExist() {
        PlayerService playerService = new PlayerService();
        List<String> positions = new ArrayList<>();
        positions.add("LW");
        positions.add("AMC");

        String alert = playerService.ValidatePlayerForm("Nathan", "Geers", 24, 28, positions);

        assertEquals("", alert);
    }

    @Test
    void validatePlayerFormPositionsDoNotExist() {
        PlayerService playerService = new PlayerService();
        List<String> positions = new ArrayList<>();
        positions.add("LSTW");
        positions.add("AMB");

        String alert = playerService.ValidatePlayerForm("Nathan", "Geers", 24, 28, positions);

        assertEquals("Please enter the other positions in this format: 'RWB, CDM, LB' etc.", alert);
    }

    @Test
    void validatePlayerFormFirstNameTooLong() {
        PlayerService playerService = new PlayerService();
        List<String> positions = new ArrayList<>();
        positions.add("ST");
        positions.add("AMC");

        String alert = playerService.ValidatePlayerForm("Nathannnnnnnnnnnnnnnnnnnnnn", "Geers", 24, 28, positions);

        assertEquals("First name must be less than 16 characters", alert);
    }
}